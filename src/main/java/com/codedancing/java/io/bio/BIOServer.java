package com.codedancing.java.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池机制
 */
public class BIOServer {

    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8888);
        System.out.println("服务已启动");
        while (true) {
            Socket accept = socket.accept();
            System.out.println("已连接一个客户端");
            THREAD_POOL.execute(() -> {
                handler(accept);
            });
        }
    }

    private static void handler(Socket socket) {
        try {
            byte[] buffer = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(buffer);
                if (read != -1) {
                    System.out.println("客户端消息：" + new String(buffer, 0, buffer.length));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("客户端连接已关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
