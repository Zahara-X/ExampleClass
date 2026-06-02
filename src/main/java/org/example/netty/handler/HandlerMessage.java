package org.example.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.entity_client.GameDataClient;

public class HandlerMessage extends SimpleChannelInboundHandler<ByteBuf> {
    private final GameDataClient gameDataClient;
    public HandlerMessage(GameDataClient gameDataClient) {
        this.gameDataClient = gameDataClient;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
         byte codec = msg.readByte();
         if(codec == 0x4) {
             int y = msg.readInt(), x = msg.readInt();
             gameDataClient.setY(y);
             gameDataClient.setX(x);
         }
         if(codec == 0x6) {
             int packet = msg.readInt();
             int x = packet & 0xFF;
             int y = (packet >>> 8) & 0xFF;
             int width = (packet >>> 16) & 0xFF;
             int height = (packet >>> 24) & 0xFF;
             gameDataClient.spawnEntity(x, y, width, height);
         }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}