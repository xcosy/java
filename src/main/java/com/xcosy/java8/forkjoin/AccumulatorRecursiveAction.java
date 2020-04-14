package com.xcosy.java8.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 相对于 RecursiveTask 而言，RecursiveAction 没有返回值(即，泛型指定为void， public abstract class RecursiveAction extends ForkJoinTask<Void>)
 *
 * @author  JiaLei
 */
public class AccumulatorRecursiveAction extends RecursiveAction {

    private final int start;
    private final int end;
    private final int[] data;
    private final int LIMIT = 3;

    public AccumulatorRecursiveAction(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected void compute() {
        if ((end - start) <= LIMIT) {
            for (int i = start; i < end; i++) {
              AccumulatorHelper.accumulate(data[i]);
            }
        } else {
            int mid = (start + end) / 2;
            AccumulatorRecursiveAction left = new AccumulatorRecursiveAction(start, mid, data);
            AccumulatorRecursiveAction right = new AccumulatorRecursiveAction(mid, end, data);
            left.fork();
            right.fork();
            left.join();
            right.join();
        }
    }

    static class AccumulatorHelper {
        private static final AtomicInteger result = new AtomicInteger(0);

        /**
         * 可作为包可见的
         */
        static void accumulate(int value) {
            result.getAndAdd(value);
        }

        public static int getResult() {
            return result.get();
        }

        /**
         * 清空结果集
         */
        void reset() {
            result.set(0);
        }
    }
}
