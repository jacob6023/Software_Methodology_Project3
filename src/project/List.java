package Project_1.src.project;
import java.util.Calendar;

/**
 * This class creates a list of appointments/calendar of appointments.
 *
 * @author Vikram kadyan
 * @author Jack Crosby
 */
public class List {
    private Appointment[] appointments;
    private int size; //num appointments in the array

    // Constant int to indicate if appointment has not been found
    private final int NOT_FOUND = -1;

    //Getters
    public Appointment[] getAppointments(){return appointments;}
    public int getSize(){return size;}

    //setters
    public void setAppointments(Appointment[] appointments){this.appointments = appointments;}
    public void setSize(int size){this.size = size;}

    /**
     * Parameterized Constructor to create the list of appointments and its size.
     *
     * @param appointments array of appointments.
     * @param size size of the array.
     */
    public List(Appointment[] appointments, int size){
        this.appointments = appointments;
        this.size = size;
    }

    /**
     * Default constructor to initialize an empty List of default length 4 (size !=4) with nothing in it (size = 0).
     */
    public List(){
        this.appointments = new Appointment[4];
        this.size = 0;
    }

    /**
     * Copy constructor to copy the existing List.
     *
     * @param copyAppointments List argument being sent to copy.
     */
    public List(List copyAppointments){
        this.appointments = copyAppointments.getAppointments();
        this.size = copyAppointments.getSize();
    }

