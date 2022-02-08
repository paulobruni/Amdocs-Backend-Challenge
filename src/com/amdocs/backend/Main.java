package com.amdocs.backend;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args){
        System.out.println("Maximum close date: " + new SLAAnalyzer().calculateSLA(LocalDateTime.parse(LocalDateTime.of(
                2019,
                Month.AUGUST,
                3,
                13,
                42,
                31,
                066).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))),
                8));
    }
}
