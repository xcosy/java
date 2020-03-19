package com.codedancing.java8.optional;

import com.codedancing.java8.entity.Apple;

import java.util.Optional;

/**
 * Optional 应该怎样用？
 * 在使用 Optional 的时候需要考虑一些事情，以决定什么时候怎样使用它。
 * 重要的一点是 Optional 不是 Serializable。因此，它不应该用作类的字段。
 * 如果你需要序列化的对象包含 Optional 值，Jackson 库支持把 Optional 当作普通对象。也就是说，Jackson 会把空对象看作 null，而有值的对象则把其值看作对应域的值。这个功能在 jackson-modules-java8 项目中。
 *
 * Optional 主要用作返回类型。在获取到这个类型的实例后，如果它有值，你可以取得这个值，否则可以进行一些替代行为。
 * Optional 类有一个非常有用的用例，就是将其与流或其它返回 Optional 的方法结合，以构建流畅的API。
 *
 * Optional 是 Java 语言的有益补充 —— 它旨在减少代码中的 NullPointerExceptions，虽然还不能完全消除这些异常。
 *
 * @author  JiaLei
 */
public class OptionalUsage {

    public static void main(String[] args) {

        /**
         * 构造Optional实体：1.empty; 2.of; 3.ofNullable
         */
        Optional<Apple> emptyOptional = Optional.empty();
        Optional<Apple> redAppleOptional = Optional.of(new Apple("red", 120L));
        // null 值作为参数传递进去，如果是of()方法会抛出 NullPointerException，如果对象可能是null则使用 ofNullable() 方法
        Optional<Apple> nullOptional = Optional.ofNullable(null);

        /**
         * 获取值
         */
        redAppleOptional.get();
        // java.util.NoSuchElementException: No value present
//        emptyOptional.get();
//        nullOptional.get();


        /**
         * 判断是否有值
         */
        // 判断是否有值,接受一个 Consumer(消费者) 参数，如果对象不是空的，就对执行传入的 Lambda 表达式
        System.out.println(nullOptional.isPresent());
        redAppleOptional.ifPresent(System.out::println);
        redAppleOptional.ifPresent(apple -> System.out.println(apple.getColor()));


        /**
         * 返回默认值
         */
        // 如果有值则返回该值，否则返回传递给它的参数值，如果对象的初始值不是 null，那么默认值会被忽略
        redAppleOptional.orElse(new Apple());
        // 有值的时候返回值，如果没有值，它会执行作为参数传入的 Supplier(供应者) 函数式接口，并将返回其执行结果
        redAppleOptional.orElseGet(Apple::new);

        // orElse 和 orElseGet 的比较：
        // 1. 当对象为空而返回默认对象时，行为并无差异，都是创建默认值并返回
        // 2. 当对象不为空，orElse() 方法仍然创建了 默认值 对象。与之相反，orElseGet() 方法不创建 默认值 对象。
        // 在执行较密集的调用时，比如调用 Web 服务或数据查询，这个差异会对性能产生重大影响。


        // 如果有值就传出原来的值，对象为空的时候抛出异常
        redAppleOptional.orElseThrow(RuntimeException::new);
        redAppleOptional.orElseThrow(() -> new RuntimeException("RuntimeException"));


        /**
         * 转化值：1.map; 2.flatMap;
         */
        //map
        Optional<Apple> greenAppleOptional = Optional.ofNullable(new Apple("green", 150L));
        String appleColor = greenAppleOptional.map(Apple::getColor).orElse("UNKNOWN");
        System.out.println(appleColor);

        Apple apple = new Apple("blue", 130L);
        Optional<Apple> opt = Optional.ofNullable(null);
        String flatMap = opt.flatMap(Apple::getColorByOptional).orElse("UNKNOWN");
        System.out.println(flatMap);

    }

}
