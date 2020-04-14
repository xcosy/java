package com.xcosy.java.jvm.classloader;

/**
 * String[] 和 int[] 的类加载器的结果都是null，
 * 但是String代表的是BootStrap ClassLoader，而int这种原始类型代表的是没有类加载器
 */
public class ClassLoadingTest6 {
    public static void main(String[] args) {
        String[] strings = new String[1];
        System.out.println(strings.getClass().getClassLoader());
        System.out.println("===================");

        ClassLoadingTest6[] classLoadingTest6s = new ClassLoadingTest6[2];
        System.out.println(classLoadingTest6s.getClass().getClassLoader());
        System.out.println("===================");

        int[] ints = new int[1];
        System.out.println(ints.getClass().getClassLoader());
    }
}
