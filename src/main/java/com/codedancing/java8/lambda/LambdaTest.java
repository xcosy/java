package com.codedancing.java8.lambda;

import com.codedancing.java8.entity.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {

    /**
     * Runnable 接口就是FunctionalInterface，java8 的许多接口中加入了该注解
     */
    @FunctionalInterface
    public interface AppleFilter {

        /**
         * Filter
         * @param apple
         *        Apple
         * @return
         *        是否匹配
         */
        boolean filter(Apple apple);

        default void filterDefault() {
            System.out.println("FunctionalInterface Default");
        }

        static void filterStatic() {
            System.out.println("FunctionalInterface Static");
        }
    }

    public static class GreenAnd150WeightAppleFilter implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return "green".equals(apple.getColor()) && 150 <= apple.getWeight();
        }

    }

    /**
     * 策略模式
     */
    private static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                list.add(apple);
            }
        }

        return list;
    }

    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple("green", 150L), new Apple("yellow", 150L), new Apple("green", 170L));

        /*
         * 方式一：策略模式
         * 如果条件多了之后就会有许多类实现AppleFilter，这样就会比较累赘
         */
        List<Apple> lambdaApples1 = findApple(apples, new GreenAnd150WeightAppleFilter());


        /*
         * 方式二：匿名内部类
         */
        List<Apple> lambdaApples2 = findApple(apples, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor()) && apple.getWeight() < 170;
            }
        });


        /*
         * 方式三：Lambda 表达式
         * 接口必须满足条件：有且只有一个方法，会在编译期间检查该接口是否是FunctionalInterface. 接口中的 default、static 类型方法可以存在（java 8）.
         */
        List<Apple> lambdaApples3 = findApple(apples, (Apple apple) -> "green".equals(apple.getColor()));
        // 可以自动推导，只有一个参数可以去掉括号
        List<Apple> lambdaApples4 = findApple(apples, apple ->  "green".equals(apple.getColor()));



        System.out.println(lambdaApples1);
        System.out.println(lambdaApples2);
        System.out.println(lambdaApples3);
        System.out.println(lambdaApples4);




        /*
         * Runnable 接口就是 FunctionalInterface
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        /*
         * Lambda 表达式
         */
        new Thread(() -> { System.out.println(Thread.currentThread().getName()); }).start();


        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

}
