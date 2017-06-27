package com.io.toi.test.websocket.server;

import com.io.toi.model.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.GlobalEventExecutor;

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

    final ChannelGroup allClients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
         .childHandler(new WebSocketServerInitializer(sslCtx, this));

        ch = bootstrap.bind(_port).sync().channel();

        System.out.println("Open your web browser and navigate to " +
                           (SSL ? "https" : "http") +
                           "://127.0.0.1:" +
                           _port +
                           '/');
    }

    public void stop() throws InterruptedException {

        allClients.close().awaitUninterruptibly();

        ch.closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    ChannelHandlerContext lastCtx;

    public void received(final ChannelHandlerContext ctx, final byte[] _data) {

        lastCtx = ctx;
        received(_data);
    }

    public void received(final ChannelHandlerContext ctx, final ToiPacket _packet) {

        lastCtx = ctx;
        received(_packet);
    }

    public void done(final ChannelHandlerContext ctx) {

        lastCtx = null;
    }

    @Override
    public void received(final byte[] _data) {

        if (listener != null) {
            listener.received(_data);
        }
    }

    @Override
    public void received(final ToiPacket _packet) {

        if (listener != null) {
            listener.received(_packet);
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
    public void send(final ToiPacket _packet) {

        if (lastCtx != null) {

            lastCtx.channel().writeAndFlush(_packet);

        } else {
            // send to all

            System.out.println(" ->> send to all");

            allClients.writeAndFlush(_packet);
        }
    }

    @Override
    public void setListener(final ITransporterListener _listener) {

        listener = _listener;
    }

    public void addChannel(final Channel _channel) {

        System.out.println("add channel: " + _channel.config());

        allClients.add(_channel);
    }

    public void removeChannel(final Channel _channel) {

        System.out.println("remove channel: " + _channel.config());

        allClients.remove(_channel);
    }
}
