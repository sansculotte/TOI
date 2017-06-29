package com.io.toi.test.tcp;

import com.io.toi.test.netty.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

public class TCPServerInitializer extends ChannelInitializer<SocketChannel> {

    private final ITransporterNetty server;

    public TCPServerInitializer(final ITransporterNetty _server) {

        server = _server;
    }

    @Override
    public void initChannel(final SocketChannel ch) throws Exception {

        final ChannelPipeline pipeline = ch.pipeline();

        // TODO.... no JSON here..
        // TODO: we need a streaming decoder here
        pipeline.addLast(new JsonObjectDecoder()); // default 1024 x 1024
        pipeline.addLast(new TOUIPacketDecoder());
        pipeline.addLast(new TOUIPacketHandler(server));

        pipeline.addLast(new TOUIPacketEncoder());
    }

}
