package com.xcosy.java.thread.threadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool extends Thread {

    /**
     * 线程池大小
     */
    private int size;

    /**
     * 最小线程数
     */
    private int min;

    /**
     * 中等线程数
     */
    private int active;

    /**
     * 最大线程数
     */
    private int max;

    /**
     * 任务队列大小
     */
    private int taskQueueSize;

    /**
     * 拒绝策略
     */
    private final DiscardPolicy discardPolicy;

    /**
     * 默认任务队列大小
     */
    private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;

    /**
     * 默认拒绝策略
     */
    public static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard this task!");
    };

    /**
     * 线程序列
     */
    private static volatile int seq = 0;

    /**
     * 线程名前缀
     */
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    /**
     * 线程组
     */
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    /**
     * 任务队列
     */
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列
     */
    private static final List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    /**
     * 线程池是否销毁，默认是false
     */
    private volatile boolean destroy = false;


    public SimpleThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    public void submit(Runnable runnable) {
        if (destroy) {
            throw new IllegalStateException("The thread pool already destroy and not allow submit task.");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > taskQueueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkTask();
        }
        this.size = this.min;
        this.start();
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("The thread pool Min: %d, Active: %d, Max: %d, CurrentThreadPoolSize: %d, CurrentTaskSize: %d.\n", this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to active.");
                    this.size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to max.");
                    this.size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        System.out.println("=========Reduce Work Thread=========");

                        int releaseSize = size - active;
                        for (Iterator<WorkTask> it = THREAD_QUEUE.iterator(); it.hasNext();) {
                            if (releaseSize <= 0) {
                                break;
                            }

                            WorkTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createWorkTask() {
        WorkTask workTask = new WorkTask(GROUP, THREAD_PREFIX + (seq++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }

        synchronized (THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkTask task : THREAD_QUEUE) {
                    if (task.getTaskState() == TaskState.BLOCKED) {
                        task.interrupt();
                        task.close();
                        initVal--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
        }

        this.destroy = true;
        System.out.println("The thread pool is disposed");
    }

    public int getMin() {
        return min;
    }

    public int getActive() {
        return active;
    }

    public int getMax() {
        return max;
    }

    public int getSize() {
        return size;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public boolean isDistory() {
        return this.destroy;
    }

    private enum TaskState {
        /**
         * 线程的四种状态：可运行，运行，阻塞，销毁
         */
        FREE, RUNNING, BLOCKED, DEAD
    }

    /**
     * 拒绝策略
     */
    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    /**
     * 拒绝策略异常
     */
    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    private static class WorkTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Work Thread Closed.");
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

    }

    public static void main(String[] args) throws InterruptedException {
//        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(6, 10, SimpleThreadPool.DEFAULT_DISCARD_POLICY);
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40)
                .forEach(i -> {
                    simpleThreadPool.submit(() -> {
                        System.out.println("The runnable " + i + " be Serviced by " + Thread.currentThread() + " started.");
                        try {
                            Thread.sleep(2_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("The runnable " + i + " be Serviced by " + Thread.currentThread() + " finished.");
                    });
                });

        Thread.sleep(20_000);
        simpleThreadPool.shutdown();

    }

}
