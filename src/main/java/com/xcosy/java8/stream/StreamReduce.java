package com.xcosy.java8.stream;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Stream Reduce: 聚合操作，给定一个函数操作
 *
 * @author JiaLei
 */
public class StreamReduce {

    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 5, 3, 6, 7, 8});

        // 求和
        Integer result10 = stream.reduce(0, (i, j) -> i + j);
        //Integer result11 = stream.reduce(0, Integer::sum);
        System.out.println(result10);

        // 计算最大
        stream = Arrays.stream(new Integer[]{1, 5, 3, 6, 7, 8});
        stream.reduce((i, j)-> i > j ? i : j).ifPresent(System.out::println);
        //stream.reduce(Integer::max).ifPresent(System.out::print);

        // 计算最大
        stream = Arrays.stream(new Integer[]{1, 5, 3, 6, 7, 9});
        stream.reduce((i, j) -> i > j ? j : i).ifPresent(System.out::println);
        //stream.reduce(Integer::min).ifPresent(System.out::print);

        // 计算积
        stream = Arrays.stream(new Integer[] {1, 5, 3, 6, 7, 10});
        Integer reduce = stream.filter(i -> i % 2 == 0).reduce(1, (i, j) -> i * j);
        Optional.of(reduce).ifPresent(System.out::println);

    }

}
