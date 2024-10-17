package Project_1.src.project;
import Project_1.src.util.Date;

/**
 * This class defines the imaging appointment with properties radiology room.
 *
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
     *
     * @param copyImaging the imaging object to be copied.
     */
    public Imaging(Imaging copyImaging){
        super(copyImaging);
        this.room = copyImaging.room;
    }

    /**
     * Compare the imaging appointment.
     *
     * @param appointment the imaging appointment to be compared.
     * @return in declaration order: -1 if before, 0 if same, 1 if after.
     */
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

    /**
     * Determine if the imaging appointment is equal to another object.
     *
     * @param object the object to be compared.
     * @return true if the imaging appointment is equal to the object, false otherwise.
     */
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

    /**
     * Output the imaging appointment in format "Appointment: date timeslot patient provider [room]".
     *
     * @return String formatted in values date timeslot patient provider [room].
     */
    @Override
    public String toString(){
        return super.toString() + "[" + room + "]";
    }

}
