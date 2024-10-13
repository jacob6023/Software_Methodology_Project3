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
     * String
     * @return
     */
    @Override
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * compares last name as first priority, then first name, then dob (inherited by the Date class's compareTo()
     *  if profile comes before, > 0. if after, < 0, 0 if the same profile
     *  TODO: add null check
     * @param profile the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Profile profile){
        int lastNameComparison = this.lname.compareTo(profile.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        int firstNameComparison = this.fname.compareTo(profile.fname);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        return this.dob.compareTo(profile.dob);
    }

    /**
     *
     * @param obj
     * @return
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
        this.fname = null;
        this.lname = null;
        this.dob = null;
    }

    /**
     * Copy Constructor
     */
    public Profile(Profile copyProfile){
        this.fname = copyProfile.fname;
        this.lname = copyProfile.lname;
        this.dob = copyProfile.dob;
    }

    /**
     * Parameter Constructor
     * @param fname
     * @param lname
     * @param dob
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public static void main(String[] args) {
        // Create Date objects
        Date dob1 = new Date(1990, 5, 15);
        Date dob2 = new Date(1992, 3, 25);
        Date dob3 = new Date(1990, 5, 15); // Same dob as dob1

        // Create Profile objects
        Profile profile1 = new Profile("Jack", "Crosby", dob1);
        Profile profile2 = new Profile("John", "Doe", dob2);
        Profile profile3 = new Profile("Jane", "Crosby", dob3);
        Profile profile4 = new Profile("Jack", "Crosby", dob1); // Same as profile1

        // Crosby vs. Doe
        System.out.println("Test 1 (lname comparison): " + profile1.compareTo(profile2)); // Expected < 0

        // Jack vs. Jane (first names since last names same)
        System.out.println("Test 2 (fname comparison): " + profile1.compareTo(profile3)); // Expected < 0

        // same names, different DOB
        System.out.println("Test 3 (DOB comparison): " + profile1.compareTo(profile2)); // Expected < 0

        // identical profiles
        System.out.println("Test 4 (identical profiles): " + profile1.compareTo(profile4)); // Expected 0

        // Doe vs. Crosby
        System.out.println("Test 5 (lname comparison): " + profile2.compareTo(profile1)); // Expected > 0
    }



}
