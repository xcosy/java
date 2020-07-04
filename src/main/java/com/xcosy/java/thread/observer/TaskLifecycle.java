package com.xcosy.java.thread.observer;

public interface TaskLifecycle<T> {

    /**
     * 任务启动触发
     */
    void onStart(Thread thread);

    /**
     * 任务运行时触发
     */
    void onRunning(Thread thread);

    /**
     * 任务完成时触发
     */
    void onFinish(Thread thread, T result);

    /**
     * 任务执行报错时触发
     */
    void onError(Thread thread, Exception e);

    /**
     * 生命周期接口的默认实现
     */
    class DefaultTaskLifecycle<T> implements TaskLifecycle<T> {

        @Override
        public void onStart(Thread thread) {
            System.out.println("task is started.");
        }

        @Override
        public void onRunning(Thread thread) {
            System.out.println("task is running.");
        }

        @Override
        public void onFinish(Thread thread, T result) {
            System.out.println("task is finished. result: " + result);
        }

        @Override
        public void onError(Thread thread, Exception e) {
            System.out.println("task has a error : " + e.getMessage());
        }
    }

}
