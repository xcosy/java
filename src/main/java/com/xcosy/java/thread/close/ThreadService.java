package com.xcosy.java.thread.close;

public class ThreadService {

    /**
     * 定义执行线程
     */
    private Thread executeThread;

    private volatile boolean finished = false;

    /**
     * 执行任务线程
     * @param task
     *        执行任务
     */
    public void execute(Runnable task) {
        executeThread = new Thread() {
            @Override
            public void run() {
                Thread taskThread = new Thread(task);
                taskThread.setDaemon(true);
                taskThread.start();

                // 执行线程等待任务线程执行结束
                try {
                    taskThread.join();
                    finished = true;
                    System.out.println("任务结束！");
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
            }
        };

        executeThread.start();
    }

    /**
     * 关闭线程
     * @param millis
     *        最多等待执行多长时间
     */
    public void shutdown(long millis) {
        long startTime = System.currentTimeMillis();
        while (!finished) {
            if ((System.currentTimeMillis() - startTime) >= millis) {
                System.out.println("任务超时！");
                executeThread.interrupt();
                break;
            }
        }
    }
}
