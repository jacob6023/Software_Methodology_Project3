package Project_1.src;
import java.util.Calendar;

public class Appointment implements Comparable<Appointments> {
    private Date date;
    private Timeslot timeslot;
    private Profile patient;
    private Provider provider;

    /**
     * TODO: instance methods
     * TODO: equals, toString, compareTo
     * TODO: define constants
     */

    /**
     * getter methods
     */
    public Date getDate() {return date;}
    public Timeslot getTimeslot() {return timeslot;}
    public Profile getProfile() {return patient;}
    public Provider getProvider() {return provider;}

    /**
     * setter methods
     */
    public void setDate(Date date) {this.date = date;}
    public void setTimeSlot(Timeslot timeslot) {this.timeslot = timeslot;}
    public void setProfile(Profile patient) {this.patient = patient;}
    public void setProvider(Provider provider) {this.provider = provider;}

    /**
     * Default constructor
     * TODO: set constants (non-magic number) to initialize the default values to
     */
    public Appointment() {

    }

    /**
     * Copy Constructor
     * @param copyAppointmnet: appointment of an object already initialized
     */
    public Appointment(Appointment copyAppointmnet) {
        this.date = copyAppointment.date;
        this.timeslot = copyAppointment.timeslot;
        this.patient = copyAppointment.patient;
        this.provider = copyAppointment.provider;
    }

    /**
     * Constructor with all parameters
     * @param date
     * @param timeslot
     * @param patient
     * @param provider
     */
    public Appointment(Date date, Timeslot timeslot, Profile patient, Provider provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.Provider = provider;
    }


    @Override
    public int compareTo(Appointments o) {
        return 0;
    }
}


