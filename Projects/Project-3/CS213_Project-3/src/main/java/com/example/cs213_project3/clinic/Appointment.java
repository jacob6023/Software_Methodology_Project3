package com.example.cs213_project3.clinic;
import com.example.cs213_project3.clinic.src.project.util.Date;

/**
 * This class represents the Appointment being scheduled and its details.
 * Composition class of date, timeslot, profile, and provider.
 *
 * @author Jack Crosby
 */
public class Appointment implements Comparable<Appointment> {

    /**
     * The date of the appointment.
     */
    protected Date date;

    /**
     * The timeslot of the appointment.
     */
    protected Timeslot timeslot;

    /**
     * The patient scheduling the appointment.
     */
    protected Person patient;

    /**
     * The provider the appointment is for.
     */
    protected Person provider;

    /**
     * Getters
     *
     * @return the date of the appointment.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the timeslot of the appointment.
     *
     * @return the timeslot of the appointment.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Get the patient scheduling the appointment.
     *
     * @return the patient scheduling the appointment.
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Get the provider the appointment is for.
     *
     * @return the provider the appointment is for.
     */
    public Person getProvider() {
        return provider;
    }


    /**
     * Setters
     *
     * @param date the date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Set the timeslot of the appointment.
     *
     * @param timeslot the timeslot to set.
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Set the patient scheduling the appointment.
     *
     * @param patient the patient to set.
     */
    public void setPatient(Person patient) {
        this.patient = patient;
    }

    /**
     * Set the provider the appointment is for.
     *
     * @param provider the provider to set.
     */
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


