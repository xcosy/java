package com.xcosy.java.io.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器的IP、端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6000);

        if (!socketChannel.connect(address)) {
            while(!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }

        // 如果连接成功
        String str = "123";
        // ByteBuffer buffer = ByteBuffer.allocate(1024);
        // buffer.put(str.getBytes());
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);

        // 将Client停止在这里
        System.in.read();
    }

}
