package com.example.project3.clinic;

/**
 * This class defines a node in a singly linked list that maintains the list of visits.
 *
 * @author Jack Crosby
 */
public class Visit {
    /**
     * The appointment where the visit took place.
     */
    private Appointment appointment;

    /**
     * The next visit in the linked list.
     */
    private Visit next;

    /**
     * Getter
     *
     * @return the appointment or next visit.
     */
    public Appointment getAppointment(){
        return appointment;
    }

    /**
     * Getter, get the next visit in the linked list.
     *
     * @return the next visit in the linked list.
     */
    public Visit getNextVisit(){
        return next;
    }

    /**
     * Setters
     *
     * @param appointment the appointment to set.
     */
    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }

    /**
     * Set the next visit in the linked list.
     *
     * @param next the next visit in the linked list.
     */
    public void setNextVisit(Visit next){
        this.next = next;
    }

    /**
     * Default Constructor to create a visit with null values.
     */
    public Visit(){
        this.appointment = null;
        this.next = null;
    }

    /**
     * Copy Constructor to create a visit with an existing visit's data.
     *
     * @param copyVisit the visit whose data is being copied.
     */
    public Visit(Visit copyVisit){
        this.appointment = copyVisit.appointment;
        this.next = copyVisit.next;
    }

    /**
     * Parameterized Constructor to create visit.
     *
     * @param appointment the appointment where the visit took place.
     * @param next a reference to the next visit in the linked list.
     */
    public Visit(Appointment appointment, Visit next){
        this.appointment = appointment;
        this.next = next;
    }

}
