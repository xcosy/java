package com.xcosy.java8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableThread implements Callable<Integer> {

    public static void main(String[] args) {
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> futureTask = new FutureTask<>(callableThread);

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "的循环变量i的值：" + i);
            if (i == 20) {
                new Thread(futureTask, "有返回值的线程").start();
            }
        }

        try {
            System.out.println("线程的返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Integer call() throws Exception {
        int result = 0;
        for (int j = result; j < 10; j++) {
            System.out.println(Thread.currentThread().getName() + " " + (result = j));
        }
        return result;
    }
}
