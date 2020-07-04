package com.xcosy.java.thread.observer;

public interface Observable {

    /**
     * 任务生命周期的枚举类型
     */
    enum Cycle{
        STARTED, RUNNING, DONE, ERROR
    }

    /**
     * 获取当前任务的生命周期状态
     */
    Cycle getCycle();

    /**
     * 定义启动线程的方法，主要作用是为了屏蔽 Thread 的其它方法
     */
    void start();

    /**
     * 定义线程的打断方法，作用与 start 方法一样，也是为了屏蔽 Thread 的其它方法
     */
    void interrupt();

}
