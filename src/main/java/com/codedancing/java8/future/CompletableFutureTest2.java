package com.codedancing.java8.future;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 很少直接new CompletableFuture， 而是使用其工厂方法（静态方法）去使用
 */
public class CompletableFutureTest2 {

    public static void main(String[] args) throws InterruptedException {

//        AtomicBoolean finished = new AtomicBoolean(false);
//
//        // 这其实就是一个守护线程，主线程执行结束怎么办呢？
//        CompletableFuture.supplyAsync(CompletableFutureTest::get).whenComplete((v, t) -> {
//            Optional.ofNullable(v).ifPresent(System.out::println);
//            Optional.ofNullable(t).ifPresent(x -> x.printStackTrace());
//            finished.set(true);
//        });
//
//        System.out.println("--no block--");
//        // join 主线程
//        while (!finished.get()) {
//            Thread.sleep(1);
//        }


        // 第一个参数是线程数，第二个参数是设置线程参数
        ExecutorService executorService = Executors.newFixedThreadPool(2, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        executorService.submit(() -> {
            System.out.println("test...");
        });


    }

}


