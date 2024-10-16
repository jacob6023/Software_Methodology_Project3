package Project_1.src.project;
import Project_1.src.util.Date;

/**
 * This class represents the Appointment being scheduled and its details.
 * Composition class of date, timeslot, profile, and provider.
 * Parent of Imaging
 *
 * @author Jack Crosby
 */
public class Appointment implements Comparable<Appointment> {
    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;
    protected Person provider;

    // Getters
    public Date getDate() {
        return date;
    }
    public Timeslot getTimeslot() {
        return timeslot;
    }
    public Person getPatient() {
        return patient;
    }
    public Person getProvider() {
        return provider;
    }

    // Setters
    public void setDate(Date date) {
        this.date = date;
    }
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
    public void setPatient(Person patient) {
        this.patient = patient;
    }
    public void setProvider(Person provider) {
        this.provider = provider;
    }

    /**
     * Default constructor to initialize the data to null.
     */
    public Appointment() {
        this.date = null;
        this.timeslot = null;
        this.patient = null;
        this.provider = null;
    }

    /**
     * Copy Constructor to copy the data from an existing appointment object.
     *
     * @param copyAppointment: appointment of an object already initialized.
     */
    public Appointment(Appointment copyAppointment) {
        this.date = copyAppointment.date;
        this.timeslot = copyAppointment.timeslot;
        this.patient = copyAppointment.patient;
        this.provider = copyAppointment.provider;
    }

    /**
     * Parameterized Constructor to create a new appointment.
     *
     * @param date the appointment date.
     * @param timeslot the appointment timeslot.
     * @param patient the patient scheduling the appointment.
     * @param provider the provider the appointment is for.
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Equals method to determine if instance of object appointment is equal to the argument.
     *
     * @param obj the object this Appointment is being compared to.
     * @return true if two appointments have the same date, timeslot, and patient; return false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Appointment app){
            return this.date.equals(app.date) && this.timeslot.equals(app.timeslot) && this.patient.equals(app.patient);
        }
        return false;
    }

    /**
     * toString() method to make the appointment data into format: appointment date, appointment timeslot, patient info, provider info.
     *
     * @return a String representation of appointment.
     */
    @Override
    public String toString() {
        return this.date.toString() + " " + this.timeslot.toString() + " " + this.patient.toString() + " " + this.provider.toString();
    }

    /**
     * CompareTo method that compares an appointment with argument Appointment app.
     * Uses Object Date's compareTo and Timeslot's compareTo.
     *
     * @param appointment: the appointment we are comparing the object to.
     * @return 0 if same time, -1 if earlier, +1 if later date.
     */
    @Override
    public int compareTo(Appointment appointment){
        int dateComparison = this.date.compareTo(appointment.date);
        int timeComparison = this.timeslot.compareTo(appointment.timeslot);
        if(dateComparison > 0){
            return 1;
        }else if(dateComparison < 0){
            return -1;
        }else{
            if(timeComparison > 0){
                return 1;
            }else if(timeComparison < 0){
                return -1;
            }
        }
        return 0;
    }

}


