package server.entity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class GameDataServer {
  private volatile int x;
  private volatile int y;
  private volatile int width;
  private volatile int height;
  private volatile int speed = 10;
  private volatile int pack_check;
  private volatile int keysApp;
  public GameDataServer(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
  }

  public GameDataServer() {}

  public void updatePhysicsTime(ChannelHandlerContext channel) {
      int key = getKeysApp();
      if((key & (1 << 0)) != 0) y -= speed;
      if((key & (1 << 1)) != 0) y += speed;
      if((key & (1 << 2)) != 0) x -= speed;
      if((key & (1 << 3)) != 0) x += speed;
      keyboard(channel);
  }

  public void keyboard(ChannelHandlerContext context) {
      ByteBuf buf = context.alloc().buffer(0x9);
      buf.writeByte(0x4);
      buf.writeInt(this.y);
      buf.writeInt(this.x);
      context.writeAndFlush(buf);
  }

    public void setKeysApp(int keysApp) {
        this.keysApp = keysApp;
    }

    public int getKeysApp() {
        return keysApp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
}