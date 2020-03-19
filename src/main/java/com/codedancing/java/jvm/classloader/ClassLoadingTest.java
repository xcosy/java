package com.codedancing.java.jvm.classloader;

/**
 *
 */
public class ClassLoadingTest {
    public static void main(String[] args) {
        System.out.println(Child.printString);
    }
}

class Parent {
    public static String printString = "parent";

    static {
        System.out.println("Parent Init");
    }
}

class Child extends Parent{
    public static String print = "child";

    static {
        System.out.println("Child Init");
    }
}
