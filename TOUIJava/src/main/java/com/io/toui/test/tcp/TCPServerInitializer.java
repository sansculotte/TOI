package com.io.toui.test.tcp;

import com.io.toui.test.netty.TOUIPacketDecoder;
import com.io.toui.test.netty.TOUIPacketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

public class TCPServerInitializer extends ChannelInitializer<SocketChannel> {

    private final TCPServerTransporterNetty server;

    public TCPServerInitializer(final TCPServerTransporterNetty _server) {

        server = _server;
    }

    @Override
    public void initChannel(final SocketChannel ch) throws Exception {

        final ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new JsonObjectDecoder()); // default 1024 x 1024

        pipeline.addLast(new TOUIPacketDecoder());

        pipeline.addLast(new TOUIPacketHandler(server));
    }

}
