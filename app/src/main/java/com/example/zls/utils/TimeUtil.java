package com.example.zls.utils;

import com.example.zls.R;

import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.Temporal;

import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by chaichuanfa on 2019/1/15
 */
public final class TimeUtil {

    private static Context mContext = null;

    private TimeUtil() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static ZonedDateTime utc2DefaultZone(ZonedDateTime utcDateTime) {
        return utcDateTime.withZoneSameInstant(ZoneId.systemDefault());
    }

    /**
     * 获取已经过去的秒数
     */
    public static long getPastSeconds(Temporal now, Temporal past) {
        Duration duration = Duration.between(past, now);
        return Math.max(duration.getSeconds(), 0);
    }

    /**
     * 将ZonedDateTime格式化为"yyyy-MM-dd"
     */
    public static String zoneDate2String(ZonedDateTime time) {
        return mContext.getString(R.string.date_year_month_day_formatter, time.getYear(),
                time.getMonthValue(), time.getDayOfMonth());
    }

    /**
     * 将格式化日期"yyyy-MM-dd"转为ZonedDateTime
     */
    public static ZonedDateTime string2ZoneDate(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(time, formatter);
        ZonedDateTime dateTime = date.atStartOfDay(ZoneId.systemDefault());
        return dateTime;
    }

    /**
     * 将格式化日期"yyyy-MM-dd HH:mm:ss"转为ZonedDateTime
     */
    public static ZonedDateTime parse(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime
                .parse(time, formatter)
                .atZone(ZoneId.systemDefault());
    }

    public static String getDisplayTime(ZonedDateTime time) {
        ZonedDateTime now = ZonedDateTime.now();
        if (time.getYear() == time.getYear()) {
            if (time.getDayOfYear() == now.getDayOfYear()) {
                return mContext
                        .getString(R.string.text_time_duration_mmss_formatter, time.getHour(),
                                time.getMinute());
            } else if (time.getDayOfYear() + 1 == now.getDayOfYear()) {
                return mContext.getString(R.string.text_time_within_two_days);
            } else if (time.getDayOfYear() - 1 == now.getDayOfYear()) {
                return mContext.getString(R.string.text_time_tomorrow);
            } else {
                return mContext.getString(R.string.text_time_duration_mmdd_formatter,
                        time.getMonthValue(), time.getDayOfMonth());
            }
        } else {
            return getSimpleDate(time);
        }
    }

    public static String getDisplayTimeDetail(ZonedDateTime now, ZonedDateTime time) {
        if (time.getYear() == time.getYear()) {
            if (time.getDayOfYear() == now.getDayOfYear()) {
                return mContext.getString(R.string.text_time_duration_mmss_formatter,
                        time.getHour(), time.getMinute());
            } else if (time.getDayOfYear() + 1 == now.getDayOfYear()) {
                return mContext.getString(R.string.text_time_within_two_days_formatter,
                        time.getHour(), time.getMinute());
            } else if (time.getDayOfYear() - 1 == now.getDayOfYear()) {
                return mContext.getString(R.string.text_time_tomorrow_formatter, time.getHour(),
                        time.getMinute());
            } else {
                return mContext.getString(R.string.text_time_duration_mmdd_formatter,
                        time.getMonthValue(), time.getDayOfMonth()) + " " + mContext
                        .getString(R.string.text_time_duration_mmss_formatter, time.getHour(),
                                time.getMinute());
            }
        } else {
            return getSimpleDateWithHour(time);
        }
    }

    /**
     * get simple date string, emit year for time in this year
     *
     * @param date date to represent
     * @return simple represent string "2017/03/01"
     */
    public static String getSimpleDate(ZonedDateTime date) {
        return mContext.getString(R.string.simple_date_str_split_formatter, date.getYear(),
                date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * get simple date string, emit year for time in this year
     *
     * @param date date to represent
     * @return simple represent string "2017/03/01 21:12"
     */
    public static String getSimpleDateWithHour(ZonedDateTime date) {
        return mContext.getString(R.string.simple_date_str_split_with_hour_formatter,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(),
                date.getMinute());
    }

    /**
     * get simple date string, emit year for time in this year
     *
     * @param date date to represent
     * @return simple represent string "2017-03-01 21:12:00"
     */
    public static String getSimpleFormatDate(ZonedDateTime date) {
        return mContext.getString(R.string.simple_date_str_formatter,
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(),
                date.getMinute(), date.getSecond());
    }

    public static ZonedDateTime zonedDate(long millis) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public static ZonedDateTime ofEpochSecond(long epochSecond) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.systemDefault());
    }

