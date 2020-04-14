package com.xcosy.java8;


class Father {

    public String name = "父亲属性";

    public String name1 = "父亲属性1";

    protected String getName() {
        return name;
    }

    public static String getArea() {
        return "Father Beijing";
    }
}


public class Son extends Father {

    public String name = "儿子的属性";

    public String name1 = "儿子的属性1";

    @Override
    protected String getName() {
        return name;
    }

    public static String getArea() {
        return "Son Beijing";
    }

}