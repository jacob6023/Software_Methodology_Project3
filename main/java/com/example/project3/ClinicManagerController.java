package com.example.project3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import com.example.project3.clinic.*;
import com.example.project3.util.*;
import org.jetbrains.annotations.NotNull;

/**
 * Controller class for handling GUI events and interactions.
 */
public class ClinicManagerController {

    // FXML UI Components
    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private RadioButton officeVisitRadioButton;

    @FXML
    private RadioButton imagingServiceRadioButton;

    @FXML
    private TextField npiField;

    @FXML
    private ComboBox<String> timeSlotComboBox;

    @FXML
    private ComboBox<String> providerComboBox;

    @FXML
    private ComboBox<Radiology> imagingServiceComboBox;

    @FXML
    private ComboBox<String> npiComboBox;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button rescheduleButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button loadProviderButton;

    @FXML
    private Button printByAppointmentButton;

    @FXML
    private Button printByPatientButton;

    @FXML
    private Button printByLocationButton;

    @FXML
    private Button printOfficeAppointmentsButton;

    @FXML
    private Button printImagingAppointmentsButton;

    @FXML
    private Button printExpectedCreditButton;

    @FXML
    private Button printBillingStatementButton;

    // ToggleGroup for RadioButtons
    private ToggleGroup serviceTypeGroup;

    // ObservableLists for ComboBoxes
    private ObservableList<String> timeSlots;

    private ObservableList<String> npis;

    // Data structures
    private List<Appointment> appointments;
    private List<Provider> providers;
    private List<Patient> medicalRecord;
    private List<Technician> technicianRotation;
    private List<Imaging> imagingAppointments;

    private int currentTechnicianIndex;
    private static final int technicianRotationLength = 6;

    private boolean checkExistingAppointment(Appointment appointment){
        for(Appointment app : appointments){
            if(app.equals(appointment)){
                showAlert(AlertType.ERROR, appointment.getPatient().getProfile().toString() + " has an existing appointment at the same time slot.",
                        "The appointment already exists.");
                return true;
            }
        }
        return false;
    }

