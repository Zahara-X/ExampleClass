package server.entity_server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class CubeServer extends GameEntityServer {
    public Channel channel;
    public volatile int currentMask;
    public CubeServer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public CubeServer() {}

    public void move(char c) {
        if(c == 'W') this.y -= this.speed;
        if(c == 'S') this.y += this.speed;
        if(c == 'A') this.x -= this.speed;
        if(c == 'D') this.x += this.speed;
        System.out.println("Y: " + y);
        System.out.println("X: " + x);
        ByteBuf buf = channel.alloc().buffer(0x9);
        buf.writeByte(0x2);
        buf.writeInt(x);
        buf.writeInt(y);
        channel.writeAndFlush(buf);
    }
    public void spawnPlayer(GameEntityServer entity) {
        this.x = entity.x;
        this.y = entity.y;
        this.width = entity.width;
        this.height = entity.height;

        ByteBuf buf = channel.alloc().buffer(16);
        buf.writeByte(0x4);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(width);
        buf.writeInt(height);
        channel.writeAndFlush(buf);
    }
}