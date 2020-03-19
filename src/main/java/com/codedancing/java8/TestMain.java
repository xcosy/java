package com.codedancing.java8;

import java.util.ArrayList;
import java.util.LinkedList;

public class TestMain {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);

        list.remove(null);
        System.out.println(list.size());

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add(null);
        linkedList.add(null);
        linkedList.add(null);

        linkedList.remove(null);
        System.out.println(linkedList.size());

    }

}