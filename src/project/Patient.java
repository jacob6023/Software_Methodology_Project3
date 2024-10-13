package Project_1.src.project;

/**
 * This class uses a linked list of the amount of visits a specific patient has and computes their charge.
 *
 * @author Jack Crosby
 * @author Vikram kadyan
 */
public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit next; //a LL of visits (completed appointment)

    // Getters
    public Profile getProfile(){return profile;}
    public Visit getVisit(){return next;}

    // Setters
    public void setProfile(Profile profile){this.profile = profile;}
    public void setVisits(Visit next){this.next = next;}

    /**
     * Parameterized Constructor to create Patient.
     * @param profile the patient's profile.
     * @param next the node of the linked list.
     */
    public Patient(Profile profile, Visit next){
        this.profile = profile;
        this.next = next;
    }

    /**
     * Default Constructor to set the Patient data to null.
     */
    public Patient(){
        this.profile = null;
        this.next = null;
    }

    /**
     * Copy Constructor to copy an existing patient.
     * @param copyPatient the patient whose data being copied.
     */
    public Patient(Patient copyPatient){
        this.profile = copyPatient.profile;
        this.next = copyPatient.next;
    }

    /**
     * Computes the charge by traversing the linked list Visits.
     *
     * @return charge of the patient.
     */
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

    /**
     * Determine if the patient is the same to argument patient.
     *
     * @param obj the object patient being compared to.
     * @return true if patients are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient patient){
            return this.profile.equals(patient.profile);
        }
        return false;
    }

    /**
     * Print the value of charge the patient owes.
     * Calling this in medical record for the PS command.
     *
     * @return String of the amount the patient owes.
     */
    @Override
    public String toString() {
        return String.format("%.2f", (double) charge());
    }

    /**
     * Compare two patients.
     * Uses profile's compareTo.
     * TODO: may need possible update in future since it just uses the profile's compareTo.
     *
     * @param patient the object to be compared by first name, last name, (lexicographical order) then date of birth.
     * @return < 0 if before, 0 if same, > 0 if after.
     */
    @Override
    public int compareTo(Patient patient) {
        return this.profile.compareTo(patient.profile);
    }


}

