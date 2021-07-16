package com.wangjun.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MS_TIME_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String DATE_SECOND_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_MINUTES_TIME_FORMAT = "yyyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_MONTH_FORMAT = "yyyy-MM";

    /**
     * 获得当前时间，格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDate() {
        return getCurrentDate(DATE_TIME_FORMAT);
    }

    /**
     * 获得当前时间，格式自定义
     */
    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(day.getTime());
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param calendar
     * @param format
     * @return
     */
    public static String formatDate(Calendar calendar, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * 生成日期
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private static Date parseDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar currentDate = new GregorianCalendar();

        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, month);
        currentDate.set(Calendar.DAY_OF_MONTH, day);
        currentDate.set(Calendar.HOUR_OF_DAY, hour);
        currentDate.set(Calendar.MINUTE, minute);
        currentDate.set(Calendar.SECOND, second);
        currentDate.set(Calendar.MILLISECOND, 0);

        return currentDate.getTime();
    }

    /**
     * 按照时间字符串和格式转换成Date类
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     * @author lihe 2013-7-4 下午5:21:50
     * @see
     */
    public static Date parseDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("日期转换有误" + e.getMessage(), e);
        }

        return null;
    }

    public static Date getMonthStart(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) { // 如果是空则用默认当前时间
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 从0月开始
        int day = 1;
        int hour = 0; // 0点
        int minute = 0; // 0分
        int second = 0; // 0秒

        return parseDate(year, month, day, hour, minute, second);
    }

    /**
     * 转换毫秒时间
     *
     * @return
     */
    public static Date parseMsDate(String msDate) {
        return parseDate(msDate, DATE_MS_TIME_FORMAT);
    }

    public static Date getDateForTodayStart() {
        return getDayStart(new Date());
    }

    public static Date getDateForTodayEnd() {
        return getDayEnd(new Date());
    }


    public static Date getDayStart(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Date getDayEnd(Date date) {
        date = DateUtils.setHours(date, 23);
        date = DateUtils.setMinutes(date, 59);
        date = DateUtils.setSeconds(date, 59);
        date = DateUtils.setMilliseconds(date, 0);
        return date;
    }

    public static Date getDateForHourStart(Date date) {
        return DateUtils.truncate(date, Calendar.HOUR);
    }


    /**
     * 获取时间的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取时间的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取月份的天数
     *
     * @param date
     * @return
     */
    public static int getMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据间隔秒数获得时间
     *
     * @param date
     * @param secondOffset
     * @return
     */
    public static Date getDateBySecondOffset(Date date, int secondOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, secondOffset);
        return calendar.getTime();
    }

    /**
     * 根据天数增减获得新的时间
     *
     * @param date
     * @param dateOffset
     * @return
     */
    public static Date getDateByDateOffset(Date date, int dateOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dateOffset);
        return calendar.getTime();
    }

    /**
     * 根据月份增减获得新的时间
     *
     * @param date
     * @param monthOffset
     * @return
     */
    public static Date getDateByMonthOffset(Date date, int monthOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthOffset);
        return calendar.getTime();
    }


    /**
     * 根据天数增减获得新的时间
     *
     * @param date
     * @param yearOffset
     * @return
     */
    public static Date getDateByYearOffset(Date date, int yearOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearOffset);
        return calendar.getTime();
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday
     * @return
     */
    public static Integer getAge(Date birthday) {
        if (birthday == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int startY = calendar.get(Calendar.YEAR);

        calendar.setTime(new Date());
        int endY = calendar.get(Calendar.YEAR);
        int age = endY - startY;
        return age >= 0 ? age : 0;
    }

    /**
     * 根据生日计算周岁年龄
     *
     * @param birthday
     * @return
     */
    public static int getFullAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int start = Integer.parseInt(df.format(birthday));
        int end = Integer.parseInt(df.format(new Date()));
        int fullAge = (end - start) / 10000;
        return fullAge;
    }

    /**
     * 根据年龄计算生日(一月一日起)
     *
     * @param age
     * @return
     */
    public static Date getBirthdayByAge(Integer age) {
        if (age == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        //一月一日12点
        calendar.set(calendar.get(Calendar.YEAR) - age, 1, 1, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static boolean isWork(String weekday, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = (calendar.get(Calendar.DAY_OF_WEEK) - 2 + 7) % 7;
        return "1".equals(weekday.substring(week, week + 1));
    }


    /**
     * 获得周几（周一为1）
     *
     * @param date
     * @return
     */
    public static int getMondayStartDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7;
        return week == 0 ? 7 : week;
    }

    /**
     * 获得几个月之后的日期前一天23：59：59
     *
     * @param amount
     * @return
     */
    public static Date getAfterMonthDate(int amount) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, amount);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 时间戳转为date
     *
     * @param time
     * @return
     */
    public static Date convertLongToDate(Long time) {
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000));
        return parseDate(result, "yyyy-MM-dd HH:mm:ss");

    }

    /**
     * Date转为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
