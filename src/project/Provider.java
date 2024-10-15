package Project_1.src.project;

/**
 *
 * This is a subclass of the Person class
 * Extends the person class and includes instance variable Location location to keep track of the practice location
 * TODO: since Person's instance variable is protected, we can just use .profile instead of .getProfile() since this is a subclass.
 *
 * @author Jack Crosby
 */
public abstract class Provider extends Person{
    private Location location;

    // Getter
    public Location getLocation(){return location;}

    // Setter
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Default Constructor
     * Make protected to prevent instantiation of Provider class. Since this is only used by the subclasses.
     * This ensures that the Provider class can't be instantiated directly and can only be extended by subclasses.
     */
    protected Provider() {
        super();
        this.location = null;
    }

    /**
     * Parameterized Constructor
     * @param profile the profile of the provider.
     * @param location the location of the provider.
     */
    protected Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    /**
     * Copy Constructor
     * @param copyProvider the provider being copied.
     */
    protected Provider(Provider copyProvider) {
        super(copyProvider);
        this.location = copyProvider.location;
    }


    /**
     * compare just the location, since we have to display list of appointments ordered by location
     * TODO: This implementation may suppose to be in imaging class and not here, may just call super for this one
     *
     * @param provider
     * @return
     */
    public int compareLocation(Provider provider) {
        return this.location.compareTo(provider.getLocation());
    }

    @Override
    public int compareTo(Provider provider) {
        int profileComparison = super.compareTo(provider);
        if (profileComparison != 0) {
            return profileComparison;
        }
        return this.location.compareTo(provider.location);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){ // both references point to the same object
            return true;
        }
        if(!super.equals(object)){ // compares profile
            return false;
        }
        Provider provider = (Provider) object;
        return this.location.equals(provider.getLocation());
    }

    /**
     * Output
     * @return "[fname lname dob, location, county zip]
     */
    @Override
    public String toString() {
        return "[" + super.toString() + ", " + location.toString() + "] ";
    }

    /**
     * TODO: credit amount from bottom of the output.txt
     */

    /**
     * Abstract method that returns the provider's charging rate per visit depending on if appointment is for doctor or technician for seeing patients.
     * Gets either the doctor's charging rate or the technician's charging rate
     *
     *
     * @return the provider's charging rate per visit for seeing patients.
     */
    public abstract int rate();

}


