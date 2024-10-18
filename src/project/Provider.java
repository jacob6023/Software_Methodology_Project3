package Project_1.src.project;

/**
 * Provider class is an abstract class that represents a provider in the system.
 * It's a superclass to represent either a doctor or a technician, while being a subclass of person.
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
     * Default Constructor to create a provider with null values.
     */
    protected Provider() {
        super();
        this.location = null;
    }

    /**
     * Copy Constructor to create a provider with an existing provider's data.
     * @param copyProvider the provider being copied.
     */
    protected Provider(Provider copyProvider) {
        super(copyProvider);
        this.location = copyProvider.location;
    }

    /**
     * Parameterized Constructor to create a provider.
     *
     * @param profile the profile of the provider.
     * @param location the location of the provider.
     */
    protected Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    /**
     * Compare the provider's profile and location.
     *
     * @param person the object to be compared.
     * @return 0 if the provider is equal to the object, a negative integer if the provider is less than the object, a positive integer if the provider is greater than the object.
     */
    @Override
    public int compareTo(Person person) {
        if(!(person instanceof Provider)){
            throw new IllegalArgumentException("Invalid comparison");
        }
        Provider provider = (Provider) person;
        int profileComparison = super.compareTo(provider);
        if(profileComparison != 0){
            return profileComparison;
        }
        return this.location.compareTo(provider.getLocation());
    }

    /**
     * Check if the provider is equal to another object.
     * @param object the object to be compared.
     * @return true if the provider is equal to the object, false otherwise.
     */
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
     * Display the provider's information.
     *
     * @return String in format "[fname lname dob, location, county zip].
     */
    @Override
    public String toString() {
        return "[" + super.toString() + ", " + location.toString() + "] ";
    }

    /**
     * Abstract method that returns the provider's charging rate per visit.
     * Gets either the doctor's charging rate or the technician's charging rate.
     *
     * @return the provider's charging rate per visit for seeing patients.
     */
    public abstract int rate();

}


