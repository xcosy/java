package com.codedancing.java.io.nio.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 实现了零拷贝的NIO拷贝
 *
 * result： file size: 68104307 bytes, take 40ms
 */
public class NIOCopy {

    public static void main(String[] args) throws IOException {
        // 指定源文件和目标文件
        FileChannel fisChannel = new FileInputStream("/home/codedancing/Desktop/Jenkins权威指南.pdf").getChannel();
        FileChannel fosChannel = new FileOutputStream("/home/codedancing/Desktop/Jenkins权威指南(copy).pdf").getChannel();

        long startTime = System.currentTimeMillis();
        // 在Linux环境下，transferTo方法一次就可以完成传输
        // 在Windows环境下，transferTo方法一次只能发送8M数据，需要分段传输文件，而且需要记住传输数据的offset
        long transferSize = fisChannel.transferTo(0, fisChannel.size(), fosChannel);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("file size: %d bytes, take %sms", transferSize, endTime - startTime));

        // 关闭资源
        fosChannel.close();
        fisChannel.close();
    }

}
