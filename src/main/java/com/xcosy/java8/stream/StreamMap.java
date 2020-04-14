package com.xcosy.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Stream Map: map, flatMap
 *
 * @author  JiaLei
 */
public class StreamMap {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 9, 8, 7);

        List<Integer> collect = list.stream().map(i -> i * 2).collect(toList());
        System.out.println(collect);

        String[] words = {"Hello,", "World"};
        Stream<String[]> stream = Arrays.stream(words).map(w -> w.split(""));
        Stream<String> stringStream = stream.flatMap(Arrays::stream);
        stringStream.distinct().forEach(System.out::print);


    }

}
