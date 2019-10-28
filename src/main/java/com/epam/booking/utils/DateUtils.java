package com.epam.booking.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private static final int FIRST_DAY_OF_MONTH = 1;

    public int calculateDaysBetweenDates(Date firstDate, Date secondDate) {
        long firstDateTime = firstDate.getTime();
        long secondDateTime = secondDate.getTime();
        long difference = Math.abs(firstDateTime - secondDateTime);
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    public Date getCurrentDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        resetTime(calendar);
        return calendar.getTime();
    }

    public Date getCurrentMonthAndYear() {
        Calendar calendar = Calendar.getInstance();
        resetTime(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, FIRST_DAY_OF_MONTH);
        return calendar.getTime();
    }

    public boolean isBetweenDates(Date date, Date start, Date end) {
        return date.after(start) && date.before(end) || date.equals(start) || date.equals(end);
    }

    private void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
