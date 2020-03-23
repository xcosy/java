package com.codedancing.java.io.netty.task;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 目的：
 * 了解Netty中NioEventLoop的taskQueue、scheduleTaskQueue
 */
public class NettyServerTaskQueueHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        // 将msg转成一个ByteBuf（这个类是Netty提供的，不是NIO的ByteBuffer）
        // ByteBuf性能更高
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址： " + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, Client!", CharsetUtil.UTF_8));

        // 将耗时任务加入该channel对应的NioEventLoop的taskQueue中，异步执行，不会导致NettyServer阻塞
        // TaskQueue中的任务会依次执行
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 2", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 3", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 用户自定义定时任务，该任务提交到scheduleTaskQueue中
        // 指定定时时间
        ctx.channel().eventLoop().schedule(() -> {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 3", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }, 10, TimeUnit.SECONDS);

    }

    /**
     * 处理异常，如果有异常需要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
