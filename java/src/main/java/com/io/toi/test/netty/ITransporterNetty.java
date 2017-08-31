package com.io.toi.test.netty;

import com.io.toi.model.ITransporter;
import com.io.toi.model.ToiPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface ITransporterNetty extends ITransporter {

    void received(final ChannelHandlerContext ctx, final ToiPacket _packet);

    void addChannel(final Channel _channel);

    void removeChannel(final Channel _channel);
}
