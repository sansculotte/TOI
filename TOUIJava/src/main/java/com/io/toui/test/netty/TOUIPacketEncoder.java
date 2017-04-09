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
package com.io.toui.test.netty;

import com.io.toui.model.ITOUISerializer;
import com.io.toui.model.Packet;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class TOUIPacketEncoder extends MessageToMessageEncoder<Packet<?>> {

    private final ITOUISerializer serializer;

    public TOUIPacketEncoder(final ITOUISerializer _serializer) {
        serializer = _serializer;
    }

    @Override
    protected void encode(
            final ChannelHandlerContext ctx, final Packet<?> msg, final List<Object> out) throws
                                                                                          Exception {


        out.add(Unpooled.wrappedBuffer(serializer.serialize(msg)));
    }
}
