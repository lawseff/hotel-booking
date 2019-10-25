package com.epam.booking.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public Date getCurrentDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1; // In Calendar months start at 0
    }

    public int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public boolean isBetweenDates(Date date, Date start, Date end) {
        return date.after(start) && date.before(end) || date.equals(start) || date.equals(end);
    }

}
