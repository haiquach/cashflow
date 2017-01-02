package com.hquach.Utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilities class for date calculation.
 * @author Hai Quach
 */
public class DateUtils {

    public static Date getMidNight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    public static Date getNextMidNight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getFistDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getFistDayOfNextWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date getBeginThisYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    public static Date getBeginNextYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getBeginThisYear());
        System.out.println(getMidNight());
        System.out.println(getNextMidNight());
        System.out.println(getFistDayOfWeek());
        System.out.println(getFirstDayOfNextMonth());
        System.out.println(getFirstDayOfMonth());
        System.out.println(getFirstDayOfNextMonth());
    }
}
