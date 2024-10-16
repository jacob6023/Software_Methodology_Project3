package Project_1.src.project;
import Project_1.src.util.Date;
import Project_1.src.util.List;
import Project_1.src.util.Sort;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

/**
 * This is the user interface class to process the command lines entered on the terminal.
 * Will schedule, cancel, reschedule, compute the charge a patient owes, and print out sorted lists of appointments.
 *
 * @author Jack Crosby
 */

public class ClinicManager {
    private List<Appointment> appointments;
    private List<Provider> providers;
    private List<Technician> technicianRotation;
    private int currentTechnicianIndex;

    // Getters
    public List<Appointment> getAppointments() {return appointments;}
    public List<Provider> getProviders() {return providers;}
    public List<Technician> getTechnicianRotation() {return technicianRotation;}
    public int getCurrentTechnicianIndex(){return currentTechnicianIndex;}

    // Setters
    public void setAppointments(List<Appointment> appointments) {this.appointments = appointments;}
    public void setProviders(List<Provider> providers) {this.providers = providers;}
    public void setTechnicianRotation(List<Technician> technicianRotation) {this.technicianRotation = technicianRotation;}
    public void setCurrentTechnicianIndex(int currentTechnicianIndex){this.currentTechnicianIndex = currentTechnicianIndex;}

    /**
     * Default Constructor to create scheduler with default values.
     * Uses Appointments and MedicalRecords default constructors.
     */
    public ClinicManager() {
        this.appointments = new List<>();
        this.providers = new List<>();
        this.technicianRotation = new List<>();
        this.currentTechnicianIndex = 0;
    }

    /**
     * Parameterized Constructor to create the scheduler.
     *
     * @param appointments the appointments list/calendar list being modified according to user input.
     * @param providers the provider list being modified if user schedules an appointment and has not been canceled.
     */
    public ClinicManager(List<Appointment> appointments, List<Provider> providers, List<Technician> technicianRotation, int currentTechnicianIndex) {
        this.appointments = appointments;
        this.providers = providers;
        this.technicianRotation = technicianRotation;
        this.currentTechnicianIndex = currentTechnicianIndex;
    }

    /**
     * Copy Constructor to create a new scheduler with an existing scheduler.
     *
     * @param clinicManager the ClinicManager whose data is being copied into new scheduler.
     */
    public ClinicManager (ClinicManager clinicManager){
        this.appointments = clinicManager.appointments;
        this.providers = clinicManager.providers;
        this.technicianRotation = clinicManager.technicianRotation;
        this.currentTechnicianIndex = clinicManager.currentTechnicianIndex;
    }

