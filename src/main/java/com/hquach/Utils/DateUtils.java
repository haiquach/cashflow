package com.hquach.Utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Utilities class for date calculation.
 * @author Hai Quach
 */
public class DateUtils {

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate tomorrow() {
        return today().plusDays(1);
    }

    public static LocalDate beginningThisMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate beginningNextMonth() {
        return beginningThisMonth().plusMonths(1);
    }

    public static LocalDate beginningThisYear() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDate beginningNextYear() {
        return beginningThisYear().plusYears(1);
    }

    public static LocalDate asLocalDate(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
