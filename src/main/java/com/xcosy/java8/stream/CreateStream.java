package com.xcosy.java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreateStream {

    public static void main(String[] args) {
        createStreamFromCollection().forEach(System.out::println);
        createStreamFromValues().forEach(System.out::println);
        createStreamFromArrays().forEach(System.out::println);
        createStreamFromFile();
    }

    /**
     * 集合
     */
    private static Stream<String> createStreamFromCollection() {
        List<String> strings = Arrays.asList("Hello", "World", "Java", "Nice", "2019");
        return strings.stream();
    }

    /**
     * Values
     */
    private static Stream<String> createStreamFromValues() {
        return Stream.of("Hello", "World", "Java", "Nice", "2019");
    }

    /**
     * 数组
     */
    private static Stream<String> createStreamFromArrays() {
        return Arrays.stream(new String[]{"Hello", "World", "Java", "Nice", "2019"});
    }

    /**
     * 文件
     */
    private static Stream<String> createStreamFromFile() {
        Path path = Paths.get("D:\\IDEA\\WorkSpace\\Learning\\Java-8\\src\\main\\java\\cn\\com\\garay\\stream\\CreateStream.java");
        try (Stream<String> fileStream = Files.lines(path)) {
            fileStream.forEach(System.out::println);
            return fileStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Iterator
     */
    private static Stream<Integer> createStreamFromIterator() {
        Stream<Integer> stream = Stream.iterate(0, n -> n + 2).limit(10);
        return stream;
    }

    /**
     * generator
     */
    private static Stream<Double> createStreamFromGenerator() {
        return Stream.generate(Math::random).limit(10);
    }

}
