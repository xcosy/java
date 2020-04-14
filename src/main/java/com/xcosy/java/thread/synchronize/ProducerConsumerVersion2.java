package com.xcosy.java.thread.synchronize;

import java.util.stream.Stream;

/**
 * 多生产者、多消费者
 *
 * @author  JiaLei
 */
public class ProducerConsumerVersion2 {

    private final Object LOCK = new Object();

    private int i;

    private volatile boolean isProduce = false;

    public void produce() {
        synchronized (LOCK) {
            while (isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i++;
            System.out.println("P->" + i);
            LOCK.notifyAll();
            isProduce = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            while (!isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("C->" + i);
            LOCK.notifyAll();
            isProduce = false;
        }
    }
}

class ProducerConsumerVersion2Test {
    public static void main(String[] args) {
        ProducerConsumerVersion2 pc = new ProducerConsumerVersion2();

        Stream.of("producer01", "producer02", "producer03").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while (true)
                        pc.produce();
                }
            }.start();
        });

        Stream.of("consumer01", "consumer02", "consumer03").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while (true)
                        pc.consume();
                }
            }.start();
        });

    }
}
