package Project_1.src.project;

/**
 * TODO: Extends the Provider class and includes two instance variables
 *
 * This is a subclass of the Provider class, and the Provider class is a subclass of the Person class.
 * TODO: determine if make the class abstract or put in method associated with abstract metho
 * @author Jack Crosby
 */

public class Doctor extends Provider{
    private Specialty specialty; //encapsulate the rate per visit based on specialty.
    private String npi;//National Provider Identification unique to the doctor.

    // Getters
    public Specialty getSpecialty(){return specialty;}
    public String getNPI(){return npi;}

    // Setters
    public void setSpecialty(Specialty specialty){this.specialty = specialty;}
    public void setNPI(String npi){this.npi = npi;}

    /**
     * Default Constructor
     */
    public Doctor(){
        super();
        this.specialty = null;
        this.npi = null;
    }

    /**
     * Parameterized Constructor to create the Doctor at the Provider.
     *
     * @param profile the profile info of the provider's doctor.
     * @param location the location of the provider's doctor.
     * @param specialty the specialty of the provider's doctor.
     * @param npi the National Provider Identification of the provider's doctor.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi){
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Copy Constructor to copy a doctor
     *
     * @param copyDoctor the doctor being copied
     */
    public Doctor(Doctor copyDoctor){
        super(copyDoctor);
        this.specialty = copyDoctor.specialty;
        this.npi = copyDoctor.npi;
    }

    @Override
    public int compareTo(Doctor doctor){
        int providerComparison = super.compareTo(doctor);
        if(providerComparison != 0){
            return providerComparison;
        }
        int specialityComparison = specialty.compareTo(doctor.getSpecialty());
        if(specialityComparison != 0){
            return specialityComparison;
        }
        return this.npi.compareTo(doctor.getNPI());
    }

    @Override
    public boolean equals(Object object){
        boolean isSpecialty;
        boolean isNPI;
        if(this == object){ // same object reference. both references point to the same object
            return true;
        }
        if(!super.equals(object)){  // compares provider profile and its location
            return false;
        }
        Doctor doctor = (Doctor) object;
        isSpecialty = this.specialty.equals(doctor.specialty);
        isNPI = this.npi.equals(doctor.npi);
        return isSpecialty && isNPI;
    }

    @Override
    public String toString(){
        return super.toString() + "[" + specialty.name() + ", #" + npi + "]";
    }

    /**
     * Encapsulate the rate per visit based on specialty.
     * TODO: confirm this does that^^^. NO, Outputs what specialty and the NPI
     *
     * @return the provider's doctor rate per visit.
     */
    public int rate(){
        return specialty.getCharge();
    }

}
