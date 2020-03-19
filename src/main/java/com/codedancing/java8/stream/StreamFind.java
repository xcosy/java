package com.codedancing.java8.stream;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Stream Find: findAny, findFirst
 *
 * @author  JiaLei
 */
public class StreamFind {

    public static void main(String[] args) {
        Stream<Integer> stream0 = Arrays.stream(new Integer[]{0, 2, 65, 7, 4, 3});
        Optional<Integer> any0 = stream0.filter(i -> i > 6).findAny();
        System.out.println(any0.get());

        Stream<Integer> stream1 = Arrays.stream(new Integer[]{0, 2, 65, 7, 4, 3});
        Optional<Integer> any1 = stream1.filter(i -> i > 10).findFirst();
        System.out.println(any1.orElse(-1));

        Stream<Integer> stream2 = Arrays.stream(new Integer[]{0, 2, 65, 7, 4, 3});
        Optional<Integer> any2 = stream2.filter(i -> i < 0).findAny();
        any2.ifPresent(System.out::println);

    }

}
