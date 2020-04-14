package com.xcosy.java.jvm.designpatterns;

import java.util.concurrent.*;

/**
 * 在内部类初始化时，才创建Singleton实例
 */
public class Singleton {

   private Singleton() {}

   private static class Inner {
       private static final Singleton INSTANCE = new Singleton();
   }

   public static Singleton getInstance() {
       return Inner.INSTANCE;
   }
}

class SingleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton> callable = new Callable<Singleton>() {
            public Singleton call() throws Exception {
                return Singleton.getInstance();
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Singleton> f1 = es.submit(callable);
        Future<Singleton> f2 = es.submit(callable);

        System.out.println(f1.get());
        System.out.println(f2.get());

        es.shutdown();
    }
}