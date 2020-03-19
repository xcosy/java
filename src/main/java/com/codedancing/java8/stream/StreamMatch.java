package com.codedancing.java8.stream;

import java.util.Arrays;
import java.util.stream.Stream;
 /**
 * Stream Match: allMatch, anyMatch, noneMatch
 *
 * @author  JiaLei
 */
public class StreamMatch {

    public static void main(String[] args) {

        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 5, 6, 8, 3, 2, 4});
        boolean a = stream.allMatch(i -> i > 0);
        System.out.println(a);

        Stream<Integer> stream1 = Arrays.stream(new Integer[]{1, 5, 6, 8, 3, 2, 4});
        boolean b = stream.anyMatch(i -> i > 7);
        System.out.println(b);

        Stream<Integer> stream2 = Arrays.stream(new Integer[]{1, 5, 6, 8, 3, 2, 4});
        boolean c = stream.noneMatch(i -> i < 0);
        System.out.println(c);


    }

}
