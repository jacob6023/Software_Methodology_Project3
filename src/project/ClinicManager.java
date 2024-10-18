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
 * TODO: confirm sorting
 *
 * @author Jack Crosby
 */

public class ClinicManager {
    private List<Appointment> appointments;
    private List<Provider> providers;
    private List<Patient> medicalRecord; // use to print billing statement
    private List<Technician> technicianRotation;
    private List<Imaging> imagingAppointments;
    private int currentTechnicianIndex;

    private static final int technicianRotationLength = 6;

    // Getters
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public List<Patient> getMedicalRecord(){
        return medicalRecord;
    }

    public List<Technician> getTechnicianRotation() {
        return technicianRotation;
    }

    public List<Imaging> getImagingAppointments(){
        return imagingAppointments;
    }

    public int getCurrentTechnicianIndex(){
        return currentTechnicianIndex;
    }

    // Setters
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;

    }

    public void setMedicalRecord(List<Patient> patients){
        this.medicalRecord = medicalRecord;
    }

    public void setTechnicianRotation(List<Technician> technicianRotation) {
        this.technicianRotation = technicianRotation;

    }

    public void setImagingAppointments(List<Imaging> imagingAppointments){
        this.imagingAppointments = imagingAppointments;
    }

    public void setCurrentTechnicianIndex(int currentTechnicianIndex){
        this.currentTechnicianIndex = currentTechnicianIndex;
    }

    /**
     * Default Constructor to create scheduler with default values.
     * Uses Appointments and MedicalRecords default constructors.
     */
    public ClinicManager() {
        this.appointments = new List<>();
        this.providers = new List<>();
        this.medicalRecord = new List<>();
        this.technicianRotation = new List<>();
        this.imagingAppointments = new List<>();
        this.currentTechnicianIndex = 0;
    }

    /**
     * Constructor to create a scheduler with a list of appointments, providers, and a list of technicians.
     *
     * @param appointments the list of appointments.
     * @param providers the list of providers.
     * @param technicianRotation the list of technicians.
     * @param medicalRecord the list of patients.
     * @param imagingAppointments the list of imaging appointments.
     * @param currentTechnicianIndex the index of the current technician.
     */
    public ClinicManager(List<Appointment> appointments, List<Provider> providers, List<Technician> technicianRotation, List<Patient> medicalRecord, List<Imaging> imagingAppointments, int currentTechnicianIndex) {
        this.appointments = appointments;
        this.providers = providers;
        this.medicalRecord = medicalRecord;
        this.technicianRotation = technicianRotation;
        this.imagingAppointments = imagingAppointments;
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
        this.medicalRecord = clinicManager.medicalRecord;
        this.technicianRotation = clinicManager.technicianRotation;
        this.imagingAppointments = clinicManager.imagingAppointments;
        this.currentTechnicianIndex = clinicManager.currentTechnicianIndex;
    }

    // Private helper method to check for existing appointment
    private boolean checkExistingAppointment(Date date, Timeslot timeslot, Profile profile) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProfile = appointment.getPatient().getProfile().equals(profile);
            boolean sameDate = appointment.getDate().equals(date);
            boolean sameTimeslot = appointment.getTimeslot().equals(timeslot);
            if (sameProfile && sameDate && sameTimeslot) {
                System.out.println(appointment.getPatient().getProfile().toString() + " has an existing appointment at the same time slot.");
                return true;
            }
        }
        return false;
    }

    // Private helper method to handle appointment date parsing and validation
    private Date handleAppointmentDate(String dateStr) {
        String[] dateSections = dateStr.split("/");
        if (dateSections.length != 3) {
            System.out.println("Appointment date: " + dateStr + " is not a valid calendar date.");
            return null;
        }
        int monthInt = Integer.parseInt(dateSections[0]);
        int dayInt = Integer.parseInt(dateSections[1]);
        int yearInt = Integer.parseInt(dateSections[2]);

        Date appointmentDate = new Date(yearInt, monthInt, dayInt);
        if (!appointmentDate.isValid()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is not a valid calendar date.");
            return null;
        }
        if (appointmentDate.isToday() || appointmentDate.isDayBeforeToday()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is today or a date before today.");
            return null;
        }
        if (appointmentDate.isWeekend()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is Saturday or Sunday.");
            return null;
        }
        if (appointmentDate.isNotWithinSixMonthsFromToday()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is not within six months.");
            return null;
        }
        return appointmentDate;
    }

    // Private helper method to handle timeslot parsing and validation
    private Timeslot handleTimeslot(int slotNum) {
        if (slotNum < 1 || slotNum > 12) {
            System.out.println(slotNum + " is not a valid time slot.");
            return null;
        }
        Timeslot timeslot = new Timeslot(slotNum);
        return timeslot;
    }

    // Private helper method to handle patient profile creation and validation
    private Profile handlePatientProfile(String firstName, String lastName, String dobStr) {
        String[] dobSections = dobStr.split("/");
        if (dobSections.length != 3) {
            System.out.println("Patient DOB: " + dobStr + " is not a valid calendar date.");
            return null;
        }
        int monthInt = Integer.parseInt(dobSections[0]);
        int dayInt = Integer.parseInt(dobSections[1]);
        int yearInt = Integer.parseInt(dobSections[2]);

        Date dob = new Date(yearInt, monthInt, dayInt);
        if (!dob.isValid()) {
            System.out.println("Patient DOB: " + dobStr + " is not a valid calendar date.");
            return null;
        }
        if (!dob.realDOB()) {
            System.out.println("Patient DOB: " + dobStr + " is today or a date after today.");
            return null;
        }
        return new Profile(firstName, lastName, dob);
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    private Radiology handleImagingService(String requestedImagingService) {
        Radiology imagingService;
        try {
            imagingService = Radiology.valueOf(requestedImagingService); // Use Radiology enum to identify the imaging service
        } catch (IllegalArgumentException e) {
            System.out.println(requestedImagingService.toLowerCase() + " - imaging service is not provided.");
            return null;
        }
        return imagingService;
    }

    /**
     * Finds an existing patient in the medical record or creates a new one and adds it to the medical record.
     *
     * @param profile the profile of the patient to find or create.
     * @return the existing or new patient.
     */
    private Patient findOrCreatePatient(Profile profile) {
        for (int i = 0; i < medicalRecord.size(); i++) {
            Patient existingPatient = medicalRecord.get(i);
            if (existingPatient.getProfile().equals(profile)) {
                return existingPatient;
            }
        }
        Patient newPatient = new Patient(profile, null);
        medicalRecord.add(newPatient);
        return newPatient;
    }

    private void reverseTechnicianRotation() {
        int n = technicianRotation.size();
        for (int i = 0; i < n / 2; i++) {
            Technician temp = technicianRotation.get(i);
            technicianRotation.set(i, technicianRotation.get(n - 1 - i));
            technicianRotation.set(n - 1 - i, temp);
        }
    }


    /**
     * Runs the user interface.
     */
    public void run() {
        loadProviders();
        displayProviders();
        displayTechnicianRotation();
        System.out.println();
        System.out.println("Clinic Manager is running...");
        Scanner scan = new Scanner(System.in);
        System.out.println();
        while (true) {
            String command = scan.nextLine();
            if (command.isEmpty()) {
                continue; // ignore empty lines
            }
            String comm = command.split(",")[0].trim();
            if (!comm.equals(comm.toUpperCase())) {
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
                    printByAppointment();
                    break;
                case "PP":
                    printByPatient();
                    break;
                case "PL":
                    printByLocation();
                    break;
                case "PS":
                    printBillingStatement();
                    break;
                case "PO":
                    printOfficeAppointments();
                    break;
                case "PI":
                    printImagingAppointments();
                    break;
                case "PC":
                    printExpectedCredit();
                    break;
                case "Q":
                    System.out.println("Clinic Manager terminated.");
                    return;
                default:
                    System.out.println("Invalid command!");
            }
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
                technicianRotation.add(technician);
            }
        }
        Sort.providers(providers, 'c');
        reverseTechnicianRotation();
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
        Technician technician = technicianRotation.get(currentTechnicianIndex);
        currentTechnicianIndex = (currentTechnicianIndex + 1) % technicianRotation.size(); // Increment the index and wrap around
        return technician;
    }

    /**
     * Display the rotation list of technicians for scheduling imaging appointments.
     */
    public void displayTechnicianRotation() {
        System.out.println("Rotation list for the technicians.");
        for (int i = 0; i < technicianRotationLength; i++) {
            System.out.print(technicianRotation.get(i).getProfile().get_fname() + " " +
                    technicianRotation.get(i).getProfile().get_lname() +
                    " (" + technicianRotation.get(i).getLocation().name() + ")");
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
    public void scheduleDoctorAppointment(String command) {
        String[] tokens = command.split(",");
        if(tokens.length < 7){
            System.out.println("Missing data tokens.");
            return;
        }
        Date appointmentDate = handleAppointmentDate(tokens[1].trim());
        if (appointmentDate == null) return;
        int slotNum;
        try {
            slotNum = Integer.parseInt(tokens[2].trim());
        } catch (NumberFormatException e) {
            System.out.println(tokens[2].trim() + " is not a valid time slot.");
            return;
        }
        Timeslot timeslot = handleTimeslot(slotNum);
        if (timeslot == null) return;
        Profile patientProfile = handlePatientProfile(tokens[3].trim(), tokens[4].trim(), tokens[5].trim());
        if (patientProfile == null) return;
        if (checkExistingAppointment(appointmentDate, timeslot, patientProfile)) return;
        String npi = tokens[6].trim();
        Doctor doctor = findDoctorByNPI(npi);
        if (doctor == null || !isNumeric(npi)) {
            System.out.println(npi + " - provider doesn't exist.");
            return;
        }
        Appointment newAppointment = new Appointment(appointmentDate, timeslot, new Patient(patientProfile, null), doctor);
        appointments.add(newAppointment);
        System.out.println(newAppointment.toString() + " booked.");
        Visit newVisit = new Visit(newAppointment, null);
        Patient patient = findOrCreatePatient(patientProfile);
        patient.setVisit(newVisit); // Document the visit in the patient's medical record
    }

    /**
     * Schedules an imaging appointment with a technician.
     * TODO: make this shorter with helper methods
     * @param command the schedule command.
     */
    public void scheduleTechnicianAppointment(String command){
        String[] tokens = command.split(",");
        if(tokens.length < 7){
            System.out.println("Missing data tokens.");
            return;
        }
        Date appointmentDate = handleAppointmentDate(tokens[1].trim());
        if (appointmentDate == null) return;
        int slotNum;
        try {
            slotNum = Integer.parseInt(tokens[2].trim());
        } catch (NumberFormatException e) {
            System.out.println(tokens[2].trim() + " is not a valid time slot.");
            return;
        }
        Timeslot timeslot = handleTimeslot(slotNum);
        if (timeslot == null) return;
        Profile patientProfile = handlePatientProfile(tokens[3].trim(), tokens[4].trim(), tokens[5].trim());
        if (patientProfile == null) return;
        if (checkExistingAppointment(appointmentDate, timeslot, patientProfile)) return;
        String imagingServiceString = tokens[6].trim().toUpperCase();
        Radiology imagingService;
        try {
            imagingService = Radiology.valueOf(imagingServiceString); // Use Radiology enum to identify the imaging service
        } catch (IllegalArgumentException e) {
            System.out.println(imagingServiceString.toLowerCase() + " - imaging service is not provided.");
            return;
        }
        boolean technicianFound = false;
        for (int i = 0; i < technicianRotation.size(); i++) {
            Technician currentTechnician = getNextTechnician();
            // Check if the technician is available at the given timeslot
            boolean isAvailable = true; // Assume availability until proven otherwise
            for (int j = 0; j < appointments.size(); j++) {
                Appointment existingAppointment = appointments.get(j);
                boolean sameDate = existingAppointment.getDate().equals(appointmentDate);
                boolean sameTimeslot = existingAppointment.getTimeslot().equals(timeslot);
                boolean sameTechnician = existingAppointment.getProvider().equals(currentTechnician);
                // If there's already an appointment for the same date, timeslot, and technician, they are not available
                if (sameDate && sameTimeslot && sameTechnician) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                Location technicianLocation = currentTechnician.getLocation();
                boolean isRoomAvailable = true;
                for (int j = 0; j < appointments.size(); j++) {
                    if (appointments.get(j) instanceof Imaging) {
                        Imaging existingImagingAppointment = (Imaging) appointments.get(j);
                        boolean sameLocation = existingImagingAppointment.getProvider().equals(currentTechnician);
                        boolean sameDate = existingImagingAppointment.date.equals(appointmentDate);
                        boolean sameTimeslot = existingImagingAppointment.timeslot.equals(timeslot);
                        boolean sameRoom = existingImagingAppointment.getRadiologyRoom().equals(imagingService);
                        if (sameLocation && sameDate && sameTimeslot && sameRoom) {
                            isRoomAvailable = false;
                            break;
                        }
                    }
                }
                if (isRoomAvailable) {
                    Imaging newImagingAppointment = new Imaging(appointmentDate, timeslot, new Patient(patientProfile, null), currentTechnician, imagingService);
                    appointments.add(newImagingAppointment);
                    System.out.println(newImagingAppointment.toString() + " booked.");
                    technicianFound = true;
                    Visit newVisit = new Visit(newImagingAppointment, null);
                    Patient patient = findOrCreatePatient(patientProfile);
                    patient.setVisit(newVisit); // Document the visit in the patient's medical record
                    break;
                }

            }

        }
        if (!technicianFound) {
            System.out.println("Cannot find an available technician at all locations for " + imagingServiceString + " at slot " + slotNum + ".");
        }

    }

    /**
     * Cancel an appointment.
     *
     * @param command the cancel command.
     */
    public void cancelAppointment(String command) {
        String[] tokens = command.split(",");
        if (tokens.length < 6) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date appointmentDate = new Date(tokens[1].trim());
        int slotNum = Integer.parseInt(tokens[2].trim());
        Timeslot timeslot = new Timeslot();
        timeslot.assignSlot(slotNum);
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);
        boolean appointmentFound = false;
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProfile = appointment.getPatient().getProfile().equals(patientProfile);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().equals(timeslot);
            if (sameProfile && sameDate && sameTimeslot) {
                appointments.remove(appointment);
                System.out.println(appointment.getDate().toString() + " " + appointment.getTimeslot().toString() + " " + appointment.getPatient().toString() + " has been canceled.");
                appointmentFound = true;
                break;
            }
        }
        if (!appointmentFound) {
            System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patientProfile.toString() + " does not exist.");
        }
    }

    /**
     * Reschedule an appointment to a different timeslot on the same day with the same provider.
     * TODO: make shorter.
     *
     * @param command the reschedule command.
     */
    public void rescheduleAppointment(String command) {
        String[] tokens = command.split(",");
        if (tokens.length < 7) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date appointmentDate = new Date(tokens[1].trim());
        int ogSlotNum = Integer.parseInt(tokens[2].trim());
        Timeslot originalTimeslot = handleTimeslot(ogSlotNum);
        if (originalTimeslot == null) return;
        Profile patientProfile = handlePatientProfile(tokens[3].trim(), tokens[4].trim(), tokens[5].trim());
        if (patientProfile == null) return;
        Appointment originalAppointment = null;
        Provider provider = null;
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProfile = appointment.getPatient().getProfile().equals(patientProfile);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().equals(originalTimeslot);
            if (sameProfile && sameDate && sameTimeslot) {
                originalAppointment = appointment;
                provider = (Provider) appointment.getProvider();
                break; // Appointment found
            }
        }
        if (originalAppointment == null) {
            System.out.println(appointmentDate.toString() + " " + originalTimeslot.toString() + " " + patientProfile.toString() + " does not exist.");
            return;
        }
        int newSlotNum = Integer.parseInt(tokens[6].trim());
        Timeslot rescheduledTimeslot = handleTimeslot(newSlotNum);
        if (rescheduledTimeslot == null) return;
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            boolean sameProvider = appointment.getProvider().equals(provider);
            boolean sameDate = appointment.getDate().equals(appointmentDate);
            boolean sameTimeslot = appointment.getTimeslot().equals(rescheduledTimeslot);
            if (sameProvider && sameDate && sameTimeslot) {
                System.out.println(provider.toString() + " is not available at slot " + newSlotNum + ".");
                return;
            }
        }
        originalAppointment.setTimeslot(rescheduledTimeslot);
        System.out.println("Rescheduled to " + originalAppointment.toString());
    }

    /**
     * Sorted by appointment date, then appointment timeslot, then provider's name.
     */
    public void printByAppointment(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        Sort.appointments(appointments, 'a');
        System.out.println();
        System.out.println("** list of appointments, ordered by date/time/provider. **");
        for(int i = 0; i < appointments.size(); i++){
            System.out.println(appointments.get(i).toString());
        }
        System.out.println("** end of list **");
    }

    /**
     * Sorted by patient profile (last name, first name, dob), then appointment date, then appointment timeslot.
     */
    public void printByPatient(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        Sort.appointments(appointments, 'p');
        System.out.println();
        System.out.println("** List of appointments, ordered by patient/date/time **");
        for(int i = 0; i < appointments.size(); i++){
            System.out.println(appointments.get(i).toString());
        }
        System.out.println("** end of list **");
    }

    /**
     * Print appointments sorted by county, name, then appointment date and time.
     */
    public void printByLocation(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        System.out.println();
        System.out.println("** List of appointments, ordered by county/date/time. **");
        Sort.providers(providers, 'l');
        Sort.appointments(appointments, 'd');
        for(int i = 0; i < providers.size(); i++){
            for(int j = 0; j < appointments.size(); j++){
                if(appointments.get(j).getProvider().equals(providers.get(i))){
                    System.out.println(appointments.get(j).toString());
                }
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the list of office appointments, sorted by the county, then date, then time.
     */
    public void printOfficeAppointments(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        Sort.providers(providers, 'l');
        Sort.appointments(appointments, 'd');
        System.out.println();
        System.out.println("** List of office appointments ordered by county/date/time. **");
        for(int i = 0; i < appointments.size(); i++){
            if(appointments.get(i).getProvider() instanceof Doctor){
                System.out.println(appointments.get(i).toString());
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the list of imaging appointments, sorted by the county name, then date and time.
     */
    public void printImagingAppointments(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        Sort.providers(providers, 'l');
        Sort.appointments(appointments, 'd');
        System.out.println();
        System.out.println("** List of radiology appointments ordered by county/date/time. **");
        for(int i = 0; i < appointments.size(); i++){
            if(appointments.get(i) instanceof Imaging){
                System.out.println(appointments.get(i).toString());
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the expected credit amounts for the providers for seeing patients, sorted by provider profile.
     */
    public void printExpectedCredit(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        System.out.println();
        System.out.println("** Credit amount ordered by provider");
        Sort.providers(providers, 'c');
        int credit = 0;
        for(int i = 0; i < providers.size(); i++){
            for(int j = 0; j < appointments.size(); j++){
                if(appointments.get(j).getProvider().equals(providers.get(i))){
                    credit += providers.get(i).rate();
                }
            }
            System.out.println("("+ (i + 1) + ")" + providers.get(i).getProfile().toString() + " " + "[credit amount: $" + credit + ".00]");
        }
        System.out.println("** end of list **");
    }

    /**
     * Display billing statements for all patients.
     */
    public void printBillingStatement(){
        if(appointments.isEmpty()){
            System.out.println("The schedule calendar is empty.");
            return;
        }
        Sort.medicalRecord(medicalRecord);
        System.out.println();
        System.out.println("** Billing statement ordered by patient. **");
        for(int i = 0; i < medicalRecord.size(); i++){
            System.out.println("("+ (i + 1) + ")" + medicalRecord.get(i).printCharge());
        }
        System.out.println("** end of list **");
        emptyAppointments();
    }

    public void emptyAppointments() {
        for (int i = appointments.size() - 1; i >= 0; i--) {
            appointments.remove(appointments.get(i));
        }
    }



}



