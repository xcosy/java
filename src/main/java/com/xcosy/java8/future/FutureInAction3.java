package com.xcosy.java8.future;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 异步基于事件回调的Future
 *
 * 不要等待而是完成之后自动获取
 *
 */
public class FutureInAction3 {

    public static void main(String[] args) {
        Future<String> invoke = invoke(() -> {
            try {
                Thread.sleep(10_000L);
                return "I'm finished.";
            } catch (InterruptedException e) {
                return "I'm Error.";
            }
        });

        invoke.setCompletable(new Completable<String>() {
            @Override
            public void complete(String s) {
                System.out.println(s);
            }

            @Override
            public void exception(Throwable causeException) {
                System.out.println("ERROR");
                causeException.printStackTrace();
            }
        });

        System.out.println(invoke.get());
        System.out.println("......");
    }

    private static <T> Future<T> invoke(Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);

        Future<T> future = new Future<T>() {

            private Completable<T> completable;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }

            @Override
            public void setCompletable(Completable<T> completable) {
                this.completable = completable;
            }

            @Override
            public Completable<T> getCompletable() {
                return completable;
            }
        };

        Thread t = new Thread(() -> {
            try {
                T value = callable.action();
                result.set(value);
                finished.set(true);
                if (future.getCompletable() != null) {
                    future.getCompletable().complete(value);
                }
            } catch (Throwable causeException) {
                if (future.getCompletable() != null) {
                    future.getCompletable().exception(causeException);
                }
            }
        });
        t.start();

        return future;
    }

    // 怎么写一个自己的Future呢？
    private interface Future<T> {
        T get();

        boolean isDone();

        /**
         * 添加方法
         */
        void setCompletable(Completable<T> completable);

        Completable<T> getCompletable();
    }

    private interface Callable<T> {
        T action();
    }


    /**
     * 当有值的时候通知这个方法
     * @param <T>
     */
    private interface Completable<T> {

        void complete(T t);

        void exception(Throwable causeException);

    }

}
