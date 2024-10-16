package Project_1.src.project;

/**
 * This class represents the Technician at the provider.
 *
 * @author Jack Crosby
 */
public class Technician extends Provider{
    private int ratePerVisit;

    // Constant for the default constructor ratePerVisit value
    private static final int NULL_RATE = -1;

    // Getter
    public int getRatePerVisit(){return ratePerVisit;}

    // Setter
    public void setRatePerVisit(int ratePerVisit){this.ratePerVisit = ratePerVisit;}

    /**
     * Default Constructor
     */
    public Technician(){
        super();
        this.ratePerVisit = NULL_RATE;
    }

    /**
     * Copy Constructor
     */
    public Technician(Technician copyTechnician){
        super(copyTechnician);
        this.ratePerVisit = copyTechnician.ratePerVisit;
    }

    /**
     * Parameterized Constructor that creates the Technician.
     *
     * @param profile the profile of the provider's technician.
     * @param location the location of the provider's technician.
     * @param ratePerVisit the rate per visit of the provider's technician.
     */
    public Technician(Profile profile, Location location, int ratePerVisit){
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Compares the technician to another person object.
     *
     * @param person the object to be compared.
     * @return the comparison of the technician to the other person object.
     */
    @Override
    public int compareTo(Person person){
        if(!(person instanceof Technician)){
            return super.compareTo(person);
        }
        Technician technician = (Technician) person;
        int providerComparison = super.compareTo(technician);
        if(providerComparison != 0){
            return providerComparison;
        }
        return Integer.compare(this.ratePerVisit, technician.ratePerVisit);
    }

    /**
     * Compares the technician to another object to determine if they are equal.
     *
     * @param object the object to compare to the technician.
     * @return true if the object is a technician and has the same rate per visit, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!super.equals(object)){
            return false;
        }
        Technician technician = (Technician) object;
        return this.ratePerVisit == technician.getRatePerVisit();
    }

    /**
     * Output the technician's information.
     *
     * @return the string representation of the technician.
     */
    @Override
    public String toString(){
        return super.toString() + "[rate: $" + ratePerVisit + ".00]";
    }

    /**
     * Technician charging rate per visit for seeing patients.
     *
     * @return the provider's technician charging rate per visit for seeing patients.
     */
    @Override
    public int rate(){
        return ratePerVisit;
    }

}
