package com.xcosy.java8.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * RecursiveTask 和 RecursiveAction 两种方式
 *
 * @author  JiaLei
 */
public class ForkJoinPoolTest {

    private static int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};

    public static void main(String[] args) {
        // 原始方式
        System.out.println("The result is " + calculate(data));

        // RecursiveTask
        AccumulatorRecursiveTask task  = new AccumulatorRecursiveTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(task);
        System.out.println("The AccumulatorRecursiveTask Result is " + result);

        // RecursiveAction
        AccumulatorRecursiveAction action = new AccumulatorRecursiveAction(0, data.length, data);
        forkJoinPool.invoke(action);
        System.out.println("The AccumulatorRecursiveAction Result is " + AccumulatorRecursiveAction.AccumulatorHelper.getResult());
    }

    private static int calculate(int[] data) {
        int result = 0;
        for (int i : data) {
            result += i;
        }
        return result;
    }

}
