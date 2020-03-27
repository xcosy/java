package com.codedancing.java.io.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实例：
 * 使用Netty编写 Server Client
 * 让Client上线连接成功Server时，Server与Client打招呼
 *
 * 目的：
 * 了解Netty的线程模型
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组：BossGroup、WorkerGroup
        // BossGroup处理链接请求，真正的业务处理交给WorkerGroup完成
        // 两个都是无限循环
        // bossGroup 和 workerGroup 默认各自含有的子线程个数和（CPU的核数×2）相等，可以自己修改
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用链式编程来进行设置
            // 1. 设置两个线程组
            // 2. 使用NioServerSocketChannel作为服务器的通讯管道实现
            // 3. 设置线程队列得到的连接个数
            // 4. 设置保持活动连接状态
            // 5. 创建一个管道初始化对象（匿名）
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 给WorkerGroup的NioEventLoop对象的管道设置处理器
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("Server is ready...");

            // 绑定一个端口并且同步，生成了一个ChannelFuture对象，这里用到了Netty的异步模型机制
            // 启动服务器（并绑定端口）
            ChannelFuture channelFuture = serverBootstrap.bind(6000).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
