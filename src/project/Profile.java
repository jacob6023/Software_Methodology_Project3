package Project_1.src.project;
import Project_1.src.util.Date;

/**
 * This class creates the patient's profile info.
 *
 * @author Jack Crosby
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    // Getters
    public String get_fname(){
        return fname;
    }

    public String get_lname(){
        return lname;
    }

    public Date get_dob(){
        return dob;
    }

    // Setters
    public void set_fname(String fname){
        this.fname = fname;
    }

    public void set_lname(String lname){
        this.lname = lname;
    }

    public void set_dob(Date dob){
        this.dob = dob;
    }

    /**
     * Default Constructor to create profile and set the patient's profile info to null.
     */
    public Profile(){
        this.fname = null;
        this.lname = null;
        this.dob = null;
    }

    /**
     * Copy Constructor to create profile and copy an existing profile data into it.
     *
     * @param copyProfile the profile being copied.
     */
    public Profile(Profile copyProfile){
        this.fname = copyProfile.fname;
        this.lname = copyProfile.lname;
        this.dob = copyProfile.dob;
    }

    /**
     * Parameter Constructor to create new profile.
     *
     * @param fname patient's first name.
     * @param lname patient's last name.
     * @param dob patient's date of birth.
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Output patient info.
     *
     * @return String in format of "first name last name date of birth (mm/dd/yyyy)".
     */
    @Override
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * In lexicographical order compares last name, then first name, then dob (through Date's compareTo).
     *
     * @param profile the profile being compared.
     * @return < 0 if profile comes before. > 0 if profile comes after. 0 if profile has same details.
     */
    @Override
    public int compareTo(Profile profile){
        int lastNameComparison = this.lname.compareTo(profile.lname);
        if (lastNameComparison != 0) {
            return Integer.compare(lastNameComparison, 0);
        }
        int firstNameComparison = this.fname.compareTo(profile.fname);
        if (firstNameComparison != 0) {
            return Integer.compare(firstNameComparison, 0);
        }
        return Integer.compare(this.dob.compareTo(profile.dob), 0);
    }

    /**
     * Check if two profiles are the same.
     *
     * @param obj the object being compared to.
     * @return true if profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile profile){
            return this.fname.equalsIgnoreCase(profile.fname) &&
                    this.lname.equalsIgnoreCase(profile.lname) &&
                    this.dob.equals(profile.dob);
        }
        return false;
    }

}
