package Project_1.src.project;

public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit next; //a LL of visits (completed appt. )

    //traverse linked list to find the total charge
    public int charge(){
        int total = 0;
        Visit currentVisit = this.next;
        while (currentVisit != null){
            total += currentVisit.getAppointment().getProvider().getSpecialty().getCharge();
            currentVisit = currentVisit.getVisit();
        }
        return total;
    }

    /**
     * getters
     */
    public Profile getProfile(){return profile;}
    public Visit getVisit(){return next;}

    /**
     * Setters
     */
    public void setProfile(Profile profile){this.profile = profile;}
    public void setVisits(Visit next){this.next = next;}

    /**
     * parameter constructor constructor
     * @param
     */
    public Patient(Profile profile, Visit next){
        this.profile = profile;
        this.next = next;
    }

    /**
     * Default Constructor
     */
    public Patient(){
        this.profile = null;
        this.next = null;
    }

    /**
     * Copy Constructor
     * @param
     * @return
     */
    public Patient(Patient copyPatient){
        this.profile = copyPatient.profile;
        this.next = copyPatient.next;
    }

    //override for equals, toString, and compareTo
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient patient){
            return this.profile.equals(patient.profile);
        }
        return false;
    }
    @Override
    public String toString() {
        return profile.toString();
    }

    /**
     * Uses profile's compareTo
     * @param patient the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Patient patient) {
        return this.profile.compareTo(patient.profile);
    }



}

