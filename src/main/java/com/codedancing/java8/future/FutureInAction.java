package com.codedancing.java8.future;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FutureInAction {

    public static void main(String[] args) {
        Future<String> future = invoke(() -> {
            try {
                Thread.sleep(5_000);
                return "I'm finished.";
            } catch (InterruptedException e) {
                return "Error";
            }
        });

        // 已经可以获取到票据
        System.out.println(future.get());
        System.out.println(future.get());
        System.out.println(future.get());


        // 开始处理其他事务
        // 。。。。
        // 。。。。


        // 怎么判断之前的事务已经有值了？
        while (!future.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(future.get());
    }

    private static <T> Future<T> invoke(Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);

        Thread t = new Thread(() -> {
            T value = callable.action();
            result.set(value);
            finished.set(true);
        });

        t.start();

        Future<T> future = new Future<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }
        };

        return future;
    }

    // 怎么写一个自己的Future呢？
    private interface Future<T> {
        T get();

        boolean isDone();
    }

    private interface Callable<T> {
        T action();
    }

}
