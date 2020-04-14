package com.xcosy.java8.functioninterface;

import com.xcosy.java8.entity.Apple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Function<T,R> 是用的最多的FunctionInterface, 输入一个类型，返回一个类型
 */
public class FunctionLearning {

    /**
     * 输入Apple ,返回String
     */
    private static String testFunction(Apple apple, Function<Apple, String> function) {
        return function.apply(apple);
    }

    private static Apple testBiFunction(String color, long weight, BiFunction<String, Long, Apple> function) {
        return function.apply(color, weight);
    }


    public static void main(String[] args) {
        String resultString = testFunction(new Apple("red", 120L), apple -> apple.toString());
        System.out.println(resultString);

        IntFunction<Double> function = i -> i * 100D;
        double resultDouble = function.apply(10);
        System.out.println(resultDouble);

        Apple resultApple = testBiFunction("red", 180L, (color, weight) -> new Apple(color, weight));
        System.out.println(resultApple);
    }

}