    /**
     * Helper method to search for an appointment in the list.
     *
     * @param appointment the appointment being searched for.
     * @return NOT_FOUND if appointment isn't in appointments[].
     */
    private int find(Appointment appointment){
        for (int i = 0; i < size; i++) { // Only iterate up to size (number of valid elements)
            if (appointments[i].equals(appointment)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Helper method to increase the capacity by 4.
     */
    private void grow(){
        Appointment[] oldApps = this.appointments;
        this.appointments = new Appointment[appointments.length + 4];
        for(int i = 0; i < oldApps.length; i++){
            appointments[i] = oldApps[i];
        }
    }

    /**
     * Method to determine if the appointment is in the list of appointments.
     *
     * @param appointment appointment we are determining if is in the list.
     * @return true if list contains the argument appointment, false if not found.
     */
    public boolean contains(Appointment appointment){
        return find(appointment) != NOT_FOUND;
    }

    /**
     * Add appointment to the list.
     *
     * @param appointment appointment being added to the list.
     */
    public void add(Appointment appointment){
        if (size == appointments.length) {
            grow();
        }
        if(find(appointment) == NOT_FOUND){
            appointments[size] = appointment;
            size++;
        }
    }

    /**
     * Remove appointment from the list.
     *
     * @param appointment appointment being removed from the list.
     */
    public void remove(Appointment appointment){
        int appNum = find(appointment);
        if (appNum != NOT_FOUND) {
            for (int i = appNum; i < size - 1; i++) {
                appointments[i] = appointments[i + 1];  // Shift elements left
            }
            appointments[size - 1] = null;
            size--;
        }
    }

    /**
     * Gets the appointment at the specified index.
     * Use for interface when checking if the scheduling appointment's patient profile, date, and timeslot already exist.
     * Using this to iterate through the array in our interface.
     * Since appointments is a custom list class, not a built-in array, we can’t use square brackets ([]) for indexing. This is a way to access individual appointments
     *
     * @param index the index of the appointment to retrieve.
     * @return the appointment at the specified index. Index is out of bounds otherwise.
     */
    public Appointment getAppointmentAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return appointments[index];
    }

    /**
     * Print the list ordered by patient profile, date/timeslot
     * PP command to display the list of appointments,
     * sorted by the patient by last name, first name, date of birth, appointment date, appointment time.
     */
    public void printByPatient(){
        if(size == 0) System.out.println("The schedule calendar is empty.");
        for(int i = 0; i < size - 1; i++){
            for(int j = i + 1; j < size; j++){
                int compareProfile = appointments[i].getProfile().compareTo(appointments[j].getProfile());
                if(compareProfile > 0){ // < 0: alphabet comes before: a comes before b returns < 0
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[j];
                    appointments[j] = temp;
                } else if(compareProfile == 0){ // same profile but could have multiple appointments
                    int compareDate = appointments[i].getDate().compareTo(appointments[j].getDate());
                    if(compareDate > 0){ // < 0 if the  number is smaller than compared num
                        Appointment temp = appointments[i];
                        appointments[i] = appointments[j];
                        appointments[j] = temp;
                    } else if(compareDate == 0){ // very specific case: same profile and same date
                        int compareTime = appointments[i].getTimeslot().compareTime(appointments[j].getTimeslot());
                        if(compareTime > 0){
                            Appointment temp = appointments[i];
                            appointments[i] = appointments[j];
                            appointments[j] = temp;
                        }
                    }
                }
            }
        }
        if(size != 0){
            System.out.println("** Appointments ordered by patient/date/time **");
            for(int k = 0; k < size; k++){
                System.out.println(appointments[k].toString());
            }
            System.out.println("** end of list **");
            System.out.println();
        }

    }

    /**
     * Print the list ordered by county, date/timeslot.
     * PL command to display the list of appointments, sorted by the county name, appointment date, appointment time.
     */
    public void printByLocation(){
        if(size == 0) System.out.println("The schedule calendar is empty.");
        for(int i = 0; i < size - 1; i++){
            for(int j = i + 1; j < size; j++){
                int compareCounty = appointments[i].getProvider().getLocation().compareTo(appointments[j].getProvider().getLocation());
                if(compareCounty > 0){
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[j];
                    appointments[j] = temp;
                }else if(compareCounty == 0){
                    int compareDate = appointments[i].getDate().compareTo(appointments[j].getDate());
                    if(compareDate > 0){
                        Appointment temp = appointments[i];
                        appointments[i] = appointments[j];
                        appointments[j] = temp;
                    }else if(compareDate == 0){
                        int compareTimeslot = appointments[i].getTimeslot().compareTime(appointments[j].getTimeslot());
                        if(compareTimeslot > 0){
                            Appointment temp = appointments[i];
                            appointments[i] = appointments[j];
                            appointments[j] = temp;
                        }
                    }
                }

            }
        }

        if(size != 0){
            System.out.println("** Appointments ordered by county/date/time **");
            for(int k = 0; k < size; k++){
                System.out.println(appointments[k].toString());
            }
            System.out.println("** end of list ** ");
            System.out.println();
        }
    }

    /**
     * Print the list ordered by date/timeslot, provider name.
     * PA command to display the list of appointments, sorted by appointment date, appointment time, provider’s name.
     */
    public void printByAppointment(){
        if(size == 0) System.out.println("The schedule calendar is empty.");
        for(int i = 0; i < size - 1; i++){
            for(int j = i + 1; j < size; j++){
                int compareDate = appointments[i].getDate().compareTo(appointments[j].getDate());
                if(compareDate > 0){
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[j];
                    appointments[j] = temp;
                } else if(compareDate == 0){ // if same date
                    int compareTimeslot = appointments[i].getTimeslot().compareTime(appointments[j].getTimeslot());
                    if(compareTimeslot > 0){
                        Appointment temp = appointments[i];
                        appointments[i] = appointments[j];
                        appointments[j] = temp;
                    } else if(compareTimeslot == 0){ // if same timeslot
                        int compareProvider = appointments[i].getProvider().compareTo(appointments[j].getProvider());
                        if(compareProvider > 0){ // last check to see if it's different provider
                            Appointment temp = appointments[i];
                            appointments[i] = appointments[j];
                            appointments[j] = temp;
                        }
                    }
                }
            }
        }
        if(size != 0){
            System.out.println("** Appointments ordered by date/time/provider **");
            for(int k = 0; k < size; k++){
                System.out.println(appointments[k].toString());
            }
            System.out.println("** end of list **");
            System.out.println();
        }
    }

}

