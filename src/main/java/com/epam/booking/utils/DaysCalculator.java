package com.epam.booking.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DaysCalculator {

    public int calculateDaysBetweenDates(Date firstDate, Date secondDate) {
        long firstDateTime = firstDate.getTime();
        long secondDateTime = secondDate.getTime();
        long difference = Math.abs(firstDateTime - secondDateTime);
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

}
