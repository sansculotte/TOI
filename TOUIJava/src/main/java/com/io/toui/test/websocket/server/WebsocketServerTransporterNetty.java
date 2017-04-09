package com.io.toui.test.websocket.server;

import com.io.toui.model.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;


public final class WebsocketServerTransporterNetty implements ITransporter {

    static final boolean SSL = System.getProperty("ssl") != null;
    //    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" :
    // "8080"));

    private EventLoopGroup bossGroup   = new NioEventLoopGroup(1);

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final SslContext sslCtx;

    private final Channel    ch;

    private ITransporterListener   listener;

    public  Class<ITOUISerializer> serializerClass;

    public WebsocketServerTransporterNetty(final int _port) throws
                                                            CertificateException,
                                                            SSLException,
                                                            InterruptedException {

        if (SSL) {
            final SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        }
        else {
            sslCtx = null;
        }

        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         //             .handler(new LoggingHandler(LogLevel.INFO))
         .childHandler(new WebSocketServerInitializer(sslCtx));

        ch = bootstrap.bind(_port).sync().channel();

        System.out.println("Open your web browser and navigate to " +
                           (SSL ? "https" : "http") +
                           "://127.0.0.1:" +
                           _port +
                           '/');
    }

    public void stop() throws InterruptedException {

        ch.closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    ChannelHandlerContext lastCtx;

    public void received(final ChannelHandlerContext ctx, final byte[] _data) {

        lastCtx = ctx;
        received(_data);
    }

    public void done(final ChannelHandlerContext ctx) {

        lastCtx = null;
    }

    @Override
    public void received(final byte[] _data) {

        //        System.out.println("recv: " + new String(_data));

        if (listener != null) {
            listener.received(_data);
        }

    }

    @Override
    public void send(final byte[] _data) {

        if (lastCtx != null) {

            lastCtx.channel()
                   .writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(_data)));
        }
    }

    @Override
    public void send(final Packet<?> _packet) {
        // TODO
    }

    @Override
    public void setListener(final ITransporterListener _listener) {

        listener = _listener;
    }

    @Override
    public void setSerializer(final Class<ITOUISerializer> _serializerClass) {

        serializerClass = _serializerClass;
    }

    @Override
    public Class<ITOUISerializer> getSerializer() {

        return serializerClass;
    }
}
