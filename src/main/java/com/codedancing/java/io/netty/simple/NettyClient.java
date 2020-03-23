package com.codedancing.java.io.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            // 注意客户端使用的不是ServerBootstrap而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            // 设置相关参数
            // 1. 设置线程组
            // 2. 使用NioSocketChannel作为客户端通道的实现类
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 加入自己的处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("Client is ready...");

            // 启动客户端连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6000).sync();
            // 进行监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

}
