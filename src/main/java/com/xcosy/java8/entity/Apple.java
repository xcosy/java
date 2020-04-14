package com.xcosy.java8.entity;

import java.util.Optional;

/**
 * 苹果实体类，包含颜色和重量
 */
public class Apple {
    private String color;
    private Long weight;

    public Apple() { }

    public Apple(String color, Long weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public Long getWeight() {
        return weight;
    }

    public Optional<String> getColorByOptional() {
        return Optional.ofNullable(color);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

}
