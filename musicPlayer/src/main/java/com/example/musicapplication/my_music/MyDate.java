package com.example.musicapplication.my_music;

import android.annotation.SuppressLint;

import java.util.Calendar;
import java.util.TimeZone;

public class MyDate {
    private int year, month, day, h, m, s;

    public MyDate(int year, int month, int day, int h, int m, int s) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public MyDate(){
        this(Calendar.getInstance(TimeZone.getTimeZone("UTC+8")));
    }

    public MyDate(Calendar c){
        this(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH)+1,
                c.get(Calendar.DATE)-1,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND)
        );
    }

    /**
     * 打包方法：直接打包成字符串
     *
     * @param msg
     */
    public MyDate(String msg) {
        this(msg.charAt(0), msg.charAt(1), msg.charAt(2), msg.charAt(3), msg.charAt(4), msg.charAt(5));
    }

    @SuppressLint("DefaultLocale")
    public String getDetailTime() {
        return String.format("%d,%2d,%2d %02d:%02d:%02d", year, month, day, h, m, s);
    }

    @SuppressLint("DefaultLocale")
    public String getDay() {
        return String.format("%d,%d,%d", year, month, day);
    }
}
