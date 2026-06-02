package server.han;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import server.entity.GameDataServer;
import server.entity.OwnerSettingPlayer;

public class NettyHandlerServer extends SimpleChannelInboundHandler<ByteBuf> {
    private final OwnerSettingPlayer ownerSettingPlayer = new OwnerSettingPlayer();
    private final GameDataServer gameDataServer;
    public NettyHandlerServer(GameDataServer gameDataServer) {
        this.gameDataServer = gameDataServer;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
     byte codec = msg.readByte();
     if(codec == 0x5) {
         int key = msg.readInt();
         gameDataServer.setKeysApp(key);
         gameDataServer.updatePhysicsTime(ctx);
     }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ownerSettingPlayer.spawn_player(ctx);
        super.channelActive(ctx);
    }
}