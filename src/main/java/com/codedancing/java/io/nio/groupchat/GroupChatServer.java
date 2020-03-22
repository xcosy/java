package com.codedancing.java.io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int LISTEN_PORT = 6677;


    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }

    public GroupChatServer() {
        try {
            // 生成选择器
            selector = Selector.open();
            // 生成 ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(LISTEN_PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 注册listenChannel到selector，并指定关注事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     */
    private void listen() {
        try {
            while (true) {
                // 这里将会阻塞，如果使用select(timeout)将会经过timeout之后返回
                int count = selector.select();
                if (count > 0) {
                    // 有事件需要处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext()) {
                        // 取出key
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        // 监听到 ACCEPT
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        } else if(selectionKey.isReadable()) {
                            // 通道是可读状态
                            read(selectionKey);
                        }
                        // 删除key，防止重复处理
                        selectionKeyIterator.remove();
                    }

                } else {
                    System.out.println("正在等待...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 读取消息
     */
    private void read(SelectionKey selectionKey) {
        // 定义一个 SocketChannel
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                // 把buffer中的数据转成字符串
                String message = new String(buffer.array());
                System.out.println(String.format("from 客户端：%s", message));
                // 向其他客户端转发消息
                sender(message, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(String.format("%s 离线了", socketChannel.getRemoteAddress()));
                // 注销离线设备
                selectionKey.cancel();
                // 关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户端
     */
    private void sender(String message, SocketChannel selfSocketChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历selector中的selectionKey并排除自己
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != selfSocketChannel) {
                SocketChannel destChannel = (SocketChannel)targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                destChannel.write(buffer);
            }
        }
    }

}
