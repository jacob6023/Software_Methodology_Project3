package Project_1.src.project;

/**
 * This class uses a linked list of the amount of visits a specific patient has and computes their charge.
 *
 * @author Jack Crosby
 */
public class Patient extends Person{
    private Visit visit; //a LL of visits (completed appointment). keep track of a LL of visits

    // Getters. Note that since Person uses protected profile, we can just use .profile instead of .getProfile() since this is a subclass.
    public Profile getProfile(){
        return profile;
    }

    public Visit getVisit(){
        return visit;
    }

    // Setters
    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public void setVisit(Visit next){
        this.visit = next;
    }

    /**
     * Default Constructor to set the Patient data to null.
     */
    public Patient(){
        super();
        this.visit = null;
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
     * Parameterized Constructor to create Patient.
     * @param profile the patient's profile.
     * @param next the node of the linked list.
     */
    public Patient(Profile profile, Visit next){
        super(profile);
        this.visit = next;
    }

    /**
     * Computes the charge by traversing the linked list Visits.
     *
     * @return charge of the patient.
     */
    public int charge(){
        int total = 0;
        Visit currentVisit = visit;
        while (currentVisit != null){
            if ( (currentVisit.getAppointment() != null) &&
                    (currentVisit.getAppointment().getProvider() instanceof Provider) ) {
                Provider provider = (Provider) currentVisit.getAppointment().getProvider();
                total += provider.rate();
            }
            currentVisit = currentVisit.getNextVisit();
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
     *
     * TODO: amount patient owes at end of the output.txt.
     *
     * @return String of the amount the patient owes.
     */
    public String printCharge() {
        return this.toString() + " [due: $" + String.format("%.2f", (double) charge()) + "]";
    }

    /**
     * Output
     * TODO: may need to print out the appointment info, since this is a LL of visits this patient has had.
     * @return "[fname lname dob, location, county zip]
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Compares the patient to another patient.
     *
     * @param person the person to be compared
     * @return < 0 if before, 0 if same, > 0 if after.
     */
    @Override
    public int compareTo(Person person) {
        if(!(person instanceof Patient)){
            throw new IllegalArgumentException("Invalid comparison");
        }
        Patient patient = (Patient) person;
        int profileComparison = super.compareTo(patient);
        if(profileComparison != 0){
            return profileComparison;
        }
        return this.visit.getAppointment().getPatient().compareTo(patient.getVisit().getAppointment().getPatient());
    }

}

