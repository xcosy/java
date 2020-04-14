package com.xcosy.java.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 大数据量时，解决单个Buffer频繁切换影响效率
 *
 * Scattering：将数据写入到Buffer时，可以采用Buffer数组依次写入
 * Gathering：将数据从Buffer读取数据时，可以采用Buffer数组依次读取
 */
public class ScatteringAndGathering {

    public static void main(String[] args) throws IOException {
        // 使用 ServerSocketChannel 与 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到Socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 从客户端接受8个字节
        int messageLength = 8;
        // 循环读取
        while(true) {
            int byteRead = 0;
            while(byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                // 累计读取的字节数
                byteRead += read;
                System.out.println("byteRead: " + byteRead);
                // 使用流打印，看当前Buffer的Position和limit
                Arrays.stream(byteBuffers).map(buffer -> "position:" + buffer.position() + "" +
                        ", limit:" + buffer.limit()).forEach(System.out::println);
            }

            // 将所有的Buffer进行翻转
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // 将数据读出显示到客户端
            long byteWrite = 0;
            while(byteWrite < messageLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            // 将所有的Buffer进行clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println(String.format("byteRead: %s, byteWrite: %s, messageLength: %s", byteRead, byteWrite, messageLength));
        }
    }

}
