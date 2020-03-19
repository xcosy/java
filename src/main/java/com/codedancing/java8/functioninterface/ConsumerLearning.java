package com.codedancing.java8.functioninterface;

import com.codedancing.java8.entity.Apple;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Consumer接口，接收一个泛型类型，例如Object 或者 Resource,可以进行操作
 */
public class ConsumerLearning {
    private static void findAppleByConsumer(List<Apple> apples, Consumer<Apple> consumer) {
        for (Apple apple : apples) {
            consumer.accept(apple);
        }
    }

    private static void findAppleByBiConsumer(String preString, List<Apple> apples, BiConsumer<Apple, String> consumer) {
        for (Apple apple : apples) {
            consumer.accept(apple, preString);
        }
    }

    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple("green", 150L), new Apple("yellow", 150L), new Apple("green", 170L));

        findAppleByConsumer(apples, apple -> System.out.println(apple));

        findAppleByBiConsumer("苹果颜色： ", apples, (apple, preString) -> System.out.println(preString + apple.getColor() + ", 重量：" + apple.getWeight() + "Kg."));
    }
}
