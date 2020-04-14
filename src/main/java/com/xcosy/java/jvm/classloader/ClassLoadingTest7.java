package com.xcosy.java.jvm.classloader;

/**
 * 当前类加载器（Current ClassLoader）
 * - 每个类都会使用自己的类加载器（即自身的类加载器）来加器其他类（即所依赖的类）
 * - 如果ClassX引用了ClassY，那么ClassX的类加载器就会去加载ClassY（前提是ClassY未被加载）
 *
 *
 * 线程上下文类加载器（Context ClassLoader）
 * - 线程上下文来加载器是从 JDK1.2 开始引入的，类 Thread 中 getContextClassLoader() 和 setContextClassLoader(ClassLoader cl)
 * 分别用来获取和设置上下文类加载器
 * - 如果没有通过setContextClassLoader(ClassLoader cl) 进行设置的话，线程将继承父线程的上下文类加载器
 * Java应用运行时的初始线程的上下文类加载器是系统类加载器。在线程中运行的代码可以通过该类加载器来加载类与资源。
 *
 *
 * 线程上下文类加载器的重要性：
 * - SPI（Service Provider Interface）
 * - 父ClassLoader可以使用线程的Thread.currentThread().getContextClassLoader()所指定的ClassLoader加载的类。
 * 这就改变了父ClassLoader不能使用ClassLoader或是其他没有直接父子关系的ClassLoader加载的类的情况，即改变了双亲委托模型。
 * - 线程上下文类加载器就是当前线程的类加载器
 * - 在双亲委托模型下，类加载就是由下至上的，即下层的类加载器会委托上层的类加载器进行加载，但是对于SPI来说，有些接口是Java核心库所提供的，而
 * Java核心库是由启动类加载器加载的，而这些接口的实现却来自于不同的Jar包（厂商提供），Java的启动类加载器是不会加载其他来源的Jar包，这样传
 * 统的双亲委托模型就无法满足SPI的要求，而通过给当前线程设置上下文类加载器，就可以由设置的上下文类加载器来实现对于接口实现类的加载。
 *
 * 例如，rt.jar 中的java.sql.Connection数据库连接类，需要依赖与MySQL或者Oracle的jar包。
 */
public class ClassLoadingTest7 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}
