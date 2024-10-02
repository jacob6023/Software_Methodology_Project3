package Project_1.src.project;

/**
 * @author Jack Crosby
 */
public class Appointment implements Comparable<Appointment> {
    private Date date;
    private Timeslot timeslot;
    private Profile patient;
    private Provider provider;

    /**
     * @param obj
     * @return true if two appointments have the same date, timeslot, and patient; return false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Appointment app){
            return this.date.equals(app.date) && this.timeslot.equals(app.timeslot) && this.patient.equals(app.patient);
        }
        return false;
    }

    /**
     * @return a textual representation of an  appointment
     */
    @Override
    public String toString() {
        return this.date.toString() + " " + this.timeslot.toString() + " " + this.patient.toString() + " " + this.provider.toString();
    }

    /**
     * Uses Object Date compareTo and Timeslot's compareTo
     * @param app: the appointment we are comparing the object to
     * @return 0 if same time, -1 if earlier, +1 if later date
     */
    @Override
    public int compareTo(Appointment app){
        int dateComparison = this.date.compareTo(app.date);
        int timeComparison = this.timeslot.compareTime(app.timeslot);

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
    public void setTimeslot(Timeslot timeslot) {this.timeslot = timeslot;}
    public void setProfile(Profile patient) {this.patient = patient;}
    public void setProvider(Provider provider) {this.provider = provider;}

    /**
     * Default constructor
     * if the argument is of a type object, send it to the object's default constructor
     * otherwise set it to a constant
     */
    public Appointment() {
        this.date = null;
        this.timeslot = null;
        this.patient = null;
        this.provider = null;
    }

    /**
     * Copy Constructor
     * @param copyAppointment: appointment of an object already initialized
     */
    public Appointment(Appointment copyAppointment) {
        this.date = copyAppointment.date;
        this.timeslot = copyAppointment.timeslot;
        this.patient = copyAppointment.patient;
        this.provider = copyAppointment.provider;
    }

    /**
     * Parameterized Constructor
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


