package server.entity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class OwnerSettingPlayer {
    private final GameDataServer gameDataServer = new PlayerServer(100,100,100,100);
    public void spawn_player(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer(0x5);
        int[] arrayEntity = {gameDataServer.getX(), gameDataServer.getY(), gameDataServer.getWidth(), gameDataServer.getHeight()};
        int packet = (arrayEntity[0] & 0xFF) | (arrayEntity[1] << 8) | (arrayEntity[2] << 16) | (arrayEntity[3] << 24);
        buf.writeByte(0x6);
        buf.writeInt(packet);
        ctx.writeAndFlush(buf);
    }
}