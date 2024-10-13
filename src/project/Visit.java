package Project_1.src.project;

/**
 * This class defines a node in a singly linked list that maintains the list of visits.
 */
public class Visit {
    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    // Getters
    public Appointment getAppointment(){return appointment;}
    public Visit getVisit(){return next;}

    // Setters
    public void setAppointment(Appointment appointment){this.appointment = appointment;}
    public void setVisit(Visit next){this.next = next;}

    // Default Constructor
    public Visit(){
        this.appointment = null;
        this.next = null;
    }

    // Parameterized Constructor
    public Visit(Appointment appointment, Visit next){
        this.appointment = appointment;
        this.next = next;
    }

    // copy constructor
    public Visit(Visit copyVisit){
        this.appointment = copyVisit.appointment;
        this.next = copyVisit.next;
    }

}
