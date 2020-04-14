package com.xcosy.java.jvm.classloader;

/**
 * 常量在编译阶段就会存入到调用这个常量的方法所在的类的常量池中，
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化
 * 注意：这里指的是将常量存入到了MyTest2的常量池中，之后MyTest2与MyParent2就没有任何关系了，
 * 甚至，我们可以将MyParent2的class文件删除
 */
public class ClassLoadingTest2 {
    public static void main(String[] args) {
        System.out.println(MyParent2.STRING);
        System.out.println(MyParent2.int1);
        System.out.println(MyParent2.short1);
        System.out.println(MyParent2.long1);
        System.out.println(MyParent2.float1);
        System.out.println(MyParent2.double1);

        MyParent2[] myParent2s = new MyParent2[1];
        System.out.println(myParent2s.getClass());
        System.out.println(myParent2s.getClass().getSuperclass());

        int[] ints = new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());

        boolean[] booleans = new boolean[1];
        System.out.println(booleans.getClass());
        System.out.println(booleans.getClass().getSuperclass());
    }
}

class MyParent2 {
    public static final String STRING = "Hello World.";
    public static final int int1 = 128;
    public static final short short1 = 127;
    public static final short int6 = 6;
    public static final long long1 = 128;
    public static final float float1 = 128;
    public static final double double1 = 128;

    static {
        System.out.println("MyParent2 static block.");
    }
}
