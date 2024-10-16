package Project_1.src.project;
import Project_1.src.util.Date;

/**
 * Imaging class extends the Appointment class from Project 1 to include the instance var private Radiology room;
 * to keep track of the imaging room, X-ray, ultrasound, or CAT scan for the imaging appointment.
 * Subclass of Appointment
 *
 * TODO: comments
 * TODO: since appointments instance variables are protected, we can just use .date instead of .getDate() since this is a subclass. To make the code cleaner and show you understand inheritance.
 * TODO: Determine if need to add a method related to the radiology room
 * @author Jack Crosby
 */
public class Imaging extends Appointment{
    private Radiology room;

    // Getter
    public Radiology getRadiologyRoom(){
        return room;
    }

    // Setter
    public void setRadiologyRoom(Radiology room){
        this.room = room;
    }

    /**
     * Default Constructor
     */
    public Imaging(){
        super();
        this.room = null;
    }

    /**
     * Parameterized Constructor to create the imaging object.
     *
     * @param date the date of the parent object appointment.
     * @param timeslot the timeslot of the parent object appointment.
     * @param patient the patient of the parent object appointment.
     * @param provider the provider of the parent object appointment.
     * @param room the radiology room of the imaging child object
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room){
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Copy Constructor
     * @param copyImaging
     */
    public Imaging(Imaging copyImaging){
        super(copyImaging);
        this.room = copyImaging.room;
    }

    @Override
    public int compareTo(Appointment appointment){
        if(!(appointment instanceof Imaging)){
            throw new IllegalArgumentException("Invalid comparison");
        }
        Imaging imagingAppointment = (Imaging) appointment;
        int appointmentComparison = super.compareTo(imagingAppointment);
        if (appointmentComparison != 0) {
            return appointmentComparison;
        }
        return this.room.compareTo(imagingAppointment.room);
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!super.equals(object)){
            return false;
        }
        Imaging imaging = (Imaging) object;
        return this.room.equals(imaging.getRadiologyRoom());
    }

    @Override
    public String toString(){
        return super.toString() + "[" + room + "]";
    }

}
