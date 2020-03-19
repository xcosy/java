package com.codedancing.java8.functioninterface;

import com.codedancing.java8.entity.Apple;

import java.util.function.Supplier;

public class SupplierLearning {

    private static Apple createApple(Supplier<Apple> supplier) {
        //调用get()方法，此时会调用对象的构造方法，即获得到真正对象，每次get都会调用构造方法，即获取的对象不同
        return supplier.get();
    }

    public static void main(String[] args) {
        Apple resultApple = createApple(() -> new Apple("red", 150L));
        System.out.println(resultApple);
    }
}
