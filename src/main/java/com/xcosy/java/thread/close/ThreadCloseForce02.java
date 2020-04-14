package com.xcosy.java.thread.close;

public class ThreadCloseForce02 {

    public static class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                if (this.isInterrupted()) {
                    break;
                }

                // 如果下面还有需要执行的逻辑，就无法执行到
            }
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.interrupt();
    }
}
