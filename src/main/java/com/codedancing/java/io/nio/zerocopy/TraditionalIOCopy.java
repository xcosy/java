package com.codedancing.java.io.nio.zerocopy;

import java.io.*;

/**
 * 传统IO拷贝大文件
 *
 * result： file size: 68104307 bytes, take 239ms
 */
public class TraditionalIOCopy {

    public static void main(String[] args) throws IOException {
        // 指定文件位置和IO
        FileInputStream fis = new FileInputStream(new File("/home/codedancing/Desktop/Jenkins权威指南.pdf"));
        DataInputStream dis = new DataInputStream(fis);
        // 目标文件位置和IO
        FileOutputStream fos = new FileOutputStream(new File("/home/codedancing/Desktop/Jenkins权威指南(copy).pdf"));
        DataOutputStream dos = new DataOutputStream(fos);

        long startTime = System.currentTimeMillis();
        // 缓冲区
        byte[] buffer = new byte[1024];
        // 统计文件大小
        long fileSize = 0;
        // 每次读取的字节数
        long readBytes = 0;
        // 开始拷贝
        while((readBytes = dis.read(buffer)) >= 0) {
            fileSize += readBytes;
            dos.write(buffer);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("file size: %d bytes, take %sms", fileSize, endTime - startTime));

        // 关闭资源
        fos.close();
        fis.close();
        dos.close();
        dis.close();
    }

}
