package com.xcosy.java.io.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args) {

        // 创建一个ByteBuf对象
        // 1. 该对象包含一个Byte[]，数据大小是10
        // 2. 在Netty的Buffer中，不需要进行flip反转，因为底层维护了readIndex和writeIndex和capacity，分成了三个段：
        // 0 - readIndex，已经读取的区域；readIndex - writeIndex，可读取的区域；writeIndex - capacity，可写入的区域
        ByteBuf buf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }

        System.out.println("容量：" + buf.capacity());

        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println(buf.readByte());
        }

    }

}
