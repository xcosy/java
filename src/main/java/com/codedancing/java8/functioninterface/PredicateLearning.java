package com.codedancing.java8.functioninterface;

import com.codedancing.java8.entity.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

public class PredicateLearning {

    /**
     * 使用Predicate接口,还用IntPredicate、LongPredicate、DoublePredicate，都是对于泛型类的具体类型实现，BiPredicate可以比较两个条件
     */
    private static List<Apple> findAppleByPredicate(List<Apple> apples, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    private static List<Apple> findAppleByIntPredicate(List<Apple> apples, LongPredicate predicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (predicate.test(apple.getWeight())) {
                result.add(apple);
            }
        }

        return result;
    }

    private static List<Apple> findAppleByBiPredicate(List<Apple> apples, BiPredicate<String, Long> predicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (predicate.test(apple.getColor(), apple.getWeight())) {
                result.add(apple);
            }
        }

        return result;
    }

    public static void main(String[] args) {

        List<Apple> apples = Arrays.asList(new Apple("green", 150L), new Apple("yellow", 150L), new Apple("green", 170L));
        /*
         * 使用 Predicate 接口
         */
        List<Apple> lambdaApples = findAppleByPredicate(apples, apple -> apple.getColor().equals("yellow"));

        /*
         * 使用 LongPredicate 接口
         */
        List<Apple> lambdaApples1 = findAppleByIntPredicate(apples, w -> w > 150);

        /*
         * 使用 BiPredicate 接口
         */
        List<Apple> lambdaApples2 = findAppleByBiPredicate(apples, (color, weight) -> color.equals("green") && weight > 150);


        System.out.println(lambdaApples);
        System.out.println(lambdaApples1);
        System.out.println(lambdaApples2);
    }
}
