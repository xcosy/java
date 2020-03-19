package com.codedancing.java.jvm.classloader;

public class ClassLoadingTest4 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();

        System.out.println(instance.counter1);
        System.out.println(instance.counter2);
    }
}

class Singleton {
    public static int counter1;

    private static Singleton singleton = new Singleton();

    private Singleton() {
        counter1++;
        counter2++;
    }

    public static int counter2 = 0;

    public static Singleton getInstance() {
        return singleton;
    }
}
