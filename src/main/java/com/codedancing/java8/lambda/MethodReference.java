package com.codedancing.java8.lambda;

import com.codedancing.java8.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReference {

    private static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
        consumer.accept(t);
    }

    public static void main(String[] args) {
        // 方式一：定义一个consumer
        Consumer<String> consumer = (s) -> System.out.println(s);
        useConsumer(consumer, "测试信息");

        // 方式二：不需要定义额外的consumer
        useConsumer((s) -> System.out.println(s), "测试信息");

        // 方式三：更加 elegant 的方式
        useConsumer(System.out::println, "测试信息");



        // =====================================================================
        // 举个例子，排序苹果，个根据苹果颜色名称
        List<Apple> appleList = Arrays.asList(new Apple("green", 120L), new Apple("red", 150L), new Apple("blue", 140L));

        // 匿名内部类方式
        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        });
        // Lambda 表达式的方式
        appleList.sort((appleA, appleB) -> appleA.getColor().compareTo(appleB.getColor()));
        // 更加elegant的方式
        appleList.sort(Comparator.comparing(Apple::getColor));

        System.out.println(appleList);
        // ======================================================================




        appleList.stream().forEach(apple -> System.out.println(apple));
        appleList.stream().forEach(System.out::println);

        // 什么情况下可以进行方法推导？

        // 举例如下：
        int value = Integer.parseInt("123");
        // 可以推导：
        Function<String, Integer> function1 = Integer::parseInt;
        Integer apply = function1.apply("123");
        System.out.println(apply);


        BiFunction<String, Integer, Character> biFunction = String::charAt;
        Character hello = biFunction.apply("Hello", 2);
        System.out.println(hello);


        String string = new String("Hello");
        Function<Integer, Character> function2 = string::charAt;
        Character apply1 = function2.apply(4);
        System.out.println(apply1);


    }

}
