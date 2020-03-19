package com.codedancing.java8.stream;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * IntStream, DoubleStream, LongStream，这里举例为IntStream
 *
 * @author  JiaLei
 */
public class NumericStreams {

    public static void main(String[] args) {

        // Integer 数组
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        // 返回 Integer Stream
        Stream<Integer> integerStream = stream.filter(i -> i > 3);
        // 对 Integer Stream 进行操作
        Optional<Integer> reduce = integerStream.reduce(Integer::sum);
        System.out.println(reduce);

        // 注意： Integer类型 远远比 int类型 的资源占用率大，所以java提供了一个IntStream，可以方便地使用拆箱之后的结果,对内存的减少有一个非常可观的改进
        // 执行转换：
        //IntStream intStream = stream.mapToInt(i -> i.intValue());
        //int sum = intStream.filter(i -> i > 3).sum();

        // 也可以进行封箱操作：
        //Stream<Integer> boxed = intStream.boxed();


        // 勾股定理： 9 和 什么数字可以组成勾股定理
        int a = 9;
        // 定义IntStream，里面的元素是 1 - 100：
        IntStream intStream = IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0);
        intStream.forEach(System.out::println);
    }

}
