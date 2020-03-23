package com.codedancing.java.io.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandler 的子类
 *
 * HttpObject：客户端和服务器相互通讯的数据被封装成HttpObject
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型 = " + msg.getClass());
            System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

            // 过滤特定资源
            // 获取Request
            HttpRequest request = (HttpRequest) msg;
            // 获取URI
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("客户端请求了：" + uri.getPath() + ", 不做响应。");
                return;
            }


            // 回复消息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("Hello， I'm Server.", CharsetUtil.UTF_8);
            // 构造一个HTTP响应，即HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }

}
