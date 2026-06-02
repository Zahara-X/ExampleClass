package org.example.netty.entity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class Cube extends GameEntity {
    private Channel channel;
    public Cube(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public Cube() {}

    public void moveKey(int keys) {
        if(channel == null || !channel.isActive()) return;
        ByteBuf buf = channel.alloc().buffer(0x5);
        buf.writeByte(0x1);
        buf.writeInt(keys);
        channel.writeAndFlush(buf);
    }
    @Override
    public void createEntity(int x, int y, int width, int height) {
        super.createEntity(x, y, width, height);
    }
    public void syn_channel(Channel channel) {
        this.channel = channel;
    }
}