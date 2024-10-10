package com.iris.blog.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

 /**
 * @author lstar
 * @create 2023-04
 * @description: 日期处理类
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	public final static String DATE_PATTERN1 = "yyyy-MM";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(LocalDateTime date) {
        return format(date, DATE_PATTERN);
    }
	public static String format(LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        return format(dateTime, DATE_PATTERN1);
    }

    public static String format(LocalDateTime date, String pattern) {
        if(date != null){
            DateTimeFormatter df =  DateTimeFormatter.ofPattern(pattern);
            return df.format(date);
        }
        return null;
    }
}
