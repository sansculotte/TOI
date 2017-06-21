package com.io.toui.test.websocket.client;

import com.io.toui.model.*;
import com.io.toui.test.netty.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketClientTransporter implements ITransporter {

    EventLoopGroup group = new NioEventLoopGroup();

    private final SslContext sslCtx;

    private final Channel    ch;

    private ITransporterListener listener;

//    TOUISerializerFactory serializerFactory = new TOUISerializerFactory();

    public WebsocketClientTransporter(final String host, final int port) throws
                                                                         URISyntaxException,
                                                                         InterruptedException {

        final URI uri = new URI("ws://" + host + ":" + port + "/");

        sslCtx = null;

        // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
        // If you change it to V00, ping is not supported and remember to change
        // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
        final WebSocketClientHandler handler = new WebSocketClientHandler(
                WebSocketClientHandshakerFactory.newHandshaker(uri,
                                                               WebSocketVersion.V13,
                                                               null,
                                                               true,
                                                               new DefaultHttpHeaders()));

        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .handler(new ChannelInitializer<SocketChannel>() {

                     @Override
                     protected void initChannel(final SocketChannel ch) {

                         final ChannelPipeline pipeline = ch.pipeline();

                         if (sslCtx != null) {
                             pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                         }

                         pipeline.addLast(new HttpClientCodec());
                         pipeline.addLast(new HttpObjectAggregator(8192));
                         pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
                         pipeline.addLast(handler);


                         pipeline.addLast(new BinaryWebSocketFrameEncoder());
                         pipeline.addLast(new StringTextWebSocketFrameEncoder());
                         pipeline.addLast(new ByteArrayTextWebSocketFrameEncoder());
                         pipeline.addLast(new TOUIPacketEncoder());

                     }
                 });

        ch = bootstrap.connect(uri.getHost(), port).sync().channel();

        handler.handshakeFuture().sync();
    }

    @Override
    public void received(final byte[] _data) {

    }

    @Override
    public void received(final ToiPacket _packet) {

    }

    @Override
    public void send(final byte[] _data) {

        ch.writeAndFlush(_data);
    }

    @Override
    public void send(final ToiPacket _packet) {
        ch.writeAndFlush(_packet);
    }

    @Override
    public void setListener(final ITransporterListener _listener) {
        listener = _listener;
    }
}
