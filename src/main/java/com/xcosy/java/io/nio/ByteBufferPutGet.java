package com.xcosy.java.io.nio;

import java.nio.ByteBuffer;

/**
 * Buffer的存取一致性和只读特性
 */
public class ByteBufferPutGet {

    public static void main(String[] args) {
        // bufferGetPut1();
        bufferGetPut2();
    }

    private static void bufferGetPut1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.putInt(100);
        byteBuffer.putInt(200);
        byteBuffer.putInt(300);
        byteBuffer.putChar('中');
        byteBuffer.putChar('国');
        byteBuffer.putShort((short)10);

        byteBuffer.flip();

        // 如果存取类型不匹配会抛出 java.nio.BufferUnderflowException
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }

    private static void bufferGetPut2() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.put((byte)1);
        byteBuffer.put((byte)2);
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.get());

        // 转换成 ReadOnlyBuffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        // 抛出异常 java.nio.ReadOnlyBufferException
        readOnlyBuffer.put((byte) 1);
    }

}
