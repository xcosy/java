package com.codedancing.java.jvm;

public class InnerClassTest {

    private static int name = 12;

    public static void main(String[] args) {
        printInfo(name);
        name++;
    }

    private static void printInfo(final int name) {
        InterfaceTest interfaceTest = new InterfaceTest() {
            public void print() {
                System.out.println(name);
            }
        };
        interfaceTest.print();
    }

}

interface InterfaceTest {
    void print();
}
