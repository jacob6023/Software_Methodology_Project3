package Project_1.src.project;

/**
 * Technician class extends the Provider class, which is a subclass of Person class.
 * This is a subclass of Provider which is a subclass of Person.
 * includes the instance var private int ratePerVisit to keep track of the technician's charging rate per visit.
 * Child class whose parent is Provider and GrandParent is Person
 *
 * User enters: T, appointment date, timeslot, patient info, and room type of imaging service
 *
 * The system shall maintain a circular list of technicians who are associated with their location.
 * The system shall use the list as the rotation to assign the technician to an appointment.
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
     * Copy Constructor
     */
    public Technician(Technician copyTechnician){
        super(copyTechnician);
        this.ratePerVisit = copyTechnician.ratePerVisit;
    }

    @Override
    public int compareTo(Technician technician){
        int providerComparison = super.compareTo(technician);
        if(providerComparison != 0){
            return providerComparison;
        }
        return Integer.compare(ratePerVisit, technician.getRatePerVisit());
    }

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

    public String toString(){
        return super.toString() + "[rate: $" + ratePerVisit + ".00]";
    }

    /**
     * returns the rate to the abstract method in provider.
     *
     * @return the provider's technician charging rate per visit for seeing patients.
     */
    public int rate(){
        return ratePerVisit;
    }

}
