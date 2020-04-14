package com.xcosy.java.thread.synchronize;

public class TicketWindowRunnable implements Runnable {

    private int index = 1;

    /**
     * 所有的票数
     */
    private static final int MAX = 100;

    private final Object MONITOR = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (MONITOR) {
                if (index > MAX) {
                    break;
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("【" + Thread.currentThread().getName() + "】 的号码是【" + index++ + "】");
            }
        }
    }
}
