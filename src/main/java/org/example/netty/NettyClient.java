package org.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.example.netty.entity_client.GameDataClient;
import org.example.netty.handler.HandlerMessage;
public class NettyClient {
    private final GameDataClient gameDataClient;
    public  NettyClient(GameDataClient gameDataClient) {
        this.gameDataClient = gameDataClient;
    }
    public void connect(String host, int port) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap boot = new  Bootstrap();
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .handler(new  ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(32 * 1024, 1, 4, 0, 0));
                            ch.pipeline().addLast(new HandlerMessage(gameDataClient));
                        }
                    });
            ChannelFuture future = boot.connect(host, port).sync();
            gameDataClient.setChannel(future.channel());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}