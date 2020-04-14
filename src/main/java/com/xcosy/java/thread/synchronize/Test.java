package com.xcosy.java.thread.synchronize;

public class Test {
    public static void main(String[] args) {

        TicketWindowRunnable ticketWindowRunnable = new TicketWindowRunnable();

        Thread window01 = new Thread(ticketWindowRunnable, "一号窗口");
        Thread window02 = new Thread(ticketWindowRunnable, "二号窗口");
        Thread window03 = new Thread(ticketWindowRunnable, "三号窗口");

        window01.start();
        window02.start();
        window03.start();
    }
}
