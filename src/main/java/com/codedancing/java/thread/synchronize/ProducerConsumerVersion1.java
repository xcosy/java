package com.codedancing.java.thread.synchronize;
/**
 * 不适合多生产者、多消费者
 *
 * @author  JiaLei
 */
public class ProducerConsumerVersion1 {

    private final Object LOCK = new Object();

    private int i;

    private volatile boolean isProduce = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->" + i);
                LOCK.notify();
                isProduce = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduce) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduce = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ProducerConsumerVersion1Test {
    public static void main(String[] args) {
        ProducerConsumerVersion1 pc = new ProducerConsumerVersion1();

        new Thread("Producer") {
            @Override
            public void run() {
                 while (true)
                   pc.produce();
            }
        }.start();

        new Thread("Consumer") {
            @Override
            public void run() {
                while (true)
                    pc.consume();
            }
        }.start();

    }
}
