package com.xcosy.java.io.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 说明：
 * 1. 自定义一个Handler需要继承Netty规定好的HandlerAdapter
 * 2. 这时自定义的Handler，才能称为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取实际的数据（读取客户端发送的消息）
     * <p>
     * 1. ChannelHandlerContext ctx:上下文对象，含有管道pipeline、通道Channel、地址
     * 2. Object msg:就是客户端发送的数据，模认是Object
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
        // 将数据写入缓存区并刷新
        // 一般来说，对于发送的数据需要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, Client!", CharsetUtil.UTF_8));
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
