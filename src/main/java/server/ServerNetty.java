package server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.entity_server.CubeServer;
import server.handler.NettyHandlerServer;
import server.physics.PhysicsEngine;

import java.util.concurrent.TimeUnit;

public class ServerNetty {
    private final CubeServer cubeServer;
    public ServerNetty(CubeServer cubeServer) {
        this.cubeServer = cubeServer;
    }
    public void bind(int port) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new  NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(group, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_RCVBUF, 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new  ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyHandlerServer(cubeServer));
                        }
                    });
            ChannelFuture future = boot.bind(port).sync();
            future.channel().eventLoop().scheduleAtFixedRate(() -> {
                new PhysicsEngine(cubeServer).updatePhysics();
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
      new ServerNetty(new CubeServer()).bind(7075);
    }
}