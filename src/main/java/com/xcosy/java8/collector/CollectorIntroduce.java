package com.xcosy.java8.collector;


import com.xcosy.java8.entity.Apple;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorIntroduce {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(
                new Apple("green", 150L),
                new Apple("red", 160L),
                new Apple("green", 170L),
                new Apple("red", 180L),
                new Apple("green", 190L),
                new Apple("red", 120L),
                new Apple("green", 110L)
        );

        List<Apple> green = apples.stream().filter(apple -> apple.getColor().equals("green")).collect(Collectors.toList());
        Optional.ofNullable(green).ifPresent(System.out::println);
        System.out.println("=========================GroupByNormal==========================");
        Optional.ofNullable(groupByNormal(apples)).ifPresent(System.out::println);
        System.out.println("=========================GroupByFunction==========================");
        Optional.ofNullable(groupByFunction(apples)).ifPresent(System.out::println);
        System.out.println("=========================GroupByCollector==========================");
        Optional.ofNullable(groupByCollector(apples)).ifPresent(System.out::println);

    }

    private static Map<String, List<Apple>> groupByNormal(List<Apple> apples) {
        Map<String, List<Apple>> map = new HashMap<>(2);
        for (Apple apple : apples) {
            List<Apple> list = map.computeIfAbsent(apple.getColor(), k -> new ArrayList<>());
//            和上面的语句结果相同，若key对应的value为null，会将第二个参数的返回值存入该key并返回
//            List<Apple> list = map.get(apple.getColor());
//            if (list == null) {
//                list = new ArrayList<>();
//                map.put(apple.getColor(), list);
//            }

            list.add(apple);
        }

        return map;
    }

    private static Map<String, List<Apple>> groupByFunction(List<Apple> apples) {
        Map<String, List<Apple>> map = new HashMap<>(2);
        apples.stream().forEach(apple -> {
            List<Apple> colorList = Optional.ofNullable(map.get(apple.getColor())).orElseGet(() -> {
                List<Apple> list = new ArrayList<>();
                map.put(apple.getColor(), list);
                return list;
            });

            colorList.add(apple);
        });

        return map;
    }

    private static Map<String, List<Apple>> groupByCollector(List<Apple> apples) {
        return apples.stream().collect(Collectors.groupingBy(Apple::getColor));
    }
}
