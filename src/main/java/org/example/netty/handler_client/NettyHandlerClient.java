package org.example.netty.handler_client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.entity.Cube;

public class NettyHandlerClient extends SimpleChannelInboundHandler<ByteBuf> {
    private final Cube cube;
    public NettyHandlerClient(Cube cube) {
        this.cube = cube;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte index = msg.readByte();
        if (index == 0x2) {
            while (msg.readableBytes() >= 8) {
                cube.setX(msg.readInt());
                cube.setY(msg.readInt());
            }
        }
        if(index == 0x4) {
            int x = msg.readInt();
            int y = msg.readInt();
            int width = msg.readInt();
            int height = msg.readInt();
            cube.createEntity(x, y, width, height);
        }
    }
}