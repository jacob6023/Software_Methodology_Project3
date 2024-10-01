package Project_1.src.project;

public class Visit {
    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    /**
     * This class defines a node in a singly linked list that maintains the list of visits. You can add  constructors and methods, but you CANNOT add/change the instance variables, or -2 points for each violation.
     */


    /**
     * check if we are at the end of the list
     */
    public boolean atEnd(Appointment appointment, Visit next){

    }



    /**
     * getters
     * @param appointment
     */
    public Appointment getAppointment(){return appointment;}
    public Visit getVisit(){return next;}

    /**
     * setters
     * @param appointment
     */
    public void setAppointment(Appointment appointment){this.appointment = appointment;}
    public void setVisit(Visit next){this.next = next;}

    public Visit(){
        this.appointment = NULL;
        this.next = NULL;
    }

    public Visit(Appointment appointment){
        this.appointment = appointment;
        this.next = next;
    }

}
