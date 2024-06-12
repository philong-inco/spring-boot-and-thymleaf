package com.java5.assignment.common;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertTime {
    public static String convert(String time){
        Instant instant = Instant.ofEpochMilli(Long.valueOf(time));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static void main(String[] args) {
        System.out.println(convert("1716359511951"));
    }
}
