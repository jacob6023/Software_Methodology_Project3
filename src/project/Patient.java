package Project_1.src.project;


public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit next; //a LL of visits (completed appt.)

    //traverse linked list "Visits" and returns the charge of the patient
    public int charge(){
        int total = 0;
        Visit currentVisit = next;
        while (currentVisit != null){
            if(currentVisit.getAppointment() != null && currentVisit.getAppointment().getProvider() != null && currentVisit.getAppointment().getProvider().getSpecialty() != null){
                total += currentVisit.getAppointment().getProvider().getSpecialty().getCharge();
            }
            currentVisit = currentVisit.getVisit();
        }
        return total;
    }

    //override for equals, toString, and compareTo
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient patient){
            return this.profile.equals(patient.profile);
        }
        return false;
    }

    /**
     * Just printing value of charge since profile class already has a toString() method.
     * Calling this in medical record for the PS command
     * @return
     */
    @Override
    public String toString() {
        return String.format("%.2f", (double) charge());
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

    // Getters
    public Profile getProfile(){return profile;}
    public Visit getVisit(){return next;}

    // Setters
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

}

