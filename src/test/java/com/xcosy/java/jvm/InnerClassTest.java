package com.xcosy.java.jvm;

public class InnerClassTest {

    private static int name = 12;

    public static void main(String[] args) {
        printInfo(name);
        name++;
    }

    private static void printInfo(final int name) {
        InterfaceTest interfaceTest = () -> System.out.println(name);
        interfaceTest.print();
    }

}

/**
 * Functional Interface
 */
interface InterfaceTest {
    void print();
}
