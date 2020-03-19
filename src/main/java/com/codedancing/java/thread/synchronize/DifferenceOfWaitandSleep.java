package com.codedancing.java.thread.synchronize;

public class DifferenceOfWaitandSleep {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The Application will been exit!");
            // 当程序退出的时候释放一些资源，例如Tomcat等WEB容器关闭时释放资源
            notifyAndReleaseResource();
        }));


        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5_000);
                int result = 10 / 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(e);
            System.out.println(thread);
        });

        t.start();

        // m1();
        // m2();
    }

    private static void notifyAndReleaseResource() {
        System.out.println("release some resources...");
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done.");
    }

    private static void m1() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果没有使用synchronized代码，则会抛出java.lang.IllegalMonitorStateException
     */
    private static void m2() {
        synchronized(LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
