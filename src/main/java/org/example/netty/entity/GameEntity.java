package org.example.netty.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class GameEntity {
    protected volatile int x;
    protected volatile int y;
    protected volatile int width;
    protected volatile int height;
    private AtomicInteger xAtom = new AtomicInteger(0);
    private AtomicInteger yAtom = new AtomicInteger(0);
    public GameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameEntity() {}

    public void createEntity(int x, int y, int width, int height) {
        xAtom.set(x);
        xAtom.set(x);
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return xAtom.get();
    }

    public void setX(int x) {
        xAtom.set(x);
    }

    public int getY() {
        return yAtom.get();
    }

    public void setY(int y) {
        yAtom.set(y);
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
}