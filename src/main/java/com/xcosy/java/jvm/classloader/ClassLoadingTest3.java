package com.xcosy.java.jvm.classloader;

public class ClassLoadingTest3 {

    public static void main(String[] args) {
        System.out.println(MyChild3.childNum3);
    }

}

interface MyParent3 {
    int parentNum3 = 5;
}

interface MyChild3 extends MyParent3 {
    int childNum3 = 6;
}