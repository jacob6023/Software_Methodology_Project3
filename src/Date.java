package Project_1.src;
import java.util.Calendar;

public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;
    public boolean isValid(); // check if the date is a valid calender data

    /**
     * TODO: instance methods and define constants. other todo's mentioned along the class
     * TODO: isValid() to determine if the date is a calender date. have to include the leap year case described in the doc
     * TODO: equals, toString, compareTo
     */

    /**
     * method description here
     *  If the instance variables are objects, create new instances of those objects within the constructor and initialize them with the values from the argument object. This is called deep copying and ensures that changes to the copied object do not affect the original object.
     * TODO: implement code
     */
    public boolean isValid(){

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
     * TODO: dealing with public boolean isValid();
     * TODO: need a testbed Main() for isValid()
     * TODO: need to make a class for isValid()?
     */

    /**
     * Default Constructor
     * TODO: make constant value (non-magic numbers) to initialize to default values
     */
    public Date(){

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
     * Argument Constructor
     * dealing with the boolean isValid()? CHECK
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }


    @Override
    public int compareTo(Date o) {
        return 0;
    }
}