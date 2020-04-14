package com.xcosy.java.thread.close;

public class ThreadCloseForce01 {

    public static class Worker extends Thread {

        /**
         * 设置开关
         */
        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {

            }
        }

        public void shutdown() {
            this.start = false;
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

        worker.shutdown();

    }
}
