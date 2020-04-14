package com.xcosy.java.io.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        // 生成ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞通道
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 6000));
        // 生成一个Selector对象
        Selector selector = Selector.open();
        // 把serverSocketChannel注册到Selector，关注事件OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端链接
        while(true) {
            if (selector.select(1000) == 0) {
                continue;
            }
            // 如果返回大于0， 就获取到相关的SelectionKey
            // selector.selectedKeys()返回关注事件的集合
            // 通过selectionKey获取指定的通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while(selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                // 判断请求类型
                if (selectionKey.isAcceptable()) {
                    // 给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println(String.format("客户端连接成功！, socketChannel:%s", socketChannel.hashCode()));
                    // 将SocketChannel注册到Selector，关注事件为 OP_READ
                    // 给该socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    // 获取缓冲区并读取内容
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    socketChannel.read(buffer);
                    System.out.println(String.format("from 客户端 %s", new String(buffer.array())));
                }
                // 手动从集合中移除当前的selectionKey，防止重复操作
                selectionKeyIterator.remove();
            }
        }
    }

}
