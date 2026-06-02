package org.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netty.entity.Cube;
import org.example.netty.handler_client.NettyHandlerClient;

public class NettyClient {
    private final Cube cube;
    public NettyClient(Cube cube) {
        this.cube = cube;
    }
    public void connect(String host, int port) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap boot = new  Bootstrap();
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_RCVBUF, 1024)
                    .option(ChannelOption.SO_SNDBUF, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new  ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyHandlerClient(cube));
                        }
                    });
            ChannelFuture future = boot.connect(host, port).sync();
            cube.syn_channel(future.channel());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}