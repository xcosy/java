package com.xcosy.java.jvm.classloader;

/*
 * 线程上下文加载器的一般使用模式：（获取 - 使用 - 还原）
 *
 * ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 * try {
 *     Thread.currentThread().setContextClassLoader(targetCl);
 *     myMethod();
 * } finally {
 *     Thread.currentThread().setContextClassLoader(classLoader);
 * }
 *
 * myMethod() 里面则调用了Thread.currentThread().getContextClassLoader() ，获取当前线程的上下文类加载器。
 * 如果一个类由类加载器A加载，那么这个类的依赖类也是由相同的类加载器加载的（如果该依赖类之前没有被加载过的话）
 * ContextClassLoader 的作用就是为了破坏Java的类加载模式 —— 双亲委托机制。
 * 当高层提供了统一的接口让低层去实现，同时又要在高层加载（或实例化）低层的类时，就必须要通过线程上下文类加载器来帮助高层的ClassLoader找到并加载该类。
 */

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ClassLoadingTest8 {
    public static void main(String[] args) {
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println("driver: " + driver + ", classloader: " + driver.getClass().getClassLoader());
        }

        System.out.println("当前线程上下文类加载器： " + Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader 的类加载器： " + ServiceLoader.class.getClassLoader());
    }
}
