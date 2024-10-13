package Project_1.src.project;
/**
 * @author Vikram kadyan
 */
import java.util.Scanner;
import java.util.StringTokenizer;

public class Scheduler {
    private List appointments;
    private MedicalRecord medRecord;

    // Getters
    public List getAppointments() {return appointments;}
    public MedicalRecord getMedRecord() {return medRecord;}

    // Setter for appointments
    public void setAppointments(List appointments) {this.appointments = appointments;}
    public void setMedRecord(MedicalRecord medRecord) {this.medRecord = medRecord;}

    // Default Constructor
    public Scheduler() {
        this.appointments = new List();
        this.medRecord = new MedicalRecord();
    }

    // Parameterized constructor
    public Scheduler(List appointments, MedicalRecord medRecord) {
        this.appointments = appointments;
        this.medRecord = medRecord;
    }

    // Copy Constructor
    public Scheduler (Scheduler scheduler){
        this.appointments = scheduler.appointments;
        this.medRecord = scheduler.medRecord;
    }

    public void run() {
        System.out.println("Scheduler is running.");
        Scanner scan = new Scanner(System.in); //start scanner
        while (true) {
            String command = scan.nextLine();
            if (command.isEmpty()) {
                // Ignore empty lines
                continue;
            }
            String comm = command.split(",")[0].trim(); // finds letter command and gets rid of space
            if (!comm.equals(comm.toUpperCase())) { // check if user entered lowercase command
                System.out.println("Invalid command!");
                continue;
            }
            switch (comm) {
                case "S":
                    scheduleAppointment(command);
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
                    System.out.println("Scheduler terminated.");
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private void scheduleAppointment(String command) {
        String[] tokens = command.split(",");

        /**
         * Handling app date cases
         */
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

        String slotNumString = tokens[2].trim();
        Timeslot timeslot  = null;
        int slotNum;
        try{
            slotNum = Integer.parseInt(slotNumString);
            if(slotNum < 1 || slotNum > 6){
                System.out.println(slotNumString + " is not a valid time slot.");
                return;
            }
            timeslot = Timeslot.valueOf("SLOT" + slotNum);
        } catch (NumberFormatException e) {
            System.out.println(slotNumString + " is not a valid time slot.");
            return;
        }


        /**
         * Profile info
         * handling
         */
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
        for (int i = 0; i < appointments.getSize(); i++) {
            Appointment appointment = appointments.getAppointmentAt(i);
            boolean sameProfile = appointment.getProfile().equals(patientProfile);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name());
            if (sameProfile && sameDate && sameTimeslot) {
                System.out.println(appointment.getProfile().toString() + " has an existing appointment at the same time slot.");
                return;
            }
        }

        // Provider. Also checking if the provider DNE
        String providerName = tokens[6].trim();
        Provider provider;
        try {
            provider = Provider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(providerName + " - provider doesn't exist.");
            return;
        }

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

        Visit newVisit = new Visit(scheduledAppointment, null);
        Patient patient = new Patient(patientProfile, newVisit);
        medRecord.add(patient);

    }

    /**
     * The system shall use the patient's profile and appointment date and time to uniquely identify an appointment in the
     * appointment calendar. Since the system checks the invalid data tokens before adding the appointment to the calendar, the
     * system does not check the invalid data tokens when canceling an appointment.
     * However, the appointment being canceled may not exist in the appointment calendar
     * @param command
     */
    private void cancelAppointment(String command) {
        String[] tokens = command.split(",");
        Date appointmentDate = new Date(tokens[1].trim());

        int slotNum = Integer.parseInt(tokens[2].trim());
        Timeslot timeslot = Timeslot.valueOf("SLOT" + slotNum);

        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        String providerName = tokens[6].trim().toUpperCase();
        Provider provider;
        try {
            provider = Provider.valueOf(providerName);
        } catch (IllegalArgumentException e) {
            System.out.println(providerName + " - provider doesn't exist.");
            return;
        }

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
     * reschedule an appointment to a different timeslot on the same day with the same provider
     * the system shall use the patient's profile and appointment date to uniquely identify an appointment in the appointment calendar for rescheduling
     * the system shall check if the specified appointment exists,
     * AND if the new timeslot indicated at the end of the command line is valid and available from the provider in the original appointment
     * @param command
     */
    private void rescheduleAppointment(String command) {
        String[] tokens = command.split(",");

        Date appointmentDate = new Date(tokens[1].trim());

        String slotNumString = tokens[2].trim();
        Timeslot timeslot  = null;
        try{
            int slotNum = Integer.parseInt(slotNumString);
            if(slotNum < 1 || slotNum > 6){
                System.out.println(slotNumString + " is not a valid time slot.");
                return;
            }
            timeslot = Timeslot.valueOf("SLOT" + slotNum);
        } catch (NumberFormatException e) {
            System.out.println(slotNumString + " is not a valid time slot.");
            return;
        }

        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // need to get the Provider. check if appointment exists and get the provider through that
        // use patient's profile and appointment date and time to uniquely identify an appointment in the calendar for rescheduling
        Provider provider = null;
        Appointment originalAppointment = null;
        for(int i = 0 ; i < appointments.getSize(); i++){
            Appointment appointment = appointments.getAppointmentAt(i);
            boolean sameProfile = appointment.getProfile().equals(patientProfile);
            boolean sameAppointmentDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().name().equalsIgnoreCase(timeslot.name()); // confirm if comparing name is right
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

        String newSlotNumString = tokens[6].trim();
        Timeslot rescheduledTimeslot  = null;
        int slotNum;
        try{
            slotNum = Integer.parseInt(newSlotNumString);
            if(slotNum < 1 || slotNum > 6){
                System.out.println(slotNumString + " is not a valid time slot.");
                return;
            }
            rescheduledTimeslot = Timeslot.valueOf("SLOT" + slotNum);
        } catch (NumberFormatException e) {
            System.out.println(slotNumString + " is not a valid time slot.");
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















//        Appointment scheduledAppointment = new Appointment(appointmentDate, null, patientProfile, provider);
//
//        // Process the new timeslot the user wants to reschedule to. check if valid
//        int rescheduledSlotNum = Integer.parseInt(tokens[6].trim());
//        if(rescheduledSlotNum < 1 || rescheduledSlotNum > 6){
//            System.out.println("Timeslot does not exist");
//            return;
//        }
//        Timeslot rescheduledTimeslot = Timeslot.valueOf("SLOT" + rescheduledSlotNum);
//
//        // check if the new timeslot (rescheduled timeslot) is available (at this provider)
//        // use provider, appointmentDate, and rescheduledTimeslot
//        for(int i = 0; i < appointments.getSize(); i++){
//            Appointment appointment = appointments.getAppointmentAt(i);
//            assert provider != null;
//            boolean sameProvider = appointment.getProvider().name().equalsIgnoreCase(provider.name());
//            boolean sameAppointmentDate = appointment.getDate().equals(appointmentDate);
//            if(sameProvider && sameAppointmentDate){
//                if(appointment.getTimeslot() == null) {
//                    scheduledAppointment.setTimeslot(rescheduledTimeslot);
//                }else{
//                    System.out.println("Timeslot is currently booked");
//                }
//            }
//
//        }

    }


}



