package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import server.entity_server.CubeServer;

public class NettyHandlerServer extends SimpleChannelInboundHandler<ByteBuf> {
    private final CubeServer cubeServer;
    public NettyHandlerServer(CubeServer cubeServer) {
        this.cubeServer = cubeServer;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte index = msg.readByte();
        if(index == 0x1) cubeServer.currentMask = msg.readInt();

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      cubeServer.channel = ctx.channel();
      cubeServer.spawnPlayer(new CubeServer(100,100,100,100));
    }
}