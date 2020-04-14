package com.xcosy.java.io.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {

    public static void main(String[] args) {
        // 创建 ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("Hello, Netty!", CharsetUtil.UTF_8);

        // 判断是否有数据
        if (buf.hasArray()) {
            byte[] content = buf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());
            System.out.println(buf.capacity());
        }

        // 当前还可以读取的字节数
        System.out.println(buf.readableBytes());

        // 取出各个字节
        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println((char)buf.getByte(i));
        }

        // 取出指定范围的内容，从几开始读几个
        System.out.println(buf.getCharSequence(0, 4, CharsetUtil.UTF_8));

        // UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 13, cap: 39)
        System.out.println(buf);
    }

}
