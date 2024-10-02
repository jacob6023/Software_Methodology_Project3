package Project_1.src.project;
import java.util.Calendar;

/**
 * @author Jack Crosby
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    // use -1 to assign for default constructor
    public static final int INVALID_YEAR = -1;
    public static final int INVALID_MONTH = -1;
    public static final int INVALID_DAY = -1;

    // Constants to avoid magic numbers
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

    public static final int MIN_YEAR = 1; // Example for a minimum valid year

    // Constants for leap year rules
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // index 0 is 0 so we can have the constant variables to equal the actual order of months instead of month - 1
    public static final int[] DAYS_IN_MONTH = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    /**
     * Helper method for isValid()
     * @return true if leap year
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
     * Determine if the date is a calendar date
     * @return True if calendar date
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

    private Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month - 1); // Calendar month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        return calendar;
    }

    // Method to check if the date is today
    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        Calendar date = toCalendar();
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
    }

    // Method to check if the date is a day before today
    public boolean isDayBeforeToday() {
        Calendar today = Calendar.getInstance();
        Calendar date = toCalendar();
        today.add(Calendar.DAY_OF_YEAR, -1); // Subtract one day from today
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
    }

    // Method to check if the date is on a Saturday or Sunday
    public boolean isWeekend() {
        Calendar date = toCalendar();
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    // Method to check if the date is not within six months from today
    public boolean isNotWithinSixMonthsFromToday() {
        Calendar today = Calendar.getInstance();
        Calendar sixMonthsFromToday = Calendar.getInstance();
        sixMonthsFromToday.add(Calendar.MONTH, 6); // Add six months to today
        return toCalendar().after(sixMonthsFromToday); // Check if date is after six months from today
    }

    /**
     * Compare if same date
     * @param obj
     * @return true if same date, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Date date){
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }

    /**
     * make the date a String
     * @return date in String
     */
    @Override
    public String toString(){
        return String.format("%02d/%02d/%d", this.month, this.day, this.year);
    }

    /**
     *
     * @param date the object to be compared.
     * @return -1 if less. 0 if equal. 1 if greater
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
     * Getter methods
     */
    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}

    /**
     * Setter methods
     */
    public void setYear(int year){this.year = year;}
    public void setMonth(int month){this.month = month;}
    public void setDay(int day){this.day = day;}

    /**
     * Default Constructor
     */
    public Date(){
        this.year = INVALID_YEAR;
        this.month =  INVALID_MONTH;
        this.day = INVALID_DAY;
    }

    /**
     * Copy Constructor
     * deep copying: If the instance variables are objects, create new instances of those objects within the constructor and initialize them with the values from the argument object. This is called deep copying and ensures that changes to the copied object do not affect the original object.
     * @param copyDate: a copy of an object already initialized
     */
    public Date(Date copyDate){
        this.year = copyDate.year;
        this.month = copyDate.month;
        this.day = copyDate.day;
    }

    /**
     * Parameter Constructor
     * dealing with the boolean isValid()? CHECK
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

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