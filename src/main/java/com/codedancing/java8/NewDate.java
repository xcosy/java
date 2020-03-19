package com.codedancing.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class NewDate {

    public static void main(String[] args) throws InterruptedException {
        /*
         * LocalDate 是 immutable(不可变的) 和 thread-safe(线程安全的)
         */
        testLocalDate();
        System.out.println("=================================");
        testLocalTime();
        System.out.println("=================================");
        combineLocalDateAndLocalTime();
        System.out.println("=================================");
        testInstant();
        System.out.println("=================================");
        testDuration();
        System.out.println("=================================");
        testPeriod();
        System.out.println("=================================");
        testDateFormat();
        System.out.println("=================================");
        testDateParse();
    }

    private static void testLocalDate() {
        LocalDate localDate = LocalDate.of(2019, 2, 18);
        System.out.println("getYear: " + localDate.getYear());
        System.out.println("getMonth: " + localDate.getMonth());
        System.out.println("getMonthValue: " + localDate.getMonthValue());
        System.out.println("getDayOfYear: " + localDate.getDayOfYear());
        System.out.println("getDayOfMonth: " + localDate.getDayOfMonth());
        System.out.println("getDayOfWeek: " + localDate.getDayOfWeek());
        System.out.println("now: " + LocalDate.now());

        System.out.println("get ChronoField.DAY_OF_YEAR: " + localDate.get(ChronoField.DAY_OF_YEAR));
    }


    private static void testLocalTime() {
        LocalTime time = LocalTime.now();
        System.out.println("getHour: " + time.getHour());
        System.out.println("getMinute: " + time.getMinute());
        System.out.println("getSecond: " + time.getSecond());
    }

    private static void combineLocalDateAndLocalTime() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        LocalDateTime of = LocalDateTime.of(date, time);
        System.out.println(of.toString());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

    }

    private static void testInstant() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1_000);
        Instant end = Instant.now();
        Duration between = Duration.between(start, end);
        System.out.println(between.toMillis());
    }

    private static void testDuration() {
        LocalTime now = LocalTime.now();
        LocalTime localTime = now.minusHours(1);
        Duration between = Duration.between(now, localTime);
        System.out.println(between.toMillis());
    }

    /**
     * Period 只能比较30天之内的时间差
     */
    private static void testPeriod() {
        Period between = Period.between(LocalDate.of(2018, 1, 23), LocalDate.of(2019, 2, 25));
        System.out.println(between.getDays());
        System.out.println(between.getMonths());
    }

    private static void testDateFormat() {
        LocalDate now = LocalDate.now();
        String format = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        String format1 = now.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(format);
        System.out.println(format1);
    }

    private static void testDateParse() {
        String date1 = "20161113";
        LocalDate parse = LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(parse);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date2 = "2019-11-13";
        LocalDate parse1 = LocalDate.parse(date2, dateTimeFormatter);
        System.out.println(parse1);
    }


}
