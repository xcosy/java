package com.codedancing.java.thread.close;

public class ThreadCloseForce03 {
    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();

        long startMills = System.currentTimeMillis();
        threadService.execute(() -> {
           /* while (true) {
                // time
            }*/

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadService.shutdown(5_000);
        System.out.println(System.currentTimeMillis() - startMills);

    }
}
