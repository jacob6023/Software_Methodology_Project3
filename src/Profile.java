package Project_1.src;

public class Profile impements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * TODO: equals, toString, compareTo
     * TODO: You MUST design the test cases to thoroughly test the compareTo() method, or you will lose 7 points.  The method compares last name, first name, and then dob for sorting. Follow the instructions under the  “Test Design” section in the Coding Standard and include the test cases in the test specification.
     * TODO: You MUST include a testbed main() in this class or lose 8 points. You CAN use System.out in the  testbed main() to display the test results.
     */

    /**
     * getter methods
     */
    public String get_fname(){return fname;}
    public String get_lname(){return lname;}
    public Date get_dob(){return dob;}

    /**
     * setter methods
     */
    public void set_fname(String fname){this.fname = fname;}
    public void set_lname(String lname){this.lname = lname;}
    public void set_dob(Date dob){this.dob = dob;}

    /**
     * Default Constructor
     */
    public Profile(){

    }

    /**
     * Copy Constructor
     */
    public Profile(Profile profile){
        this.profile = profile;
    }

    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }


}
