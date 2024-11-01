package com.example.project3.clinic;

/**
 * This class represents a doctor that is a provider. It extends the Provider class and has a specialty and NPI.
 *
 * @author Jack Crosby
 */
public class Doctor extends Provider{

    /**
     * The specialty of the doctor.
     */
    private Specialty specialty;

    /**
     * The National Provider Identification of the doctor.
     */
    private String npi; //National Provider Identification unique to the doctor.

    /**
     * Getters
     *
     * @return the specialty of the doctor.
     */
    public Specialty getSpecialty(){
        return specialty;
    }

    /**
     * Get the National Provider Identification of the doctor.
     *
     * @return the National Provider Identification of the doctor.
     */
    public String getNPI(){
        return npi;
    }

    /**
     * Setters
     *
     * @param specialty the specialty to set.
     */
    public void setSpecialty(Specialty specialty){
        this.specialty = specialty;
    }

    /**
     * Set the National Provider Identification of the doctor.
     * @param npi the National Provider Identification to set.
     */
    public void setNPI(String npi){
        this.npi = npi;
    }

    /**
     * Default Constructor.
     */
    public Doctor(){
        super();
        this.specialty = null;
        this.npi = null;
    }

    /**
     * Copy Constructor to copy a doctor.
     *
     * @param copyDoctor the doctor being copied/
     */
    public Doctor(Doctor copyDoctor){
        super(copyDoctor);
        this.specialty = copyDoctor.specialty;
        this.npi = copyDoctor.npi;
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
     * Concrete method that compares the doctor to another doctor.
     *
     * @param person the object to be compared.
     * @return -1 if the doctor is less than the other doctor, 0 if they are equal, 1 if the doctor is greater than the other doctor.
     */
    @Override
    public int compareTo(Person person){
        if (!(person instanceof Doctor)) { // general class, if person is not a provider
            return super.compareTo(person);
        }
        Doctor doctor = (Doctor) person;
        int providerComparison = super.compareTo(doctor);
        if (providerComparison != 0) {
            return providerComparison;
        }
        int specialtyComparison = this.specialty.compareTo(doctor.specialty);
        if (specialtyComparison != 0) {
            return specialtyComparison;
        }
        return this.npi.compareTo(doctor.npi);
    }

    /**
     * Concrete method that determines if the doctor is equal to another doctor.
     * @param object the object to be compared.
     * @return true if the doctor is equal to the other doctor, false otherwise.
     */
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

    /**
     * Display doctor.
     *
     * @return String of the doctor's profile, location, specialty, and NPI.
     */
    @Override
    public String toString(){
        return super.toString() + "[" + specialty.name() + ", #" + npi + "]";
    }

    /**
     * Concrete method that returns the provider's doctor rate per visit.
     *
     * @return the provider's doctor charging rate per visit for seeing patients.
     */
    @Override
    public int rate(){
        return specialty.getCharge();
    }

}