    private boolean appointmentExists(Date date, Timeslot timeslot, Profile profile) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProfile = appointment.getPatient().getProfile().equals(profile);
            boolean sameDate = appointment.getDate().equals(date);
            boolean sameTimeslot = appointment.getTimeslot().equals(timeslot);
            if (sameProfile && sameDate && sameTimeslot) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to find a doctor by their NPI.
     *
     * @param npi the NPI of the doctor.
     * @return the doctor if found, or null if not found.
     */
    private Doctor findDoctorByNPI(String npi) {
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i) instanceof Doctor doctor) {
                if (doctor.getNPI().equals(npi)) {
                    return doctor;
                }
            }
        }
        return null;
    }


    /**
     * Runs the user interface.
     */
    public void run() {
        loadProviders();
        displayProviders();
        displayTechnicianRotation();
        System.out.println("Clinic Manager is running.");
        System.out.println();
        System.out.println();
        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if (command.isEmpty()) {
                continue; // ignore empty lines
            }
            String comm = command.split(",")[0].trim(); // find letter command and get rid of space
            if (!comm.equals(comm.toUpperCase())) { // check if user entered lowercase command
                System.out.println("Invalid command!");
                continue;
            }
            switch (comm) {
                case "D":
                    scheduleDoctorAppointment(command);
                    break;
                case "T":
                    scheduleTechnicianAppointment(command);
                    break;
                case "C":
                    cancelAppointment(command);
                    break;
                case "R":
                    rescheduleAppointment(command);
                    break;
                case "PA":
                    appointments.printByAppointment();
                    break;
                case "PP":
                    appointments.printByPatient();
                    break;
                case "PL":
                    appointments.printByLocation();
                    break;
                case "PS":
                    medRecord.printBillingStatement();
                    break;
                case "Q":
                    System.out.println("Clininc Manager terminated.");
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    public class DateUtils {
        public static Date parseDate(String dateStr) {
            String[] dateSections = dateStr.trim().split("/");
            if (dateSections.length != 3) return null;

            try {
                int month = Integer.parseInt(dateSections[0]);
                int day = Integer.parseInt(dateSections[1]);
                int year = Integer.parseInt(dateSections[2]);
                Date date = new Date(year, month, day);
                return date.isValid() ? date : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        public static boolean isValidAppointmentDate(Date date) {
            return !(date.isToday() || date.isDayBeforeToday() || date.isWeekend() || date.isNotWithinSixMonthsFromToday());
        }
    }

    public class TimeslotUtils {
        public static Timeslot parseTimeslot(String timeslotStr) {
            try {
                int slotNum = Integer.parseInt(timeslotStr.trim());
                if (slotNum < 1 || slotNum > 6) {
                    return null;
                }
                Timeslot timeslot = new Timeslot();
                timeslot.assignSlot(slotNum);
                return timeslot;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public class ProfileUtils {
        public static Profile createProfile(String firstName, String lastName, String dobString) {
            Date dob = DateUtils.parseDate(dobString);
            if (dob == null || !dob.realDOB()) {
                return null;
            }
            return new Profile(firstName.trim(), lastName.trim(), dob);
        }
    }




    /**
     * It shall  automatically load the list of providers from the text file “providers.txt” in the project folder
     */
    public void loadProviders(){
        File file = new File("providers.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\\s+");
            String command = tokens[0].trim();
            String firstName = tokens[1].trim();
            String lastName = tokens[2].trim();
            String dob = tokens[3].trim();
            String locationString = tokens[4].trim();
            Location location = Location.valueOf(locationString.toUpperCase());
            Date date = new Date(dob);
            Profile profile = new Profile(firstName, lastName, date);
            if(command.equals("D")){
                String specialtyString = tokens[5].trim();
                Specialty specialty = Specialty.valueOf(specialtyString.toUpperCase());
                String npi = tokens[6].trim();
                Doctor doctor = new Doctor(profile, location, specialty, npi);
                providers.add(doctor);
            }
            if(command.equals("T")){
                String ratePerVisitString = tokens[5].trim();
                int ratePerVisit = Integer.parseInt(ratePerVisitString);
                Technician technician = new Technician(profile, location, ratePerVisit);
                providers.add(technician);
            }
        }
        Sort.provider(providers);
        System.out.println("Providers loaded to the list.");
        scanner.close();

    }

    /**
     * Display the list after it is loaded, sorted by provider profile. It also creates a rotation list of technicians for  scheduling imaging appointments
     */
    public void displayProviders(){
        for (int i = 0; i < providers.size(); i++) {
            System.out.println(providers.get(i).toString());
        }
        System.out.println();
    }

    // Get the next technician in the rotation
    public Technician getNextTechnician() {
        if (technicianRotation.isEmpty()) {
            throw new IllegalStateException("No technicians available.");
        }
        Technician technician = technicianRotation.get(currentTechnicianIndex);
        currentTechnicianIndex = (currentTechnicianIndex + 1) % technicianRotation.size(); // Rotate to next technician
        return technician;
    }

    /**
     * Display the rotation list of technicians for scheduling imaging appointments.
     */
    public void displayTechnicianRotation() {
        System.out.println("Rotation list for the technicians.");
        for (int i = 0; i < technicianRotation.size(); i++) {
            System.out.print(technicianRotation.get(i).getProfile().get_fname() + technicianRotation.get(i).getProfile().get_lname() + " (" + technicianRotation.get(i).getLocation() + ")");
            if (i < technicianRotation.size() - 1) {
                System.out.print(" --> ");
            }
        }
        System.out.println();
    }

    /**
     * Schedules the appointment.
     * Handles a variety of cases that prevent the appointment from being scheduled.
     *
     * @param command the schedule command.
     */
    private void scheduleDoctorAppointment(String command) {
        String[] tokens = command.split(",");

        // Appointment date handling
        String appDate = tokens[1].trim(); // <------ Using for invalid calendar dates
        String [] dateSections = appDate.split("/");
        if(dateSections.length != 3){
            System.out.println("Appointment date: " + appDate + " is not a valid calendar date.");
            return;
        }
        String monthString = dateSections[0];
        String dayString = dateSections[1];
        String yearString = dateSections[2];
        if(monthString.length() > 2 || dayString.length() > 2 || yearString.length() != 4){
            System.out.println("Appointment date: " + appDate + " is not a valid calendar date.");
            return;
        }
        int monthInt = Integer.parseInt(monthString);
        int dayInt = Integer.parseInt(dayString);
        int yearInt = Integer.parseInt(yearString);
        Date appointmentDate = new Date(yearInt, monthInt, dayInt);
        if(!appointmentDate.isValid()){
            System.out.println("Appointment date: " + appointmentDate.toString() + " is not a valid calendar date.");
            return;
        }
        if(appointmentDate.isToday() || appointmentDate.isDayBeforeToday()){
            System.out.println("Appointment date: " + appointmentDate.toString() + " is today or a date before today");
            return;
        }
        if(appointmentDate.isWeekend()){
            System.out.println("Appointment date: " + appointmentDate.toString() + " is Saturday or Sunday");
            return;
        }
        if(appointmentDate.isNotWithinSixMonthsFromToday()){
            System.out.println("Appointment date: " + appointmentDate.toString() + " is not within six months");
            return;
        }

        // Timeslot handling
        String slotNumString = tokens[2].trim();
        Timeslot timeslot = new Timeslot();
        int slotNum;
        try{
            slotNum = Integer.parseInt(slotNumString);
            if(slotNum < 1 || slotNum > 6){
                System.out.println(slotNumString + " is not a valid time slot.");
                return;
            }
            timeslot.assignSlot(slotNum);
        } catch (NumberFormatException e) {
            System.out.println(slotNumString + " is not a valid time slot.");
            return;
        }


        // Profile info handling
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        String patientDOBstring = tokens[5].trim(); // <------ Using for invalid dob dates
        String[] DOBsections = patientDOBstring.split("/");
        if(DOBsections.length != 3){
            System.out.println("Patient DOB: " + patientDOBstring + " is not a valid calendar date.");
            return;
        }
        String DOBmonthString = DOBsections[0];
        String DOBdayString = DOBsections[1];
        String DOByearString = DOBsections[2];
        if(DOBmonthString.length() > 2 || DOBdayString.length() > 2 || DOByearString.length() != 4){
            System.out.println("Patient DOB: " + patientDOBstring + " is not a valid calendar date.");
            return;
        }
        int DOBmonthInt = Integer.parseInt(DOBmonthString);
        int DOBdayInt = Integer.parseInt(DOBdayString);
        int DOByearInt = Integer.parseInt(DOByearString);
        Date patientDOB = new Date(DOByearInt, DOBmonthInt, DOBdayInt);
        if(!patientDOB.isValid()){
            System.out.println("Patient DOB: " + patientDOBstring + " is not a valid calendar date");
            return;
        }
        if(!patientDOB.realDOB()){
            System.out.println("Patient DOB: " + patientDOBstring + " is today or a date after today.");
            return;
        }
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // check if an appointment with the same profile, date, and timeslot already exists
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProfile = appointment.getProfile().equals(patientProfile);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name());
            if (sameProfile && sameDate && sameTimeslot) {
                System.out.println(appointment.getProfile().toString() + " has an existing appointment at the same time slot.");
                return;
            }
        }

        // Provider handling. Also checking if the provider DNE
        String npi = tokens[6].trim();
        Doctor doctor = findDoctorByNPI(npi);
        if (doctor == null) {
            System.out.println(npi + " - provider doesn't exist.");
            return;
        }
        // Create a new appointment
        Appointment newAppointment = new Appointment(appointmentDate, timeslot, new Patient(patientProfile, null), doctor);
        appointments.add(newAppointment);
        System.out.println("Appointment successfully scheduled with doctor: " + doctor.getProfile().toString());

        // Check if provider is not available at the specified timeslot
        for(int j = 0; j < appointments.getSize(); j++){
            Appointment appointment = appointments.getAppointmentAt(j);
            boolean sameProvider = appointment.getProvider().name().equalsIgnoreCase(provider.name());
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name());
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            if (sameProvider && sameTimeslot && sameDate) {
                System.out.println(appointment.getProvider().toString() + " is not available at slot " + slotNum + ".");
                return;
            }
        }
        // Schedule the appointment, create the appointment with the info and add it to the list
        Appointment scheduledAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        appointments.add(scheduledAppointment);
        System.out.println(scheduledAppointment.toString() + " booked.");

        // Create the linked List or add to linked list if patient has already had appointment
        Visit newVisit = new Visit(scheduledAppointment, null);
        Patient patient = new Patient(patientProfile, newVisit);
        //appointments.add(patient); // add patient to the medical record

    }

    public void scheduleTechnicianAppointment(String command){

    }

    /**
     * The system shall use the patient's profile and appointment date and time to uniquely identify an appointment in the
     * appointment calendar. Since the system checks the invalid data tokens before adding the appointment to the calendar, the
     * system does not check the invalid data tokens when canceling an appointment.
     * However, the appointment being canceled may not exist in the appointment calendar
     *
     * @param command command to cancel the appointment
     */
    private void cancelAppointment(String command) {
        String[] tokens = command.split(",");
        Date appointmentDate = new Date(tokens[1].trim()); // getting appointment date

        // Getting Timeslot
        int slotNum = Integer.parseInt(tokens[2].trim());
        Timeslot timeslot = Timeslot.valueOf("SLOT" + slotNum);

        // Getting patient profile
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // Getting provider
        String providerName = tokens[6].trim().toUpperCase();
        Provider provider;
        try {
            provider = Provider.valueOf(providerName);
        } catch (IllegalArgumentException e) {
            System.out.println(providerName + " - provider doesn't exist.");
            return;
        }

        // Canceling the appointment
        boolean appointmentFound = false;
        for (int i = 0; i < appointments.getSize(); i++) {
            Appointment appointment = appointments.getAppointmentAt(i);
            boolean sameProfile = appointment.getProfile().equals(patientProfile);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name());
            boolean sameProvider = appointment.getProvider().name().equalsIgnoreCase(provider.name());
            if (sameProfile && sameDate && sameTimeslot && sameProvider) {
                appointments.remove(appointment);
                System.out.println(appointment.getDate().toString() + " " + appointment.getTimeslot().toString() + " " + appointment.getProfile().toString() + " has been canceled.");
                appointmentFound = true;
                break;
            }
        }
        if (!appointmentFound) {
            System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patientProfile.toString() + " does not exist.");
            return;
        }

    }

    /**
     * Reschedule an appointment to a different timeslot on the same day with the same provider.
     * The system shall use the patient's profile and appointment date to uniquely identify an appointment in the appointment calendar for rescheduling.
     * The system shall check if the specified appointment exists.
     * If the new timeslot indicated at the end of the command line is valid and available from the provider in the original appointment.
     *
     * @param command the reschedule command.
     */
    private void rescheduleAppointment(String command) {
        String[] tokens = command.split(",");
        Date appointmentDate = new Date(tokens[1].trim()); // Getting appointment date

        // Handling timeslot
        String ogSlotNumString = tokens[2].trim();
        int ogSlotNum;
        Timeslot timeslot  = null;
        try{
            ogSlotNum = Integer.parseInt(ogSlotNumString);
            if(ogSlotNum < 1 || ogSlotNum > 6){
                System.out.println(ogSlotNumString + " is not a valid time slot.");
                return;
            }
            timeslot = Timeslot.valueOf("SLOT" + ogSlotNum);
        } catch (NumberFormatException e) {
            System.out.println(ogSlotNumString + " is not a valid time slot.");
            return;
        }

        // Getting patient profile
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // Finding appointment to reschedule
        // need to get the Provider. check if appointment exists and get the provider through that
        // use patient's profile and appointment date and time to uniquely identify an appointment in the calendar for rescheduling
        Provider provider = null;
        Appointment originalAppointment = null;
        for(int i = 0 ; i < appointments.getSize(); i++){
            Appointment appointment = appointments.getAppointmentAt(i);
            boolean sameProfile = appointment.getProfile().equals(patientProfile);
            boolean sameAppointmentDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name());
            if (sameProfile && sameAppointmentDate && sameTimeslot) {
                originalAppointment = appointment;
                provider = appointment.getProvider();
                break;  // appointment found
            }
        }
        if(originalAppointment == null){
            System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patientProfile.toString() + " does not exist.");
            return;
        }

        // Getting new timeslot to reschedule
        String newSlotNumString = tokens[6].trim();
        Timeslot rescheduledTimeslot  = null;
        int newSlotNum;
        try{
            newSlotNum = Integer.parseInt(newSlotNumString);
            if(newSlotNum < 1 || newSlotNum > 6){
                System.out.println(newSlotNumString + " is not a valid time slot.");
                return;
            }
            rescheduledTimeslot = Timeslot.valueOf("SLOT" + newSlotNum);
        } catch (NumberFormatException e) {
            System.out.println(newSlotNumString + " is not a valid time slot.");
            return;
        }

        // Check if the new timeslot is available with the same provider
        for (int i = 0; i < appointments.getSize(); i++) {
            Appointment appointment = appointments.getAppointmentAt(i);
            boolean sameProvider = appointment.getProvider().name().equalsIgnoreCase(provider.name());
            boolean sameAppointmentDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(rescheduledTimeslot.name());
            if (sameProvider && sameAppointmentDate && sameTimeslot) {
                System.out.println(appointment.getProvider().toString() +  " is not available at slot " + newSlotNumString + ".");
                return;
            }
        }
        originalAppointment.setTimeslot(rescheduledTimeslot);
        System.out.println("Rescheduled to " + originalAppointment.toString());

    }

}