    /**
     * get the result to check if the past datetime have past enough time
     *
     * @param now      the standard you want to compare
     * @param past     the target datetime you want to compare
     * @param timeUnit the {@code Calendar} field to compare.
     * @return the result of compare now with past
     */
    public static boolean havePastTime(ZonedDateTime now, ZonedDateTime past, TimeUnit timeUnit,
            int value) {
        Duration duration = Duration.between(past, now);
        if (duration.isNegative() || duration.isZero()) {
            return false;
        }
        if (timeUnit == TimeUnit.DAYS) {
            return duration.toDays() >= value;
        } else if (timeUnit == TimeUnit.HOURS) {
            return duration.toHours() >= value;
        } else if (timeUnit == TimeUnit.MINUTES) {
            return duration.toMinutes() >= value;
        } else {
            return timeUnit == TimeUnit.SECONDS && duration.toMillis() / 1000 >= value;
        }
    }

    /**
     * false past是相对于now之前的时间
     */
    public static boolean havePast(ZonedDateTime now, ZonedDateTime past) {
        Duration duration = Duration.between(past, now);
        return duration.isNegative() || duration.isZero();
    }

    /**
     * Calculate age according to time
     */
    public static int getAgeByTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        int age = 0;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate date = LocalDate.parse(time, formatter);
            ZonedDateTime dateTime = date.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime now = ZonedDateTime.now();
            if (now.isBefore(dateTime)) {
                return age;
            } else {
                int yearNow = now.getYear();
                int monthNow = now.getMonthValue();
                int dayOfMonthNow = now.getDayOfMonth();

                int yearBirth = dateTime.getYear();
                int monthBirth = dateTime.getMonthValue();
                int dayOfMonthBirth = dateTime.getDayOfMonth();

                age = yearNow - yearBirth;
                if (monthNow <= monthBirth) {
                    if (monthNow == monthBirth) {
                        if (dayOfMonthNow < dayOfMonthBirth) {
                            age--;
                        }
                    } else {
                        age--;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return age;
    }

    /**
     * seconds formatter
     */
    public static String duration2String(long duration) {
        //CHECKSTYLE:OFF
        int hour = (int) (duration / 3600);
        int minute = (int) ((duration % 3600) / 60);
        int second = (int) ((duration % 3600) % 60);
        int day = hour / 24;
        int year = day / 365;
        if (year != 0) {
            return String.format(Locale.US,
                    mContext.getResources().getString(R.string.text_time_year_formatter), year);
        } else if (day != 0) {
            return String.format(Locale.US,
                    mContext.getResources().getString(R.string.text_time_day_formatter), day);
        } else if (hour == 0) {
            return String.format(Locale.US,
                    mContext.getResources().getString(R.string.text_time_duration_mmss_formatter),
                    minute % 60, second % 60);
        } else {
            return String.format(Locale.US,
                    mContext.getResources().getString(R.string.text_time_duration_hhmmss_formatter),
                    hour, minute % 60, second % 60);
        }
        //CHECKSTYLE:ON
    }

    public static String taskEndTime(long duration) {
        //CHECKSTYLE:OFF
        int day = (int) (duration / 86400);
        long leftSecond = duration % 86400;
        int hour = (int) (leftSecond / 3600);
        int minute = (int) ((leftSecond % 3600) / 60);
        int second = (int) ((leftSecond % 3600) % 60);
        if (day == 0) {
            if (hour == 0) {
                return mContext.getString(R.string.task_time_no_min, minute, second);
            } else {
                return mContext.getString(R.string.task_time_no_day, hour, minute);
            }
        } else {
            return mContext.getString(R.string.task_time, day, hour, minute);
        }
        //CHECKSTYLE:ON
    }


    /**
     * Aug 28
     */
    public static String formatEnglishMonthDay(Date date, Locale locale) {
        return formatDate(date, "MMM d", locale);
    }

    /**
     * Aug 28
     */
    public static String formatEnglishMonthDay(ZonedDateTime zonedDateTime, Locale locale) {
        return formatZoneDateTime(zonedDateTime, "MMM d", locale);
    }

    public static String formatDisplayDate(ZonedDateTime zonedDateTime, Locale locale) {
        ZonedDateTime now = ZonedDateTime.now();
        if (now.getYear() != zonedDateTime.getYear()) {
            return formatZoneDateTime(zonedDateTime, "MMM d HH:mm yyyy", locale);
        } else if (now.getDayOfYear() != zonedDateTime.getDayOfYear()) {
            return formatZoneDateTime(zonedDateTime, "MMM d HH:mm", locale);
        }
        return formatZoneDateTime(zonedDateTime, "HH:mm", locale);
    }

    /**
     * 时间格式: “Fri Aug 28 18:08:30 2015”， 模式: “EEE MMM d HH:mm:ss yyyy”
     */
    public static String formatZoneDateTime(ZonedDateTime zonedDateTime,
            String pattern, Locale locale) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale);
        return dateTimeFormatter.format(zonedDateTime);
    }

    /**
     * 时间格式: “Fri Aug 28 18:08:30 2015”， 模式: “EEE MMM d HH:mm:ss yyyy”
     */
    public static String formatDate(Date date, String pattern, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
        return dateFormat.format(date);
    }
}
