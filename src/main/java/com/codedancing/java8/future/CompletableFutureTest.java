package com.codedancing.java8.future;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * java 8 CompletableFuture
 */
public class CompletableFutureTest {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        CompletableFuture<Double> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            double v = get();
            completableFuture.complete(v);
        }).start();

        System.out.println("--no block--");
        // 还是阻塞式
        //Optional.ofNullable(completableFuture.get()).ifPresent(System.out::println);

        // 不是主动拿，而是完成之后告诉我
        completableFuture.whenComplete((v, t) -> {
           Optional.ofNullable(v).ifPresent(System.out::println);
           Optional.ofNullable(t).ifPresent(System.out::println);
        });
    }

    static double get() {
        try {
            Thread.sleep(RANDOM.nextInt(10_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return RANDOM.nextDouble();
    }

}
