package com.xcosy.java.thread.observer;

/**
 * 专门监控业务执行单元的生命周期，真正的业务逻辑交给了一个可返回计算结果的接口 Task
 */
public class ObservableThread<T> extends Thread implements Observable{

    private final Task<T> task;

    private final TaskLifecycle<T> taskLifecycle;

    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new TaskLifecycle.DefaultTaskLifecycle<>(), task);
    }

    public ObservableThread(TaskLifecycle<T> taskLifecycle, Task<T> task) {
        // task 不允许为空
        if (task == null) throw new IllegalArgumentException();
        this.task = task;
        this.taskLifecycle = taskLifecycle;
    }

    @Override
    public final void run() {
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (taskLifecycle == null) return;
        try {
            switch (cycle) {
                case STARTED:
                    this.taskLifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.taskLifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.taskLifecycle.onFinish(currentThread(), result);
                    break;
                default:
                    this.taskLifecycle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception exception) {
            if (cycle == Cycle.ERROR) throw exception;
        }
    }
}
