package com.codedancing.java.io.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理
        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();
        // 加入Netty提供的HttpServerCodec,它是Netty提供的HTTP的编/解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 加入自定的Handler
        pipeline.addLast("MyHttpServerHandler", new MyHttpServerHandler());
    }

}
