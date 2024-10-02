package Project_1.src.project;

public class Visit {
    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    /**
     * This class defines a node in a singly linked list that maintains the list of visits. You can add  constructors and methods, but you CANNOT add/change the instance variables, or -2 points for each violation.
     */



    /**
     * getters
     */
    public Appointment getAppointment(){return appointment;}
    public Visit getVisit(){return next;}

    /**
     * setters
     * @param appointment
     */
    public void setAppointment(Appointment appointment){this.appointment = appointment;}
    public void setVisit(Visit next){this.next = next;}

    /**
     * Constructors
     */
    public Visit(){
        this.appointment = null;
        this.next = null;
    }

    /**
     * parameter constructor
     * @param appointment
     * @param next
     */
    public Visit(Appointment appointment, Visit next){
        this.appointment = appointment;
        this.next = next;
    }

    /**
     * copy constructor
     */
    public Visit(Visit copyVisit){
        this.appointment = copyVisit.appointment;
        this.next = copyVisit.next;
    }

}
