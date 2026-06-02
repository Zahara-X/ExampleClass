package org.example.netty.entity_client;

public class Player extends GameDataClient {

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public Player() {}

    @Override
    public void spawnEntity(int x, int y, int width, int height) {
        super.spawnEntity(x, y, width, height);
    }

}