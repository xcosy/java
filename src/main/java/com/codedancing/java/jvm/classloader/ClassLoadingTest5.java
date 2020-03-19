package com.codedancing.java.jvm.classloader;

public class ClassLoadingTest5 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz1 = Class.forName("java.lang.String");
        System.out.println(clazz1.getClassLoader());

        System.out.println("=============================");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Class<?> aClass = systemClassLoader.loadClass("cn.com.garay.jvm.classloader.C");
        System.out.println(aClass);

        Class clazz2 = Class.forName("cn.com.garay.jvm.classloader.C");
        System.out.println(clazz2);
        System.out.println(clazz2.getClassLoader());
    }
}

class C {
    static {
        System.out.println("I'm C class.");
    }
}