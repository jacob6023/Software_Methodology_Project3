package clinic.src.project.util;
import java.util.Calendar;

/**
 * This class represents the date of either the appointment or the patient profile's date of birth.
 *
 * @author Jack Crosby
 */
public class Date implements Comparable<Date>{
    /**
     * The date of the appointment or the patient's date of birth.
     */
    private int year;

    /**
     * The month of the appointment or the patient's date of birth.
     */
    private int month;

    /**
     * The day of the appointment or the patient's date of birth.
     */
    private int day;

    /**
     * Constants to assign for default constructor or use for invalid date.
     */
    public static final int INVALID_YEAR = -1;

    /**
     * Constants to assign for default constructor or use for invalid date.
     */
    public static final int INVALID_MONTH = -1;

    /**
     * Constants to assign for default constructor or use for invalid date.
     */
    public static final int INVALID_DAY = -1;

    /**
     * Constants to use for months.
     */
    public static final int JANUARY = 1;

    /**
     * Constants to use for months.
     */
    public static final int FEBRUARY = 2;

    /**
     * Constants to use for months.
     */
    public static final int MARCH = 3;

    /**
     * Constants to use for months.
     */
    public static final int APRIL = 4;

    /**
     * Constants to use for months.
     */
    public static final int MAY = 5;

    /**
     * Constants to use for months.
     */
    public static final int JUNE = 6;

    /**
     * Constants to use for months.
     */
    public static final int JULY = 7;

    /**
     * Constants to use for months.
     */
    public static final int AUGUST = 8;

    /**
     * Constants to use for months.
     */
    public static final int SEPTEMBER = 9;

    /**
     * Constants to use for months.
     */
    public static final int OCTOBER = 10;

    /**
     * Constants to use for months.
     */
    public static final int NOVEMBER = 11;

    /**
     * Constants to use for months.
     */
    public static final int DECEMBER = 12;

    /**
     * Constants to use for months.
     */
    public static final int MIN_YEAR = 1;

    /**
     * Constants for leap year rules.
     */
    public static final int QUADRENNIAL = 4;

    /**
     * Constants for leap year rules.
     */
    public static final int CENTENNIAL = 100;

    /**
     * Constants for leap year rules.
     */
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Index 0 is 0 so we can have the constant variables to equal the actual order of months instead of month - 1.
     */
    public static final int[] DAYS_IN_MONTH = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    /**
     * Getters
     * @return the year, month, or day.
     */
    public int getYear(){
        return year;
    }

    /**
     * Getters
     * @return the year, month, or day.
     */
    public int getMonth(){
        return month;
    }

    /**
     * Getters
     * @return the year, month, or day.
     */
    public int getDay(){
        return day;
    }

    /**
     * Setters
     * @param year the year to set.
     */
    public void setYear(int year){
        this.year = year;
    }

    /**
     * Setters
     * @param month the month to set.
     */
    public void setMonth(int month){
        this.month = month;
    }

    /**
     * Setters
     * @param day the day to set.
     */
    public void setDay(int day){
        this.day = day;
    }

    /**
     * Default Constructor to set the date to invalid.
     */
    public Date(){
        this.year = INVALID_YEAR;
        this.month =  INVALID_MONTH;
        this.day = INVALID_DAY;
    }

    /**
     * Parameterized Constructor to handle if the argument is in a String argument instead of three integers.
     *
     * @param dateStr the date being sent in String data type.
     */
    public Date(String dateStr) {
        String[] parts = dateStr.split("/");
        this.month = Integer.parseInt(parts[0].trim());
        this.day = Integer.parseInt(parts[1].trim());
        this.year = Integer.parseInt(parts[2].trim());
    }

    /**
     * Copy Constructor to copy the Date from an existing Date.
     *
     * @param copyDate the Date being passed.
     */
    public Date(Date copyDate){
        this.year = copyDate.year;
        this.month = copyDate.month;
        this.day = copyDate.day;
    }

    /**
     * Parameter Constructor to create a Date.
     *
     * @param year the year of the appointment or patient's date of birth.
     * @param month the month of the appointment or patient's date of birth.
     * @param day the day of the appointment or the patient's date of birth.
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Helper method for isValid() to determine if the year is a leap year.
     *
     * @return true if leap year.
     */
    private boolean isLeapYear() {
        if (year % QUATERCENTENNIAL == 0) {
            return true;
        } else if (year % CENTENNIAL == 0) {
            return false;
        } else if (year % QUADRENNIAL == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the calendar date.
     *
     * @return calendar to give the current date.
     */
    private Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month - 1); // Calendar month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        return calendar;
    }

    /**
     * Check if date is a future date.
     *
     * @return true if date is a future date, false otherwise.
     */
    private boolean isFutureDate() {
        Calendar today = Calendar.getInstance();
        return this.toCalendar().after(today);
    }

    /**
     * Determine if the date is a valid calendar date.
     *
     * @return True if calendar date, false is not a valid calendar date.
     */
    public boolean isValid() {
        if (year < MIN_YEAR) {
            return false;
        }
        if (month < JANUARY || month > DECEMBER) {
            return false;
        }
        int maxDaysInMonth = DAYS_IN_MONTH[month];
        if (month == FEBRUARY && isLeapYear()) {
            maxDaysInMonth = 29; // Adjust for leap year
        }
        if (day < 1 || day > maxDaysInMonth) {
            return false;
        }
        return true;
    }

    /**
     * Check if the date is today's actual calendar date.
     *
     * @return true if date is today, false otherwise.
     */
    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        Calendar date = toCalendar();
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Check if the date is the day before today's actual calendar date.
     *
     * @return true if day before today, false otherwise.
     */
    public boolean isDayBeforeToday() {
        Calendar today = Calendar.getInstance();
        Calendar date = toCalendar();
//        today.add(Calendar.DAY_OF_YEAR, -1);
        return date.before(today);
    }

    /**
     * Check if date is a weekend date.
     *
     * @return true if date is weekend, false otherwise.
     */
    public boolean isWeekend() {
        Calendar date = toCalendar();
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Check is date is NOT within six months from today.
     *
     * @return true if not within six months from today, false otherwise.
     */
    public boolean isNotWithinSixMonthsFromToday() {
        Calendar today = Calendar.getInstance();
        Calendar sixMonthsFromToday = Calendar.getInstance();
        sixMonthsFromToday.add(Calendar.MONTH, 6); // Add six months to today
        return toCalendar().after(sixMonthsFromToday); // Check if date is after six months from today
    }

    /**
     * Check if the date of birth is a real date.
     *
     * @return true if real date of birth, false otherwise.
     */
    public boolean realDOB(){
        return !isToday() && !isFutureDate();
    }

    /**
     * Determine if the date is the same.
     *
     * @param object the date being compared to.
     * @return true if instance date equals the parameter object, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Date date){
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }

    /**
     * Output date.
     *
     * @return date in String format mm/dd/yyyy.
     */
    @Override
    public String toString(){
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    /**
     * Compare date.
     *
     * @param date the date to be compared.
     * @return -1 if date is before, 0 if same, 1 if after.
     */
    @Override
    public int compareTo(Date date){
        if (this.year < date.year) {
            return -1;
        } else if (this.year > date.year) {
            return 1;
        }
        if (this.month < date.month) {
            return -1;
        } else if (this.month > date.month) {
            return 1;
        }
        if (this.day < date.day) {
            return -1;
        } else if (this.day > date.day) {
            return 1;
        }
        return 0;
    }

}