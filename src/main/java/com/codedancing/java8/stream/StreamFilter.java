package com.codedancing.java8.stream;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Filter 其实就是传递一个Predicate，进行test，经过处理如果结果返回true就保留在下一个stream中，false则会过滤掉
 *
 * @author  JiaLei
 */
public class StreamFilter {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 6, 3, 2, 6, 7, 8);

        List<Integer> collect = list.stream().filter(i -> i % 2 == 0).collect(toList());
        System.out.println(collect);
        // [6, 2, 6, 8]

        List<Integer> collect1 = list.stream().distinct().collect(toList());
        System.out.println(collect1);
        // [1, 6, 3, 2, 7, 8]

        List<Integer> collect2 = list.stream().skip(5).collect(toList());
        System.out.println(collect2);
        // [7, 8]

        List<Integer> collect3 = list.stream().sorted().collect(toList());
        System.out.println(collect3);
        // [1, 2, 3, 6, 6, 7, 8]

        List<Integer> collect4 = list.stream().limit(5).collect(toList());
        System.out.println(collect4);
        // [1, 6, 3, 2, 6]

    }

}
