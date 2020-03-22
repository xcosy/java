package com.codedancing.java.io.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel文件写入
 */
public class FileChannelWrite {

    public static void main(String[] args) throws FileNotFoundException {
        String str = "Hello NIO";
        // 创建一个输出流 -> Channel
        FileOutputStream fos = new FileOutputStream("/home/codedancing/Desktop/01.txt");
        // 通过 FileOutputStream 获取对应的FileChannel
        FileChannel fosChannel = fos.getChannel();
        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将 str 放入 byteBuffer
        byteBuffer.put(str.getBytes());
        // 翻转Buffer
        byteBuffer.flip();
        // 将Buffer的数据写入Channel
        try {
            fosChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fosChannel.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
