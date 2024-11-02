package com.example.cs213_project3.clinic;
import com.example.cs213_project3.clinic.src.project.util.Date;

/**
 * This class creates the patient's profile info.
 *
 * @author Jack Crosby
 */
public class Profile implements Comparable<Profile>{
    /**
     * The patient's first name.
     */
    private String fname;

    /**
     * The patient's last name.
     */
    private String lname;

    /**
     * The patient's date of birth.
     */
    private Date dob;

    /**
     * Getters
     *
     * @return the patient's first name.
     */
    public String get_fname(){
        return fname;
    }

    /**
     * Get the patient's last name.
     *
     * @return the patient's last name.
     */
    public String get_lname(){
        return lname;
    }

    /**
     * Get the patient's date of birth.
     *
     * @return the patient's date of birth.
     */
    public Date get_dob(){
        return dob;
    }

    /**
     * Setters
     *
     * @param fname the first name to set.
     */
    public void set_fname(String fname){
        this.fname = fname;
    }

    /**
     * Set the last name of the patient.
     *
     * @param lname the last name to set.
     */
    public void set_lname(String lname){
        this.lname = lname;
    }

    /**
     * Set the date of birth of the patient.
     *
     * @param dob the date of birth to set.
     */
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
     * @return less 0 if profile comes before. greater 0 if profile comes after. 0 if profile has same details.
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
