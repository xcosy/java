package com.codedancing.java8.future;

import java.util.concurrent.*;

/**
 * JDK 自带的Future
 */
public class FutureInAction2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> submit = executorService.submit(() -> {
            try {
                Thread.sleep(10_000L);
                return "I'm finished.";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "ERROR";
            }
        });

        String s = submit.get(10, TimeUnit.MICROSECONDS);
        System.out.println(s);
        executorService.shutdown();


        while (!submit.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(submit.get());
        executorService.shutdown();


    }

}
