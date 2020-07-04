package com.xcosy.java.thread.observer;

import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        Observable observableThread = new ObservableThread<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "123";
        });
        observableThread.start();
    }

}
