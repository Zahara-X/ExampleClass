package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.entity.GameDataServer;
import server.han.NettyHandlerServer;

import java.util.concurrent.TimeUnit;

public class ServerNetty {
    private final GameDataServer gameDataServer;
    public ServerNetty(GameDataServer gameDataServer) {
        this.gameDataServer = gameDataServer;
    }
    public void bind(int port) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new  NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(group, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childHandler(new  ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(32 * 1024, 5, 4, 0, 0));
                            ch.pipeline().addLast(new NettyHandlerServer(gameDataServer));
                        }
                    });
            ChannelFuture future = boot.bind(port).sync();
            future.channel().eventLoop().scheduleAtFixedRate(() -> {
                gameDataServer.updatePhysicsTime(null);
            }, 0, 16, TimeUnit.MILLISECONDS);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    public static void main(String[] args) {
      new ServerNetty(new GameDataServer()).bind(7070);
    }
}