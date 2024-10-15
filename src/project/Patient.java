package Project_1.src.project;

/**
 * This class uses a linked list of the amount of visits a specific patient has and computes their charge.
 * TODO: extends the Person class and includes the instance var private Visit visit; to keep track of a LL of visits.
 * This is a subclass of the Person class
 * TODO: since Person's instance variable is proteced, we can just use .profile instead of .getProfile() since this is a subclass.
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
     * instanceof: need to check if the Person instance you are working with is actually a Provider before you cast it. This way, you avoid ClassCastException errors.
     * Once you confirm that the object is indeed a Provider, you can cast it and call the rate() method.
     * We can do this with abstract classes, since we aren't creating an instance of the abstract class, but we are creating an instance of the subclass.
     *polymorphism allows you to use a Provider reference (provider) to refer to any object of a subclass of Provider, such as Doctor or Technician
     *
     * @return charge of the patient.
     */
    public int charge(){
        int total = 0;
        Visit currentVisit = visit;
        while (currentVisit != null){
            if ( (currentVisit.getAppointment() != null) && (currentVisit.getAppointment().getProvider() instanceof Provider) ) {
                Provider provider = (Provider) currentVisit.getAppointment().getProvider(); // polymorphism.
                total += provider.rate();
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