    private boolean findDoctorByNPI(String npi) {
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i) instanceof Doctor doctor) {
                if (doctor.getNPI().equals(npi)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if a string is numeric.
     *
     * @param str the string to check.
     * @return true if the string is numeric, false otherwise.
     */
    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Initialize data structures
        appointments = new List<>();
        providers = new List<>();
        medicalRecord = new List<>();
        technicianRotation = new List<>();
        imagingAppointments = new List<>();
        currentTechnicianIndex = 0;

        // Initialize ToggleGroup for service type
        serviceTypeGroup = new ToggleGroup();
        officeVisitRadioButton.setToggleGroup(serviceTypeGroup);
        imagingServiceRadioButton.setToggleGroup(serviceTypeGroup);

        // Populate time slots
        timeSlots = FXCollections.observableArrayList(
                "1 - 9:00 AM", "2 - 9:30 AM", "3 - 10:00 AM",
                "4 - 10:30 AM", "5 - 11:00 AM", "6 - 11:30 AM",
                "7 - 2:00 PM", "8 - 2:30 PM", "9 - 3:00 PM",
                "10 - 3:30 PM", "11 - 4:00 PM", "12 - 4:30 PM"
        );
        timeSlotComboBox.setItems(timeSlots);

        // Populate NPIs
        npis = FXCollections.observableArrayList(
                "01", "23", "11", "32", "54", "91", "39", "09", "85", "77"
        );
        npiComboBox.setItems(npis);

        imagingServiceComboBox.setItems(FXCollections.observableArrayList(Radiology.values()));

        // Disable providerComboBox until providers are loaded
        providerComboBox.setDisable(true);

        // Set default dates to today
        appointmentDatePicker.setValue(LocalDate.now());
        dobDatePicker.setValue(LocalDate.now());
    }

    /**
     * Handles the action when the Schedule button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    private void handleScheduleButtonAction(ActionEvent event) {
        try {
            // Validate and get inputs
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            LocalDate dob = dobDatePicker.getValue();
            LocalDate appointmentDate = appointmentDatePicker.getValue();
            String timeSlotStr = timeSlotComboBox.getValue();
            String providerStr = providerComboBox.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || dob == null
                    || appointmentDate == null || timeSlotStr == null
                    || providerStr == null) {
                showAlert(AlertType.ERROR, "Invalid Input",
                        "Please fill in all fields.");
                return;
            }

            if(isNumeric(firstName) || isNumeric(lastName)){
                showAlert(AlertType.ERROR, "Invalid Input",
                        "First name and last name cannot be numbers.");
                return;
            }



            // Parse date strings to custom Date class
            Date dobDate = new Date(dob.format(DateTimeFormatter.ofPattern(
                    "yyyy-M-d")));
            Date appointmentDateCustom = new Date(appointmentDate.format(
                    DateTimeFormatter.ofPattern("yyyy-M-d")));

            // Get selected time slot number
            int timeSlotNum = Integer.parseInt(timeSlotStr.split(" - ")[0]);

            // handle validity of timeslot, dob, and appointment date.
            if(!handleTimeslotValidity(timeSlotNum) || !handleDOBValidity(dobDate) || !handleAppointmentDateValidity(appointmentDateCustom)){
                return;
            }

            Timeslot timeslot = new Timeslot(timeSlotNum); // create timeslot if valid.

            // Create Profile and Patient
            Profile patientProfile = new Profile(firstName, lastName, dobDate);
            Patient patient = new Patient(patientProfile, null);

            // Determine service type
            RadioButton selectedServiceType = (RadioButton) serviceTypeGroup
                    .getSelectedToggle();
            if (selectedServiceType == null) {
                showAlert(AlertType.ERROR, "Invalid Input",
                        "Please select a service type.");
                return;
            }


            // Schedule appointment based on service type
            if (selectedServiceType == officeVisitRadioButton) {
                // Office Visit
                Doctor selectedDoctor = findDoctorByName(providerStr);
                if (selectedDoctor == null) {
                    showAlert(AlertType.ERROR, selectedDoctor.getNPI() + "- provider doesn't exist.",
                            "provider doesn't exist.");
                    return;
                }

                if(!findDoctorByNPI(npiField.getText())){
                    showAlert(AlertType.ERROR, "Doctor with NPI: " + npiField.getText() + " provider does not exist.",
                            "Doctor does not exists.");
                    return;
                }



                Appointment appointment = new Appointment(
                        appointmentDateCustom, timeslot, patient,
                        selectedDoctor);
                if(checkExistingAppointment(appointment)) return;
                appointments.add(appointment);
                showAlert(AlertType.INFORMATION, appointment.toString() + "booked.",
                        "booked.");

                if(!medicalRecord.contains(patient)){
                    medicalRecord.add(patient);
                }
                Visit visit = new Visit(appointment, null);
                patient.addVisit(visit);

            } else if (selectedServiceType == imagingServiceRadioButton) {
                // Imaging Service
                Technician selectedTechnician = findTechnicianByName(providerStr);
                if (selectedTechnician == null) {
                    showAlert(AlertType.ERROR, "Provider Not Found",
                            "Selected provider not found.");
                    return;
                }


                Radiology selectedImagingService = imagingServiceComboBox.getValue();
                if (selectedImagingService == null) {
                    showAlert(AlertType.ERROR, "Invalid Input", "Please enter an imaging service");
                    return;
                }

                Imaging imagingAppointment = new Imaging(appointmentDateCustom,
                        timeslot, patient, selectedTechnician, selectedImagingService);
                if(checkExistingAppointment(imagingAppointment)) return;
                appointments.add(imagingAppointment);
                showAlert(AlertType.INFORMATION, "Appointment Scheduled",
                        "Imaging service scheduled successfully.");

                if(!medicalRecord.contains(patient)){
                    medicalRecord.add(patient);
                }
                Visit visit = new Visit(imagingAppointment, null);
                patient.addVisit(visit);

            }

            clearFields();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error",
                    "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Cancel button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        try {
            // Validate and get inputs
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            LocalDate dob = dobDatePicker.getValue();
            LocalDate appointmentDate = appointmentDatePicker.getValue();
            String timeSlotStr = timeSlotComboBox.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || dob == null
                    || appointmentDate == null || timeSlotStr == null) {
                showAlert(AlertType.ERROR, "Invalid Input",
                        "Please fill in all fields.");
                return;
            }

            // Parse date strings to custom Date class
            Date dobDate = new Date(dob.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));
            Date appointmentDateCustom = new Date(appointmentDate.format(DateTimeFormatter.ofPattern("yyyy-M-d")));

            // Get selected time slot number
            int timeSlotNum = Integer.parseInt(timeSlotStr.split(" - ")[0]);
            Timeslot timeslot = new Timeslot(timeSlotNum);

            // Create Profile and Patient
            Profile patientProfile = new Profile(firstName, lastName, dobDate);
            Patient patient = new Patient(patientProfile, null);

            // Find and remove the appointment
            boolean appointmentFound = false;
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                boolean sameProfile = appointment.getPatient().getProfile().equals(patientProfile);
                boolean sameDate = appointment.getDate().equals(appointmentDateCustom);
                boolean sameTimeslot = appointment.getTimeslot().equals(timeslot);
                if (sameProfile && sameDate && sameTimeslot) {
                    appointments.remove(appointment);
                    showAlert(AlertType.INFORMATION, "Appointment Canceled",
                            "Appointment has been canceled successfully.");
                    appointmentFound = true;
                    break;
                }
            }
            if (!appointmentFound) {
                showAlert(AlertType.ERROR, "Appointment Not Found",
                        "No matching appointment found.");
            }

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error",
                    "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRescheduleButtonAction(ActionEvent event){
        try {
            // Validate and get inputs
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            LocalDate dob = dobDatePicker.getValue();
            LocalDate appointmentDate = appointmentDatePicker.getValue();
            String originalTimeSlotStr = timeSlotComboBox.getValue();
            String newTimeSlotStr = timeSlotComboBox.getValue(); // Assuming there's a ComboBox for new time slot

            if (firstName.isEmpty() || lastName.isEmpty() || dob == null
                    || appointmentDate == null || originalTimeSlotStr == null || newTimeSlotStr == null) {
                showAlert(AlertType.ERROR, "Invalid Input",
                        "Please fill in all fields.");
                return;
            }

            // Parse date strings to custom Date class
            Date dobDate = new Date(dob.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));
            Date appointmentDateCustom = new Date(appointmentDate.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));

            // Get selected time slot numbers
            int originalTimeSlotNum = Integer.parseInt(originalTimeSlotStr.split(" - ")[0]);
            int newTimeSlotNum = Integer.parseInt(newTimeSlotStr.split(" - ")[0]);
            Timeslot originalTimeslot = new Timeslot(originalTimeSlotNum);
            Timeslot newTimeslot = new Timeslot(newTimeSlotNum);

            // Create Profile and Patient
            Profile patientProfile = new Profile(firstName, lastName, dobDate);
            Patient patient = new Patient(patientProfile, null);

            // Find the original appointment
            Appointment originalAppointment = null;
            Provider provider = null;
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                boolean sameProfile = appointment.getPatient().getProfile().equals(patientProfile);
                boolean sameDate = appointment.getDate().equals(appointmentDateCustom);
                boolean sameTimeslot = appointment.getTimeslot().equals(originalTimeslot);
                if (sameProfile && sameDate && sameTimeslot) {
                    originalAppointment = appointment;
                    provider = (Provider) appointment.getProvider();
                    break; // Appointment found
                }
            }
            if (originalAppointment == null) {
                showAlert(AlertType.ERROR, "Appointment Not Found",
                        "No matching appointment found.");
                return;
            }

            // Check for conflicts with the new timeslot
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                boolean sameProvider = appointment.getProvider().equals(provider);
                boolean sameDate = appointment.getDate().equals(appointmentDateCustom);
                boolean sameTimeslot = appointment.getTimeslot().equals(newTimeslot);
                if (sameProvider && sameDate && sameTimeslot) {
                    showAlert(AlertType.ERROR, "Time Slot Unavailable",
                            provider.toString() + " is not available at slot " + newTimeSlotNum + ".");
                    return;
                }
            }

            // Reschedule the appointment
            originalAppointment.setTimeslot(newTimeslot);
            showAlert(AlertType.INFORMATION, "Appointment Rescheduled",
                    "Rescheduled to " + originalAppointment.toString());

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error",
                    "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Clear button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }

    /**
     * Clears all input fields and resets selections.
     */
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        dobDatePicker.setValue(LocalDate.now());
        appointmentDatePicker.setValue(LocalDate.now());
        timeSlotComboBox.getSelectionModel().clearSelection();
        providerComboBox.getSelectionModel().clearSelection();
        providerComboBox.setDisable(true);
        serviceTypeGroup.selectToggle(null);
    }

    /**
     * Handles the action when the Load Provider button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    private void handleLoadProviderButtonAction(ActionEvent event) {
        try {
            // Load providers
            loadProviders();
            ObservableList<String> providerNames = FXCollections.observableArrayList();

            // Determine which providers to load based on service type
            RadioButton selectedServiceType = (RadioButton) serviceTypeGroup
                    .getSelectedToggle();
            if (selectedServiceType == null) {
                showAlert(AlertType.ERROR, "Invalid Input",
                        "Please select a service type before loading providers.");
                return;
            }

            if (selectedServiceType == officeVisitRadioButton) {
                // Load doctors
                for (Provider provider : providers) {
                    if (provider instanceof Doctor) {
                        providerNames.add(provider.getProfile().toString());
                    }
                }
            } else if (selectedServiceType == imagingServiceRadioButton) {
                // Load technicians
                for (Provider provider : providers) {
                    if (provider instanceof Technician) {
                        providerNames.add(provider.getProfile().toString());
                    }
                }
            }
            /**
             * TODO: see if you should do technicianRotation here.
             */

            providerComboBox.setItems(providerNames);
            providerComboBox.setDisable(false);

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error",
                    "An error occurred while loading providers.");
            e.printStackTrace();
        }
    }

    @FXML
    private void printByAppointment() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.appointments(appointments, 'a');
        StringBuilder sb = new StringBuilder();
        sb.append("** list of appointments, ordered by date/time/provider. **\n");
        for (Appointment appointment : appointments) {
            sb.append(appointment.toString()).append("\n");
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Appointments by Date", sb.toString());
    }

    @FXML
    private void printByPatient() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.appointments(appointments, 'p');
        StringBuilder sb = new StringBuilder();
        sb.append("** List of appointments, ordered by patient/date/time **\n");
        for (Appointment appointment : appointments) {
            sb.append(appointment.toString()).append("\n");
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Appointments by Patient", sb.toString());
    }

    @FXML
    private void printByLocation() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.providers(providers, 'l');
        Sort.appointments(appointments, 'c');
        StringBuilder sb = new StringBuilder();
        sb.append("** List of appointments, ordered by county/date/time. **\n");
        for (Appointment appointment : appointments) {
            sb.append(appointment.toString()).append("\n");
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Appointments by Location", sb.toString());
    }

    @FXML
    private void printOfficeAppointments() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.providers(providers, 'l');
        Sort.appointments(appointments, 'c');
        StringBuilder sb = new StringBuilder();
        sb.append("** List of office appointments ordered by county/date/time. **\n");
        for (Appointment appointment : appointments) {
            if (appointment.getProvider() instanceof Doctor) {
                sb.append(appointment.toString()).append("\n");
            }
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Office Appointments", sb.toString());
    }

    @FXML
    private void printImagingAppointments() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.appointments(appointments, 'i');
        StringBuilder sb = new StringBuilder();
        sb.append("** List of radiology appointments ordered by county/date/time. **\n");
        for (Appointment appointment : appointments) {
            if (appointment instanceof Imaging) {
                sb.append(appointment.toString()).append("\n");
            }
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Imaging Appointments", sb.toString());
    }

    @FXML
    private void printExpectedCredit() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.providers(providers, 'c');
        StringBuilder sb = new StringBuilder();
        sb.append("** Credit amount ordered by provider **\n");
        for (Provider provider : providers) {
            int credit = 0;
            for (Appointment appointment : appointments) {
                if (appointment.getProvider().equals(provider)) {
                    credit += provider.rate();
                }
            }
            sb.append(provider.getProfile().toString()).append(" [credit amount: $").append(credit).append(".00]\n");
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Expected Credit", sb.toString());
    }

    /**
     * TODO: confirm if need to empty appointments after this is called.
     */
    @FXML
    private void printBillingStatement() {
        if (appointments.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Information", "The schedule calendar is empty.");
            return;
        }
        Sort.medicalRecord(medicalRecord);
        StringBuilder sb = new StringBuilder();
        sb.append("** Billing statement ordered by patient. **\n");
        for (Patient patient : medicalRecord) {
            int bill = patient.charge();
            sb.append(patient.getProfile().toString()).append(" [due: $").append(bill).append(".00]\n");
        }
        sb.append("** end of list **");
        showAlert(AlertType.INFORMATION, "Billing Statement", sb.toString());
       // emptyAppointments();
    }

    /**
     * Load the list of providers from the text file “providers.txt”.
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
     * Helper method to handle the validity of the date of birth.
     *
     * @param dobDate
     */
    private boolean handleDOBValidity(Date dobDate){
        String[] dateSections = dobDate.toString().split("-");
        if(dateSections.length != 3){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid date of birth.");
            return false;
        }
        String day = dateSections[0];
        String month = dateSections[1];
        String year = dateSections[2];

        if(!isNumeric(day) || !isNumeric(month) || !isNumeric(year)){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid date of birth.");
            return false;
        }

        if(day.length() > 2 ||  month.length() > 2){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid date of birth.");
            return false;
        }

        if(year.length() != 4){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid date of birth.");
            return false;
        }

        if(!dobDate.isValid()){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid date of birth.");
            return false;
        }
        if(!dobDate.realDOB()){
            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is today or a date after today.",
                    "Please enter a valid date of birth.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to handle the validity of the appointment date.
     *
     * @param appointmentDate
     */
    private boolean handleAppointmentDateValidity(@NotNull Date appointmentDate){
        String[] dateSections = appointmentDate.toString().split("-");
        if(dateSections.length != 3){
            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid appointment date.");
            return false;
        }

        String day = dateSections[0];
        String month = dateSections[1];
        String year = dateSections[2];

        if(!isNumeric(day) || !isNumeric(month) || !isNumeric(year)){
            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid appointment date.");
            return false;
        }

        if(day.length() > 2 ||  month.length() > 2){
            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid appointment date.");
            return false;
        }

        if(year.length() != 4){
            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid appointment date.");
            return false;
        }

        if(!appointmentDate.isValid()){
            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
                    "Please enter a valid appointment date.");
            return false;
        }
        if (appointmentDate.isToday() || appointmentDate.isDayBeforeToday()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is today or a date before today.");
            return false;
        }
        if (appointmentDate.isWeekend()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is Saturday or Sunday.");
            return false;
        }
        if (appointmentDate.isNotWithinSixMonthsFromToday()) {
            System.out.println("Appointment date: " + appointmentDate.toString() + " is not within six months.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to handle the validity of the timeslot.
     *
     * @param timeSlotNum
     */
    private boolean handleTimeslotValidity(int timeSlotNum){
        if(timeSlotNum < 1 || timeSlotNum > 12){
            showAlert(AlertType.ERROR, timeSlotNum + " is not a valid time slot.",
                    "Please select a valid time slot.");
            return false;
        }
        return true;
    }

    /**
     * Finds a doctor by their name.
     *
     * @param name The name of the doctor.
     * @return The Doctor object, or null if not found.
     */
    private Doctor findDoctorByName(String name) {
        for (Provider provider : providers) {
            if (provider instanceof Doctor) {
                if (provider.getProfile().toString().equals(name)) {
                    return (Doctor) provider;
                }
            }
        }
        return null;
    }

    /**
     * Finds a technician by their name.
     *
     * @param name The name of the technician.
     * @return The Technician object, or null if not found.
     */
    private Technician findTechnicianByName(String name) {
        for (Provider provider : providers) {
            if (provider instanceof Technician) {
                if (provider.getProfile().toString().equals(name)) {
                    return (Technician) provider;
                }
            }
        }
        return null;
    }

    /**
     * Helper Method. Finds an existing patient in the medical record or creates a new one and adds it to the medical record.
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

    /**
     * Helper Method. Reverses the order of the technician rotation list from file.
     */
    private void reverseTechnicianRotation() {
        int n = technicianRotation.size();
        for (int i = 0; i < n / 2; i++) {
            Technician temp = technicianRotation.get(i);
            technicianRotation.set(i, technicianRotation.get(n - 1 - i));
            technicianRotation.set(n - 1 - i, temp);
        }
    }

    /**
     * Displays an alert dialog with the specified information.
     *
     * @param alertType The type of alert.
     * @param title     The title of the alert dialog.
     * @param message   The content message of the alert dialog.
     */
    private void showAlert(AlertType alertType, String title,
                           String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}











//package com.example.project3;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Alert.AlertType;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import com.example.project3.clinic.*;
//import com.example.project3.util.*;
//
///**
// * Controller class for handling GUI events and interactions.
// *
// * @author Jacob Jeong
// */
//public class ClinicManagerController {
//
//    // FXML UI Components
//    @FXML
//    private DatePicker appointmentDatePicker;
//
//    @FXML
//    private TextField firstNameField;
//
//    @FXML
//    private TextField lastNameField;
//
//    @FXML
//    private DatePicker dobDatePicker;
//
//    @FXML
//    private RadioButton officeVisitRadioButton;
//
//    @FXML
//    private RadioButton imagingServiceRadioButton;
//
//    @FXML
//    private ComboBox<String> timeSlotComboBox;
//
//    @FXML
//    private ComboBox<String> providerComboBox;
//
//    @FXML
//    private Button scheduleButton;
//
//    @FXML
//    private Button cancelButton;
//
//    @FXML
//    private Button clearButton;
//
//    @FXML
//    private Button loadProviderButton;
//
//    // ToggleGroup for RadioButtons
//    private ToggleGroup serviceTypeGroup;
//
//    // ClinicManager instance
//    private ClinicManager clinicManager;
//
//    // ObservableLists for ComboBoxes
//    private ObservableList<String> timeSlots;
//
//    //private List<Provider> providers;
//
//    /**
//     * The list of appointments the com.example.cs213_project3.clinic has.
//     */
//    private List<Appointment> appointments;
//
//    /**
//     * The list of providers the com.example.cs213_project3.clinic has.
//     */
//    private List<Provider> providers;
//
//    /**
//     * The list of patients the com.example.cs213_project3.clinic has.
//     */
//    private List<Patient> medicalRecord;
//
//    /**
//     * The list of technicians the com.example.cs213_project3.clinic has.
//     */
//    private List<Technician> technicianRotation;
//
//    /**
//     * The list of imaging appointments the com.example.cs213_project3.clinic has.
//     */
//    private List<Imaging> imagingAppointments;
//
//    /**
//     * The index of the current technician.
//     */
//    private int currentTechnicianIndex;
//
//    /**
//     * The length of the technician rotation list.
//     */
//    private static final int technicianRotationLength = 6;
//
//    /**
//     * Getters
//     * @return the list of appointments.
//     */
//    public List<Appointment> getAppointments() {
//        return appointments;
//    }
//
//    /**
//     * Get the list of providers.
//     *
//     * @return the list of providers.
//     */
//    public List<Provider> getProviders() {
//        return providers;
//    }
//
//    /**
//     * Get the list of patients.
//     *
//     * @return the list of patients.
//     */
//    public List<Patient> getMedicalRecord(){
//        return medicalRecord;
//    }
//
//    /**
//     * Get the list of technicians.
//     *
//     * @return the list of technicians.
//     */
//    public List<Technician> getTechnicianRotation() {
//        return technicianRotation;
//    }
//
//    /**
//     * Get the list of imaging appointments.
//     *
//     * @return the list of imaging appointments.
//     */
//    public List<Imaging> getImagingAppointments(){
//        return imagingAppointments;
//    }
//
//    /**
//     * Get the index of the current technician.
//     *
//     * @return the index of the current technician.
//     */
//    public int getCurrentTechnicianIndex(){
//        return currentTechnicianIndex;
//    }
//
//    /**
//     * Set the list of appointments.
//     *
//     * @param appointments the list of appointments to set.
//     */
//    public void setAppointments(List<Appointment> appointments) {
//        this.appointments = appointments;
//    }
//
//    /**
//     * Set the list of providers.
//     *
//     * @param providers the list of providers to set.
//     */
//    public void setProviders(List<Provider> providers) {
//        this.providers = providers;
//
//    }
//
//    /**
//     * Set the list of patients.
//     *
//     * @param patients the list of patients to set.
//     */
//    public void setMedicalRecord(List<Patient> patients){
//        this.medicalRecord = medicalRecord;
//    }
//
//    /**
//     * Set the list of technicians.
//     *
//     * @param technicianRotation the list of technicians to set.
//     */
//    public void setTechnicianRotation(List<Technician> technicianRotation) {
//        this.technicianRotation = technicianRotation;
//
//    }
//
//    /**
//     * Set the list of imaging appointments.
//     *
//     * @param imagingAppointments the list of imaging appointments to set.
//     */
//    public void setImagingAppointments(List<Imaging> imagingAppointments){
//        this.imagingAppointments = imagingAppointments;
//    }
//
//    /**
//     * Set the index of the current technician.
//     *
//     * @param currentTechnicianIndex the index of the current technician.
//     */
//    public void setCurrentTechnicianIndex(int currentTechnicianIndex){
//        this.currentTechnicianIndex = currentTechnicianIndex;
//    }
//
//    /**
//     * Default Constructor to create scheduler with default values.
//     * Uses Appointments and MedicalRecords default constructors.
//     */
//    public ClinicManagerController() {
//        this.appointments = new List<>();
//        this.providers = new List<>();
//        this.medicalRecord = new List<>();
//        this.technicianRotation = new List<>();
//        this.imagingAppointments = new List<>();
//        this.currentTechnicianIndex = 0;
//    }
//
//    /**
//     * Constructor to create a scheduler with a list of appointments, providers, and a list of technicians.
//     *
//     * @param appointments the list of appointments.
//     * @param providers the list of providers.
//     * @param technicianRotation the list of technicians.
//     * @param medicalRecord the list of patients.
//     * @param imagingAppointments the list of imaging appointments.
//     * @param currentTechnicianIndex the index of the current technician.
//     */
//    public ClinicManagerController(List<Appointment> appointments, List<Provider> providers, List<Technician> technicianRotation, List<Patient> medicalRecord, List<Imaging> imagingAppointments, int currentTechnicianIndex) {
//        this.appointments = appointments;
//        this.providers = providers;
//        this.medicalRecord = medicalRecord;
//        this.technicianRotation = technicianRotation;
//        this.imagingAppointments = imagingAppointments;
//        this.currentTechnicianIndex = currentTechnicianIndex;
//    }
//
//    /**
//     * Copy Constructor to create a new scheduler with an existing scheduler.
//     *
//     * @param clinicManager the ClinicManager whose data is being copied into new scheduler.
//     */
//    public ClinicManagerController(ClinicManagerController clinicManager){
//        this.appointments = clinicManager.appointments;
//        this.providers = clinicManager.providers;
//        this.medicalRecord = clinicManager.medicalRecord;
//        this.technicianRotation = clinicManager.technicianRotation;
//        this.imagingAppointments = clinicManager.imagingAppointments;
//        this.currentTechnicianIndex = clinicManager.currentTechnicianIndex;
//    }
//
//    /**
//     * Helper method to handle the validity of the date of birth.
//     *
//     * @param dobDate
//     */
//    private boolean handleDOBValidity(Date dobDate){
//        String[] dateSections = dobDate.toString().split("-");
//        if(dateSections.length != 3){
//            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid date of birth.");
//            return false;
//        }
//        String day = dateSections[0];
//        String month = dateSections[1];
//        String year = dateSections[2];
//        if(day.length() > 2 ||  month.length() > 2){
//            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid date of birth.");
//            return false;
//        }
//
//        if(year.length() != 4){
//            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid date of birth.");
//            return false;
//        }
//
//        if(!dobDate.isValid()){
//            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid date of birth.");
//            return false;
//        }
//        if(!dobDate.realDOB()){
//            showAlert(AlertType.ERROR, "Patient DOB: " + dobDate.toString() + " is today or a date after today.",
//                    "Please enter a valid date of birth.");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Helper method to handle the validity of the appointment date.
//     *
//     * @param appointmentDate
//     */
//    private boolean handleAppointmentDateValidity(Date appointmentDate){
//        String[] dateSections = appointmentDate.toString().split("-");
//        if(dateSections.length != 3){
//            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid appointment date.");
//            return false;
//        }
//
//        String day = dateSections[0];
//        String month = dateSections[1];
//        String year = dateSections[2];
//
//        if(day.length() > 2 ||  month.length() > 2){
//            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid appointment date.");
//            return false;
//        }
//
//        if(year.length() != 4){
//            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid appointment date.");
//            return false;
//        }
//
//        if(!appointmentDate.isValid()){
//            showAlert(AlertType.ERROR, "Appointment Date: " + appointmentDate.toString() + " is not a valid calendar date.",
//                    "Please enter a valid appointment date.");
//            return false;
//        }
//        if (appointmentDate.isToday() || appointmentDate.isDayBeforeToday()) {
//            System.out.println("Appointment date: " + appointmentDate.toString() + " is today or a date before today.");
//            return false;
//        }
//        if (appointmentDate.isWeekend()) {
//            System.out.println("Appointment date: " + appointmentDate.toString() + " is Saturday or Sunday.");
//            return false;
//        }
//        if (appointmentDate.isNotWithinSixMonthsFromToday()) {
//            System.out.println("Appointment date: " + appointmentDate.toString() + " is not within six months.");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Helper method to handle the validity of the timeslot.
//     *
//     * @param timeSlotNum
//     */
//    private boolean handleTimeslotValidity(int timeSlotNum){
//        if(timeSlotNum < 1 || timeSlotNum > 12){
//            showAlert(AlertType.ERROR, timeSlotNum + " is not a valid time slot.",
//                    "Please select a valid time slot.");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Initializes the controller class. This method is automatically called
//     * after the FXML file has been loaded.
//     */
//    @FXML
//    public void initialize() {
//        clinicManager = new ClinicManager();
//
//        // Initialize ToggleGroup for service type
//        serviceTypeGroup = new ToggleGroup();
//        officeVisitRadioButton.setToggleGroup(serviceTypeGroup);
//        imagingServiceRadioButton.setToggleGroup(serviceTypeGroup);
//
//        // Populate time slots
//        timeSlots = FXCollections.observableArrayList(
//                "1 - 9:00 AM", "2 - 9:30 AM", "3 - 10:00 AM",
//                "4 - 10:30 AM", "5 - 11:00 AM", "6 - 11:30 AM",
//                "7 - 2:00 PM", "8 - 2:30 PM", "9 - 3:00 PM",
//                "10 - 3:30 PM", "11 - 4:00 PM", "12 - 4:30 PM"
//        );
//        timeSlotComboBox.setItems(timeSlots);
//
//        // Disable providerComboBox until providers are loaded
//        providerComboBox.setDisable(true);
//
//        // Set default dates to today
//        appointmentDatePicker.setValue(LocalDate.now());
//        dobDatePicker.setValue(LocalDate.now());
//    }
//
//    /**
//     * Handles the action when the Schedule button is clicked.
//     *
//     * @param event The action event.
//     */
//    @FXML
//    private void handleScheduleButtonAction(ActionEvent event) {
//        try {
//            // Validate and get inputs
//            String firstName = firstNameField.getText().trim();
//            String lastName = lastNameField.getText().trim();
//            LocalDate dob = dobDatePicker.getValue();
//            LocalDate appointmentDate = appointmentDatePicker.getValue();
//            String timeSlotStr = timeSlotComboBox.getValue();
//            String providerStr = providerComboBox.getValue();
//
//            if (firstName.isEmpty() || lastName.isEmpty() || dob == null
//                    || appointmentDate == null || timeSlotStr == null
//                    || providerStr == null) {
//                showAlert(AlertType.ERROR, "Invalid Input",
//                        "Please fill in all fields.");
//                return;
//            }
//
//            // Parse date strings to custom Date class
//            Date dobDate = new Date(dob.format(DateTimeFormatter.ofPattern(
//                    "yyyy-M-d")));
//            Date appointmentDateCustom = new Date(appointmentDate.format(
//                    DateTimeFormatter.ofPattern("yyyy-M-d")));
//
//            // Get selected time slot number
//            int timeSlotNum = Integer.parseInt(timeSlotStr.split(" - ")[0]);
//
//            // handle validity of timeslot, dob, and appointment date.
//            if(!handleTimeslotValidity(timeSlotNum) || !handleDOBValidity(dobDate) || !handleAppointmentDateValidity(appointmentDateCustom)){
//                return;
//            }
//
//            Timeslot timeslot = new Timeslot(timeSlotNum); // create timeslot if valid.
//
//            // Create Profile and Patient
//            Profile patientProfile = new Profile(firstName, lastName, dobDate);
//            Patient patient = new Patient(patientProfile, null);
//
//            // Determine service type
//            RadioButton selectedServiceType = (RadioButton) serviceTypeGroup
//                    .getSelectedToggle();
//            if (selectedServiceType == null) {
//                showAlert(AlertType.ERROR, "Invalid Input",
//                        "Please select a service type.");
//                return;
//            }
//
//            // Schedule appointment based on service type
//            if (selectedServiceType == officeVisitRadioButton) {
//                // Office Visit
//                Doctor selectedDoctor = findDoctorByName(providerStr);
//                if (selectedDoctor == null) {
//                    showAlert(AlertType.ERROR, "Provider Not Found",
//                            "Selected provider not found.");
//                    return;
//                }
//                Appointment appointment = new Appointment(
//                        appointmentDateCustom, timeslot, patient,
//                        selectedDoctor);
//                clinicManager.getAppointments().add(appointment);
//                showAlert(AlertType.INFORMATION, "Appointment Scheduled",
//                        "Office visit scheduled successfully.");
//            } else if (selectedServiceType == imagingServiceRadioButton) {
//                // Imaging Service
//                Technician selectedTechnician = findTechnicianByName(
//                        providerStr);
//                if (selectedTechnician == null) {
//                    showAlert(AlertType.ERROR, "Provider Not Found",
//                            "Selected provider not found.");
//                    return;
//                }
//                // For simplicity, using XRAY as the imaging service
//                Imaging imagingAppointment = new Imaging(appointmentDateCustom,
//                        timeslot, patient, selectedTechnician, Radiology.XRAY);
//                clinicManager.getAppointments().add(imagingAppointment);
//                showAlert(AlertType.INFORMATION, "Appointment Scheduled",
//                        "Imaging service scheduled successfully.");
//            }
//
//            clearFields();
//
//        } catch (Exception e) {
//            showAlert(AlertType.ERROR, "Error",
//                    "An error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Handles the action when the Cancel button is clicked.
//     *
//     * @param event The action event.
//     */
//    @FXML
//    private void handleCancelButtonAction(ActionEvent event) {
//        // Implement cancellation logic
//        showAlert(AlertType.INFORMATION, "Cancel Appointment",
//                "Feature not implemented yet.");
//    }
//
//    /**
//     * Handles the action when the Clear button is clicked.
//     *
//     * @param event The action event.
//     */
//    @FXML
//    private void handleClearButtonAction(ActionEvent event) {
//        clearFields();
//    }
//
//    /**
//     * Clears all input fields and resets selections.
//     */
//    private void clearFields() {
//        firstNameField.clear();
//        lastNameField.clear();
//        dobDatePicker.setValue(LocalDate.now());
//        appointmentDatePicker.setValue(LocalDate.now());
//        timeSlotComboBox.getSelectionModel().clearSelection();
//        providerComboBox.getSelectionModel().clearSelection();
//        providerComboBox.setDisable(true);
//        serviceTypeGroup.selectToggle(null);
//    }
//
//    /**
//     * Handles the action when the Load Provider button is clicked.
//     *
//     * @param event The action event.
//     */
//    @FXML
//    private void handleLoadProviderButtonAction(ActionEvent event) {
//        try {
//            // Load providers from the clinicManager
//            clinicManager.loadProviders();
//            providers = FXCollections.observableArrayList();
//
//            // Determine which providers to load based on service type
//            RadioButton selectedServiceType = (RadioButton) serviceTypeGroup
//                    .getSelectedToggle();
//            if (selectedServiceType == null) {
//                showAlert(AlertType.ERROR, "Invalid Input",
//                        "Please select a service type before loading providers.");
//                return;
//            }
//
//            if (selectedServiceType == officeVisitRadioButton) {
//                // Load doctors
//                for (Provider provider : clinicManager.getProviders()) {
//                    if (provider instanceof Doctor) {
//                        providers.add(provider);
//                    }
//                }
//            } else if (selectedServiceType == imagingServiceRadioButton) {
//                // Load technicians
//                for (Provider provider : clinicManager.getProviders()) {
//                    if (provider instanceof Technician) {
//                        providers.add(provider);
//                    }
//                }
//            }
//
//            providerComboBox.setItems(providers);
//            providerComboBox.setDisable(false);
//
//        } catch (Exception e) {
//            showAlert(AlertType.ERROR, "Error",
//                    "An error occurred while loading providers.");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Finds a doctor by their name.
//     *
//     * @param name The name of the doctor.
//     * @return The Doctor object, or null if not found.
//     */
//    private Doctor findDoctorByName(String name) {
//        for (Provider provider : clinicManager.getProviders()) {
//            if (provider instanceof Doctor) {
//                if (provider.getProfile().equals(name)) {
//                    return (Doctor) provider;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Finds a technician by their name.
//     *
//     * @param name The name of the technician.
//     * @return The Technician object, or null if not found.
//     */
//    private Technician findTechnicianByName(String name) {
//        for (Provider provider : clinicManager.getProviders()) {
//            if (provider instanceof Technician) {
//                if (provider.getProfile().equals(name)) {
//                    return (Technician) provider;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Displays an alert dialog with the specified information.
//     *
//     * @param alertType The type of alert.
//     * @param title     The title of the alert dialog.
//     * @param message   The content message of the alert dialog.
//     */
//    private void showAlert(AlertType alertType, String title,
//                           String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
