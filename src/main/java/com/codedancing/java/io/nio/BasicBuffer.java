package com.codedancing.java.io.nio;

import java.nio.IntBuffer;

/**
 * 认识Buffer
 */
public class BasicBuffer {

    // Buffer的使用
    public static void main(String[] args) {
        // 创建一个Buffer，大小为5，可以存放5个int数据
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 取数据
        // 将Buffer装换，读写切换
        intBuffer.flip();
        // 取出数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }

}
