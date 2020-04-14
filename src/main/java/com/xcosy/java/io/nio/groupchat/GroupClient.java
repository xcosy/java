package com.xcosy.java.io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupClient {

    private final String IP = "127.0.0.1";
    private final int PORT = 6677;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public static void main(String[] args) {
        GroupClient groupClient = new GroupClient();
        // 每三秒读取从服务器发送的数据
        new Thread(() -> {
           while(true) {
               groupClient.readMsg();
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        // 发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            groupClient.sendMsg(msg);
        }
    }

    public GroupClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            // 连接服务器
            socketChannel.connect(new InetSocketAddress(IP, PORT));
            // 配置非阻塞
            socketChannel.configureBlocking(false);
            // 注册到Selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 设置Username
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "is ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送给服务器消息
     */
    private void sendMsg(String message) {
        String info = username + "说：" + message;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取服务器端的消息
     */
    private void readMsg() {
        try {
            int channels = selector.select();
            if (channels > 0) {
                // 有可用的通道
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while(selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if (selectionKey.isReadable()) {
                        // 得到相关的通道
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        // 生成一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 读取数据
                        socketChannel.read(buffer);
                        // 转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    selectionKeyIterator.remove();
                }
            } else {
                //System.out.println("没有可用通道.");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
