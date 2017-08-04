/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.io.toi.test.netty;

import com.io.toi.model.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ToiPacketHandler extends SimpleChannelInboundHandler<ToiPacket> {

    private final ITransporterNetty listener;

    public ToiPacketHandler(final ITransporterNetty _listener) {
        listener = _listener;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        if (listener != null) {
            listener.addChannel(ctx.channel());
        }

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {

        if (listener != null) {
            listener.removeChannel(ctx.channel());
        }

        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final ToiPacket toiPacket) throws
                                                                                      Exception {

        if (listener != null) {
            listener.received(toiPacket);
        }

    }
}
