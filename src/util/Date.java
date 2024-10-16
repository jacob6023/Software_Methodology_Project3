package Project_1.src.util;
import java.util.Calendar;

/**
 * This class represents the date of either the appointment or the patient profile's date of birth.
 *
 * @author Jack Crosby
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    // Constants to assign for default constructor or use for invalid date
    public static final int INVALID_YEAR = -1;
    public static final int INVALID_MONTH = -1;
    public static final int INVALID_DAY = -1;

    // Constants to use for months
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static final int MIN_YEAR = 1;

    // Constants for leap year rules
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // index 0 is 0 so we can have the constant variables to equal the actual order of months instead of month - 1
    public static final int[] DAYS_IN_MONTH = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    // Getters
    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}

    // Setters
    public void setYear(int year){this.year = year;}
    public void setMonth(int month){this.month = month;}
    public void setDay(int day){this.day = day;}

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
        today.add(Calendar.DAY_OF_YEAR, -1); // Subtract one day from today
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
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

    /**
     * Test bed to test this class.
     *
     * @param args the test cases inputted through command line.
     */
    public static void main(String[] args) {
        // Test cases for valid dates
        Date validDate1 = new Date(2024, FEBRUARY, 29); // Leap year
        Date validDate2 = new Date(2023, JULY, 15); // Regular valid date
        Date validDate3 = new Date(2000, DECEMBER, 31); // Leap year
        Date validDate4 = new Date(1999, NOVEMBER, 30); // Non-leap year, valid date

        // Test cases for invalid dates
        Date invalidDate1 = new Date(2023, FEBRUARY, 29); // Non-leap year
        Date invalidDate2 = new Date(2023, APRIL, 31); // April has only 30 days
        Date invalidDate3 = new Date(-1, JANUARY, 10); // Invalid year
        Date invalidDate4 = new Date(2024, 13, 1); // Invalid month
        Date invalidDate5 = new Date(2023, SEPTEMBER, 0); // Invalid day (0 day)

        // Display results of the valid date checks
        System.out.println(validDate1 + " is valid: " + validDate1.isValid());
        System.out.println(validDate2 + " is valid: " + validDate2.isValid());
        System.out.println(validDate3 + " is valid: " + validDate3.isValid());
        System.out.println(validDate4 + " is valid: " + validDate4.isValid());

        // Display results of the invalid date checks
        System.out.println(invalidDate1 + " is invalid: " + invalidDate1.isValid());
        System.out.println(invalidDate2 + " is invalid: " + invalidDate2.isValid());
        System.out.println(invalidDate3 + " is invalid: " + invalidDate3.isValid());
        System.out.println(invalidDate4 + " is invalid: " + invalidDate4.isValid());
        System.out.println(invalidDate5 + " is invalid: " + invalidDate5.isValid());
    }

}