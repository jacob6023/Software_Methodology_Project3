package Project_1.src.project;

/**
 * @author Jack Crosby
 * TODO
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * TODO: compareTo
     * TODO: You MUST design the test cases to thoroughly test the compareTo() method, or you will lose 7 points.  The method compares last name, first name, and then dob for sorting.
     * Follow the instructions under the  “Test Design” section in the Coding Standard and include the test cases in the test specification.
     * TODO: You MUST include a testbed main() in this class or lose 8 points. You CAN use System.out in the  testbed main() to display the test results.
     */

    /**
     * String
     * @return
     */
    @Override
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }

    @Override
    public int compareTo(Profile profile){

    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile profile){
            return this.fname.equals(profile.fname) && this.lname.equals(profile.fname) && this.dob.equals(profile.dob);
        }
        return false;
    }

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
        this.fname = NULL;
        this.lname = NULL;
        this.dob = NULL;
    }

    /**
     * Copy Constructor
     */

    /**
     * Constructor
     * @param fname
     * @param lname
     * @param dob
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }


}