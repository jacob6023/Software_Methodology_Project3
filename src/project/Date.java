package Project_1.src.project;

/**
 * @author Jack Crosby
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    /**
     * Use -1 instead of 0 for constants since the month and day start at 0
     */
    public static final int INVALID_YEAR = -1;
    public static final int INVALID_MONTH = -1;
    public static final int INVALID_DAY = -1;

    /**
     * TODO: dealing with public boolean isValid();
     * TODO: need a testbed Main() for isValid()
     * TODO: need to make a class for isValid()?
     */

    /**
     * check if the date is a valid calender data
     * TODO
     * @return
     */
    public boolean isValid(){

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

}