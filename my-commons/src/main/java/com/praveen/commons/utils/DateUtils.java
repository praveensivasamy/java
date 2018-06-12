package com.praveen.commons.utils;

import static org.apache.commons.lang.time.DateUtils.addMonths;
import static org.apache.commons.lang.time.DateUtils.ceiling;
import static org.apache.commons.lang.time.DateUtils.setDays;
import static org.apache.commons.lang.time.DateUtils.toCalendar;
import static org.apache.commons.lang.time.DateUtils.truncate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

public class DateUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSS");

    /**
     * Format as dd.MM.yyyy
     */
    public static synchronized String format(Date date) {
        return formatter.format(date);
    }

    /**
     * Format as yyyy-MM-dd hh:mm:ss.SSSSS
     */
    public static synchronized String timestampFormat(Date date) {
        return dateTimeFormatter.format(date);
    }

    public static DateTime jodaDateTime(Date date) {
        return new DateTime(date);
    }

    public static Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

    /**
     * Parse the string in format dd.MM.yyyy to a {@link Date}
     */
    public static synchronized Date parse(String date) {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
                    .details("Invalid date format for " + date + ". Expected format: " + formatter.toPattern());
        }
    }

    public static synchronized String oracleFormat(Date date) {
        return "to_date('" + formatter.format(date) + "','dd.mm.yyyy')";
    }

    /**
     * @return the date formated according with the current DB implementation
     */
    public static String sqlFormat(Date date) {
        return oracleFormat(date);
    }

    /**
     * Gives the first day of the month from a given day
     */
    public static Date getFirstOfMonth(Date aDate) {
        return setDays(aDate, 0);
    }

    /**
     * Gives the last day of the month from a given day
     */
    public static Date getLastOfMonth(Date aDate) {
        return addDays(ceiling(aDate, Calendar.MONTH), -1);
    }

    public static Date getLastOfPrevMonth(Date aDate) {
        return addDays(truncate(aDate, Calendar.MONTH), -1);
    }

    public static Date plusMonths(Date aDate, int amount) {
        return addMonths(aDate, amount);
    }

    public static int get(Date aDate, int field) {
        return toCalendar(aDate).get(field);
    }

    /**
     * @return the difference in days between 2 dates
     */
    public static long diff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * @return the SQL expression for shifting the given dateField by 'shift' days
     */
    public static String shiftDate(String dateField, long shift) {
        return dateField + " + INTERVAL '" + shift + "' DAY";
    }

    public static Date prevBusinessDate(Date date) {
        Date res = date;
        Calendar cal = Calendar.getInstance();
        int dayOfWeek;
        do {
            res = org.apache.commons.lang.time.DateUtils.addDays(res, -1);
            cal.setTime(res);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        } while ((dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY));
        return res;
    }

    public static Date addDays(Date d, int shifting) {
        DateTime dateTime = new DateTime(d);
        dateTime = dateTime.plusDays(shifting);
        return dateTime.toDate();
    }

    public static int getDifference(Date date1, Date date2) {
        return Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
    }

    /**
     * Check if both dates are from same month and year
     *
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        DateTime dateTime1 = new DateTime(date1);
        DateTime dateTime2 = new DateTime(date2);
        return (dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear()) && (dateTime1.getYear() == dateTime2.getYear());
    }

    /**
     * Month as integer from date
     *
     * @param riskdate
     * @return
     */
    public static int getMonth(Date date) {
        return new DateTime(date).getMonthOfYear();
    }

    /**
     * Year as integer from date
     *
     * @param riskdate
     * @return
     */
    public static int getYear(Date date) {
        return new DateTime(date).getYear();
    }

    public static Date min(Date d1, Date d2) {
        return d1.compareTo(d2) < 0 ? d1 : d2;
    }

    public static Date max(Date d1, Date d2) {
        return d1.compareTo(d2) > 0 ? d1 : d2;
    }

    public static Date addYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusYears(years);
        return dateTime.toDate();
    }
}
