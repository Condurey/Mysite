package com.mysite.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     * 日期相隔天数
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    /**
     * 日期相隔小时
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 日期相隔毫秒数
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 获取当前的日期
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return getCurrentLocalDateTime().getYear();
    }

    /**
     * 获取指定年的今天日期时间
     *
     * @param year
     * @return
     */
    public static LocalDateTime withYear(int year) {
        return getCurrentLocalDateTime().withYear(year);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getMonth() {
        return getCurrentLocalDateTime().getMonthValue();
    }

    /**
     * 获取指定年的最初日期时间
     *
     * @param year
     * @return
     */
    public static LocalDateTime firstDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * @param year
     * @return String
     * @Title: getFirstDayOfThisYear
     * @Description: 获取设置所属年最初日期时间
     */
    public static String getFirstDayOfThisYear(int year) {
        LocalDateTime firstDayOfThisYear = firstDayOfThisYear(year);
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * 获取设置所属年最终日期时间
     *
     * @param year
     * @return
     */
    public static LocalDateTime lastDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

    /**
     * @param year
     * @return String
     * @Title: getLastDayOfThisYear
     * @Description: 获取设置所属年最后时间
     */
    public static String getLastDayOfThisYear(int year) {
        LocalDateTime lastDayOfThisYear = lastDayOfThisYear(year);
        return DATETIME_FORMATTER.format(lastDayOfThisYear);
    }

    /**
     * @return String
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的第一天
     */
    public static String getFirstDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = getCurrentLocalDateTime().with(TemporalAdjusters.firstDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @return String
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的最末天
     */
    public static String getLastDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = getCurrentLocalDateTime().with(TemporalAdjusters.lastDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @param days
     * @return LocalDateTime
     * @Title: plusDays
     * @Description: 当前日期向后推多少天
     */
    public static LocalDateTime plusDays(int days) {
        return getCurrentLocalDateTime().plusDays(days);
    }

    /**
     * @param year
     * @param month
     * @return LocalDateTime
     * @Title: firstDayOfWeekInYearMonth
     * @Description: 获取指定年月的第一个周一
     */
    public static LocalDateTime firstDayOfWeekInYearMonth(int year, int month) {
        return getCurrentLocalDateTime().withYear(year).withMonth(month).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    /**
     * @return LocalDateTime
     * @Title: todayStart
     * @Description: 当天开始时间
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MIN);
    }

    /**
     * @return LocalDateTime
     * @Title: todayEnd
     * @Description: 当天结束时间
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MAX);
    }

    /**
     * @return String
     * @Title: getStartDayOfWeekToString
     * @Description: 获取周第一天
     */
    public static String getStartDayOfWeekToString() {
        return formatLocalDate(getStartDayOfWeek());
    }

    public static LocalDate getStartDayOfWeek() {
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate
                .getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return getCurrentLocalDate().with(FIRST_OF_WEEK);
    }

    /**
     * @return String
     * @Title: getEndDayOfWeekToString
     * @Description: 获取周最后一天
     */
    public static String getEndDayOfWeekToString() {
        return formatLocalDate(getEndDayOfWeek());
    }

    public static LocalDate getEndDayOfWeek() {
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(
                DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return getCurrentLocalDate().with(LAST_OF_WEEK);
    }

    public static void main(String[] args) {
        //
        Integer year = 2019;
        System.out.println(getFirstDayOfThisYear(year));
        System.out.println(getLastDayOfThisYear(year));
        //
        System.out.println(DATETIME_FORMATTER.format(plusDays(1)));
        System.out.println(DATETIME_FORMATTER.format(plusDays(-1)));

        // 取第一个周一
        LocalDate ld = LocalDate.parse("2019-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(DATE_FORMATTER.format(ld));
        //
        System.out.println(DATETIME_FORMATTER.format(firstDayOfWeekInYearMonth(year, 3)));
        System.out.println("-------------------");
        // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format();
        System.out.println(getStartDayOfWeekToString());
        System.out.println(getEndDayOfWeekToString());
        System.out.println("-------------------");
        System.out.println(DATETIME_FORMATTER.format(todayStart()));
        System.out.println(DATETIME_FORMATTER.format(todayEnd()));
        System.out.println(getEndDayOfWeek());
    }


}
