package Project_1.src.project;

/**
 * This class uses a linked list of the amount of visits a specific patient has and computes their charge.
 * TODO: extends the Person class and includes the instance var private Visit visit; to keep track of a LL of visits.
 * This is a subclass of the Person class
 *
 * @author Jack Crosby
 */
public class Patient extends Person{
    private Visit visit; //a LL of visits (completed appointment). keep track of a LL of visits

    // Getters. Note that since Person uses protected profile, we can just use .profile instead of .getProfile() since this is a subclass.
    public Profile getProfile(){return profile;}
    public Visit getVisit(){return visit;}

    // Setters
    public void setProfile(Profile profile){this.profile = profile;}
    public void setVisits(Visit next){this.visit = next;}

    /**
     * Default Constructor to set the Patient data to null.
     */
    public Patient(){
        super();
        this.visit = null;
    }

    /**
     * Parameterized Constructor to create Patient.
     * @param profile the patient's profile.
     * @param next the node of the linked list.
     */
    public Patient(Profile profile, Visit next){
        super(profile);
        this.visit = next;
    }

    /**
     * Copy Constructor to copy an existing patient.
     * @param copyPatient the patient whose data being copied.
     */
    public Patient(Patient copyPatient){
        super(copyPatient);
        this.visit = copyPatient.visit;
    }

    /**
     * Computes the charge by traversing the linked list Visits.
     * TODO: may need to move this somewhere else. the function may not be here anymore
     *
     * @return charge of the patient.
     */
    public int charge(){
        int total = 0;
        Visit currentVisit = visit;
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
     * @param object the object patient being compared to.
     * @return true if patients are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    /**
     * Print the value of charge the patient owes.
     * Calling this in medical record for the PS command.
     * TODO: figure out dynamic binding for this. the charge method may not be here anymore
     *
     * @return String of the amount the patient owes.
     */
    //@Override
    //public String toString() {
    //    return String.format("%.2f", (double) charge());
    //}

    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Dynamic Binding to Person's compareTo
     *
     * @param patient the object to be compared by first name, last name, (lexicographical order) then date of birth.
     * @return < 0 if before, 0 if same, > 0 if after.
     */
    @Override
    public int compareTo(Patient patient) {
        int profileComparison = super.compareTo(patient);
        if (profileComparison != 0) {
            return profileComparison;
        }
        return this.visit.getAppointment().compareTo(patient.getVisit().getAppointment());
    }

}

