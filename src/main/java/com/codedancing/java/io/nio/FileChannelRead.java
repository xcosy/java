package com.codedancing.java.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel文件读取
 */
public class FileChannelRead {

    public static void main(String[] args) throws IOException {
        // 创建文件输入流
        File file = new File("/home/codedancing/Desktop/01.txt");
        FileInputStream fio = new FileInputStream(file);

        // 获取FileChannel
        FileChannel fioChannel = fio.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        fioChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));

        fioChannel.close();
        fio.close();
    }

}
