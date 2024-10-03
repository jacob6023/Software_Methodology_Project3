/**
 * @author Vikram kadyan
 */
package Project_1.src.project;

public class List {
    private Appointment[] appointments;
    private int size; //num appointments in the array


    private final int NOT_FOUND = -1;

    // Method to check if an appointment with the same patient, profile, and timeslot already exists
    public boolean appointmentExists(Profile patient, Timeslot timeslot, Date date) {
        for (int i = 0; i < size; i++) {
            Appointment currAppointment = appointments[i];
            if (currAppointment.getProfile().equals(patient) &&
                    currAppointment.getTimeslot() == timeslot &&
                    currAppointment.getDate().equals(date)) {
                return true; // Appointment already exists
            }
        }
        return false;
    }

    // Method to check if a provider is available at a specific timeslot and date
    public boolean isProviderAvailable(Provider provider, Timeslot timeslot, Date date) {
        for (int i = 0; i < size; i++) {
            Appointment currAppointment = appointments[i];
            if (currAppointment.getProvider() == provider &&
                    currAppointment.getTimeslot() == timeslot &&
                    currAppointment.getDate().equals(date)) {
                return false; // Provider is not available
            }
        }
        return true; // Provider is available
    }


    private int find(Appointment appointment) { //helper method
        for (int i = 0; i < appointments.length; i++){
            if (this.appointments[i].equals(appointment)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    private void grow() { //helper method to increase the capacity by 4
        int capInc = 4;
        Appointment [] temp = new Appointment[this.appointments.length + capInc];
        for (int i = 0; i < size; i++) {
            temp[i] = appointments[i];
        }
        this.appointments = temp;
    }

    public boolean contains(Appointment appointment){
        return find(appointment) != NOT_FOUND;
    }


    public void add(Appointment appointment){
        if (size == appointments.length) {
            grow();
        }
        appointments[size] = appointment;
        this.size++;
    }

    public void remove(Appointment appointment){
        int index = this.find(appointment);
        if (index != NOT_FOUND) {
            for (int i = index; i < size; i++) {
                this.appointments[i] = this.appointments[i+1];
            }
        this.size--;
        }
    }


    public void printByPatient() { //ordered by patient profile, date/timeslot
        //sorts by last name, first name, dob, then appointment date and time
        for (int i = 0; i < size - 1; i++) {
            int minimumIndex = i;
            for (int j = i + 1; j < size; j++) {
                int comparison = appointments[j].getProfile().compareTo(appointments[minimumIndex].getProfile());

                if (comparison < 0) {
                    minimumIndex = j;
                } else if (comparison == 0) {
                    int dateComp = appointments[j].getDate().compareTo(appointments[minimumIndex].getDate());
                    if (dateComp < 0) {
                        minimumIndex = j;
                    } else if (dateComp == 0) {
                        int timeComp = appointments[j].getTimeslot().compareTime(appointments[minimumIndex].getTimeslot());
                        if (timeComp < 0) {
                            minimumIndex = j;
                        }
                    }
                }

                if (minimumIndex != i) {
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[minimumIndex];
                    appointments[minimumIndex] = temp;
                }
            }


            //prints the sorted appointments
            for (int j = 0; j < size; i++) {
                System.out.println(appointments[j]);
            }
        }
    }
        public void printByLocation(){ //ordered by county, date/timeslot
            // Sort appointments by location, then date and time
            for (int i = 0; i < size - 1; i++) {
                int minimumIndex = i;
                for (int j = i + 1; j < size; j++) {
                    String countyA = appointments[j].getProvider().getLocation().getCounty();
                    String countyB = appointments[minimumIndex].getProvider().getLocation().getCounty();
                    int comparison = countyA.compareTo(countyB);

                    if (comparison < 0) {
                        minimumIndex = j;
                    } else if (comparison == 0) {
                        int dateComp = appointments[j].getDate().compareTo(appointments[minimumIndex].getDate());
                        if (dateComp < 0) {
                            minimumIndex = j;
                        } else if (dateComp == 0) {
                            int timeComp = appointments[j].getTimeslot().compareTime(appointments[minimumIndex].getTimeslot());
                            if (timeComp < 0) {
                                minimumIndex = j;
                            }
                        }
                    }
                }

                if (minimumIndex != i) {
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[minimumIndex];
                    appointments[minimumIndex] = temp;
                }
            }

            // Print
            for (int i = 0; i < size; i++) {
                System.out.println(appointments[i]);
            }
        }

        //ordered by date/timeslot, provider name
        public void printByAppointment(){
            for (int i = 0; i < size - 1; i++) {
                int minimumIndex = i;
                for (int j = i + 1; j < size; j++) {
                    int dateComp = appointments[j].getDate().compareTo(appointments[minimumIndex].getDate());
                    if (dateComp < 0) {
                        minimumIndex = j;
                    } else if (dateComp == 0) {
                        int timeComp = appointments[j].getTimeslot().compareTime(appointments[minimumIndex].getTimeslot());
                        if (timeComp < 0) {
                            minimumIndex = j;
                        } else if (timeComp == 0) {
                            String providerA = appointments[j].getProvider().name();
                            String providerB = appointments[minimumIndex].getProvider().name();
                            int providerComp = providerA.compareTo(providerB);
                            if (providerComp < 0) {
                                minimumIndex = j;
                            }
                        }
                    }
                }
                if (minimumIndex != i) {
                    Appointment temp = appointments[i];
                    appointments[i] = appointments[minimumIndex];
                    appointments[minimumIndex] = temp;
                }
            }

            // Print
            for (int i = 0; i < size; i++) {
                System.out.println(appointments[i]);
            }
        }

    //identifies the appointment to be cancelled when reschedule is called
    public Appointment identifyAppointment(Profile patient,  Provider provider, Timeslot timeslot, Date date) {
        for (int i = 0; i <= size; i++) {
            Appointment currAppointment = appointments[i];

            if (currAppointment.getProfile().equals(patient) &&
                    currAppointment.getProvider() == provider &&
                    currAppointment.getTimeslot() == timeslot &&
                    currAppointment.getDate().equals(date)) { // Added Date comparison
                return currAppointment;
            }
        }
        return null;
    }

    }

