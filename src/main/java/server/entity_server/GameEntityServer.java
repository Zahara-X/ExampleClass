package server.entity_server;

public class GameEntityServer {
   protected volatile int x;
   protected volatile int y;
   protected volatile int width;
   protected volatile int height;
   protected volatile int speed = 10;
    public GameEntityServer(int x, int y, int width, int height) {
        this.x = x;
        this.width = width;
        this.y = y;
        this.height = height;
    }
    public GameEntityServer() {}

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