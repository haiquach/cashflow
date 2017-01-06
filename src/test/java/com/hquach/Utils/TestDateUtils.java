package com.hquach.Utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * JUnit test for date utils
 */
public class TestDateUtils {
    @Test
    public void date() {
        LocalDate now = LocalDate.now();
        Assert.assertEquals("Today: " + now, now, DateUtils.today());
        Assert.assertEquals("Tomorrow: " + now.plusDays(1), now.plusDays(1), DateUtils.tomorrow());

        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        Assert.assertEquals("First Day of This Month: " + firstDayOfMonth,
                firstDayOfMonth, DateUtils.beginningThisMonth());
        Assert.assertEquals("First Day of Next Month: " + firstDayOfMonth.plusMonths(1),
                firstDayOfMonth.plusMonths(1), DateUtils.beginningNextMonth());

        LocalDate firstDayOfYear = now.withDayOfYear(1);
        Assert.assertEquals("First Day of This Year: " + firstDayOfYear,
                firstDayOfYear, DateUtils.beginningThisYear());
        Assert.assertEquals("First Day of Next Year: " + firstDayOfYear.plusYears(1),
                firstDayOfYear.plusYears(1), DateUtils.beginningNextYear());
    }
}
