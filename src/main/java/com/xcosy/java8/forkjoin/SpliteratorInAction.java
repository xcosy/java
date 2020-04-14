package com.xcosy.java8.forkjoin;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SpliteratorInAction {

    public static void main(String[] args) {
        IntStream intStream = IntStream.rangeClosed(0, 10);
        Spliterator.OfInt spliterator = intStream.spliterator();
        Consumer<Integer> consumer = System.out::println;
        spliterator.forEachRemaining(consumer);
    }
}
