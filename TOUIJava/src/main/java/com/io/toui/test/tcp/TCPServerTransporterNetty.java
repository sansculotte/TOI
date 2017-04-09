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
package com.io.toui.test.tcp;

import com.io.toui.model.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public final class TCPServerTransporterNetty implements ITransporter {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final Channel ch;

    private ITransporterListener   listener;

    public  Class<ITOUISerializer> serializerClass;

    public TCPServerTransporterNetty(final int _port) throws InterruptedException {

        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .childHandler(new TCPServerInitializer(this));

        ch = bootstrap.bind(_port).sync().channel();
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
