package com.codedancing.java.io.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实例：
 * Netty服务器在 6000 端口监听，浏览器发出请求“http://localhost:6000”
 * 服务器可以回复消息给客户端“Hello，I'm Server.”，并对特定请求资源进行过滤
 *
 * 目的：
 * Netty可以做HTTP服务开发，帮助理解Handler实例和客户端及其请求的关系
 */
public class NettyHttpServerTest {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            // 服务配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            System.out.println("Server is ready...");

            // 启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(6000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

}
