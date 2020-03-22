package com.codedancing.java.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以让文件在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MappedByteBuffer {

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("/home/codedancing/Desktop/01.txt", "rw");
        // 获取对应的Channel
        FileChannel accessFileChannel = accessFile.getChannel();
        /**
         * 实际类型：DirectByteBuffer
         *
         * 参数1：读写模式
         * 参数2：直接修改的起始位置
         * 参数3：映射多少字节到内存
         * 可以直接修改的范围就是 0 ～ 5 字节
         */
        java.nio.MappedByteBuffer mappedByteBuffer = accessFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'A');
        mappedByteBuffer.put(3, (byte)'B');

        accessFileChannel.close();
        accessFile.close();
    }

}
