package Project_1.src.project;

import java.text.SimpleDateFormat;

/**
 * @author Jack Crosby
 */
public class Appointment implements Comparable<Appointments> {
    private Date date;
    private Timeslot timeslot;
    private Profile patient;
    private Provider provider;

    /**
     * @param parent class Object
     * @return true if two appointments have the same date, timeslot, and patient; return false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Appointment app){
            return this.date.equals(app.data) && this.timeslot.equals(app.timeslot) && this.patient.equals(app.patient);
        }
        return false;
    }

    /**
     * @return a textual representation of an  appointment
     * COME BACK TO THIS ONCE DO OTHER CLASSES
     * WRITE A HELPER METHOD TO COVERT THE ENUM TO A STRING TIME
     * MAKE SURE TO CONVERT THE MILITARY TIME TO NORMAL TIME
     */
    @Override
    public String toString() {
        return this.date.toString + " " + this.timeslot.timeString() + " " + this.patient.toString() + " " + "[" + this.provider.toString()
    }

    /**
     * @param app: the appointment we are comparing the object to
     * @return 0 if same time, -1 if earlier, +1 if later date
     *
     */
    @Override
    public int compareTo(Appointment app){
        if(this.date.equals())
    }

    /**
     * helper method to convert the Timeslot enums to integer value
     */
    private int convertTimeslot_int(Timeslot timeslot){
        return this.timeslot.hour * 100 + this.timeslot.minute;
    }

    /**
     * helper method to convert the Timeslot enums to a String
     */
    private String convertTimeslot_String(Timeslot timeslot){

    }

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
     * if the argument is of a type object, send it to the object's default constructor
     * otherwise set it to a constant
     */
    public Appointment() {
        this.date = NULL;
        this.timeslot = NULL;
        this.patient = NULL;
        this.provider = NULL;
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
        this.provider = provider;
    }


}


