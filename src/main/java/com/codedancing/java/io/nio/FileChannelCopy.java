package com.codedancing.java.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel文件拷贝
 */
public class FileChannelCopy {

    public static void main(String[] args) throws IOException {
        // 1
        copyFile1();
        // 2
        copyFile2();
    }

    /**
     * 使用Buffer
     */
    private static void copyFile1() throws IOException {
        FileInputStream fis = new FileInputStream("/home/codedancing/Desktop/01.txt");
        FileChannel fisChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("/home/codedancing/Desktop/02.txt");
        FileChannel fosChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(30 * 3);

        // 拷贝数据
        while(true) {
            // 读取数据
            int read = fisChannel.read(byteBuffer);
            System.out.println("read:" + read);
            if (read == -1) {
                break;
            }
            // 写入数据
            byteBuffer.flip();
            fosChannel.write(byteBuffer);
            // 清空Buffer
            byteBuffer.clear();
        }

        fosChannel.close();
        fisChannel.close();
        fos.close();
        fis.close();
    }

    /**
     * 使用TransferFrom
     */
    private static void copyFile2() throws IOException {
        FileInputStream fis = new FileInputStream("/home/codedancing/Desktop/01.txt");
        FileOutputStream fos = new FileOutputStream("/home/codedancing/Desktop/03.txt");

        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        // fosChannel从fisChannel中获取数据
        fosChannel.transferFrom(fisChannel, 0, fisChannel.size());

        fosChannel.close();
        fisChannel.close();
        fos.close();
        fis.close();
    }

}
