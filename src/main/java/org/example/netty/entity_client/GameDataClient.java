package org.example.netty.entity_client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class GameDataClient {
    protected volatile int x;
    protected volatile int y;
    private volatile int width;
    private volatile int height;
    private volatile int speed = 10;
    private volatile int pack_check;
    private Channel channel;
    public GameDataClient(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public GameDataClient() {}

    public void move(int key) {
        if(channel == null) return;
        ByteBuf buf = channel.alloc().buffer(0x5);
        buf.writeByte(0x5);
        buf.writeInt(key);
        channel.write(buf);
        channel.flush();
    }
    public void spawnEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void keysXNY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPack_check() {
        return pack_check;
    }

    public void setPack_check(int pack_check) {
        this.pack_check = pack_check;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}