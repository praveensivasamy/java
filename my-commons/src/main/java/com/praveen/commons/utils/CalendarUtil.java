package com.praveen.commons.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class CalendarUtil {

    /**
     * Gives the last day of the month from a given day
     * 
     * @param aDate
     * @return last day in month as date
     */
    public static DateTime getLastOfMonth(DateTime aDate) {
	return new DateTime(aDate.getYear(), aDate.getMonthOfYear(), aDate.dayOfMonth().getMaximumValue(), 0, 0);
    }

    /**
     * Gives the first day of the month from a given day
     * 
     * @param aDate
     * @return first day in month as date
     */
    public static DateTime getFirstOfMonth(DateTime aDate) {
	return new DateTime(aDate.getYear(), aDate.getMonthOfYear(), aDate.dayOfMonth().getMinimumValue(), 0, 0);
    }

    /**
     * adds numberOfDays to aDate
     * 
     * @param aDate
     * @param numberOfDays
     * @return result as a new date
     */
    public static DateTime addDays(DateTime aDate, int numberOfDays) {
	return aDate.plusDays(numberOfDays);
    }

    /**
     * subtracts numberOfDays from aDate
     * 
     * @param aDate
     * @param numberOfDays
     * @return result as a new date
     */
    public static DateTime subDays(DateTime aDate, int numberOfDays) {
	return aDate.minusDays(numberOfDays);
    }

    /**
     * extracts the day of a month from a given date
     * 
     * @param aDate
     * @return day
     */
    public static long getDayOfMonth(DateTime aDate) {
	return aDate.getDayOfMonth();
    }

    /**
     * calculate the days of the year of a given date
     * 
     * @param aDate
     * @return days
     */
    public static long getDayOfYear(DateTime aDate) {
	return aDate.getDayOfYear();
    }

    /**
     * creates a new date 01.01.YYYY of a given date
     * 
     * @param aDate
     * @return a new date
     */
    public static DateTime getFirstOfYear(DateTime aDate) {
	return new DateTime(aDate.getYear(), 1, 1, 0, 0);
    }

    /**
     * creates a new date 31.12.YYYY of a given date
     * 
     * @param aDate
     * @return a new date
     */
    public static DateTime getLastOfYear(DateTime aDate) {
	return new DateTime(aDate.getYear(), 12, 31, 0, 0);
    }

    /**
     * calculates the amount of days in a year of a given date
     * 
     * @param aDate
     * @return 365/366
     */
    public static long getNumDaysInYear(DateTime aDate) {
	return aDate.dayOfYear().getMaximumValue();
    }

    /**
     * extracts the year of a given date
     * 
     * @param aDate
     * @return year
     */
    public static final long getYear(DateTime aDate) {
	return aDate.getYear();
    }

    /**
     * calculates the amount of days in a year of a given date
     * 
     * @param year
     * @return 365/366
     */
    public static final int getDaysOfYear(int year) {
	return new DateTime(year, 1, 1, 0, 0).year().getMaximumValue();
    }

    /**
     * Tests if aDate is the last day of the month
     * 
     * @param aDate
     * @return true if aDate is the last day in month
     */
    public static boolean isLastOfMonth(DateTime aDate) {
	return (aDate.dayOfMonth().getMaximumValue() == aDate.getDayOfMonth());
    }

    /**
     * calculates the days between to given dates
     * 
     * @param aDate1
     * @param aDate2
     * @return absolute difference
     */
    public static long getDayDiff(DateTime aDate1, DateTime aDate2) {
	return Math.abs(Days.daysBetween(aDate1, aDate2).getDays());
    }

    /**
     * Tests if aDate is in a leap year
     * 
     * @param aDate
     * @return true if year is leap
     */
    public static final boolean isLeapYear(DateTime aDate) {
	return aDate.year().isLeap();
    }

}
