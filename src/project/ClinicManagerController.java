package com.example.project3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.project3.util.Date;

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
    private ComboBox<String> timeSlotComboBox;

    @FXML
    private ComboBox<String> providerComboBox;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button loadProviderButton;

    // ToggleGroup for RadioButtons
    private ToggleGroup serviceTypeGroup;

    // ClinicManager instance
    private ClinicManager clinicManager;

    // ObservableLists for ComboBoxes
    private ObservableList<String> timeSlots;
    private ObservableList<String> providers;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        clinicManager = new ClinicManager();

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

            // Parse date strings to custom Date class
            Date dobDate = new Date(dob.format(DateTimeFormatter.ofPattern(
                    "yyyy-M-d")));
            Date appointmentDateCustom = new Date(appointmentDate.format(
                    DateTimeFormatter.ofPattern("yyyy-M-d")));

            // Create Profile and Patient
            Profile patientProfile = new Profile(firstName, lastName, dobDate);
            Patient patient = new Patient(patientProfile, null);

            // Get selected time slot number
            int timeSlotNum = Integer.parseInt(timeSlotStr.split(" - ")[0]);
            Timeslot timeslot = new Timeslot(timeSlotNum);

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
                    showAlert(AlertType.ERROR, "Provider Not Found",
                            "Selected provider not found.");
                    return;
                }
                Appointment appointment = new Appointment(
                        appointmentDateCustom, timeslot, patient,
                        selectedDoctor);
                clinicManager.getAppointments().add(appointment);
                showAlert(AlertType.INFORMATION, "Appointment Scheduled",
                        "Office visit scheduled successfully.");
            } else if (selectedServiceType == imagingServiceRadioButton) {
                // Imaging Service
                Technician selectedTechnician = findTechnicianByName(
                        providerStr);
                if (selectedTechnician == null) {
                    showAlert(AlertType.ERROR, "Provider Not Found",
                            "Selected provider not found.");
                    return;
                }
                // For simplicity, using XRAY as the imaging service
                Imaging imagingAppointment = new Imaging(appointmentDateCustom,
                        timeslot, patient, selectedTechnician, Radiology.XRAY);
                clinicManager.getAppointments().add(imagingAppointment);
                showAlert(AlertType.INFORMATION, "Appointment Scheduled",
                        "Imaging service scheduled successfully.");
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
        // Implement cancellation logic
        showAlert(AlertType.INFORMATION, "Cancel Appointment",
                "Feature not implemented yet.");
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
            // Load providers from the clinicManager
            clinicManager.loadProviders();
            providers = FXCollections.observableArrayList();

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
                for (Provider provider : clinicManager.getProviders()) {
                    if (provider instanceof Doctor) {
                        providers.add(provider.getProfile().toString());
                    }
                }
            } else if (selectedServiceType == imagingServiceRadioButton) {
                // Load technicians
                for (Provider provider : clinicManager.getProviders()) {
                    if (provider instanceof Technician) {
                        providers.add(provider.getProfile().toString());
                    }
                }
            }

            providerComboBox.setItems(providers);
            providerComboBox.setDisable(false);

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error",
                    "An error occurred while loading providers.");
            e.printStackTrace();
        }
    }

    /**
     * Finds a doctor by their name.
     *
     * @param name The name of the doctor.
     * @return The Doctor object, or null if not found.
     */
    private Doctor findDoctorByName(String name) {
        for (Provider provider : clinicManager.getProviders()) {
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
        for (Provider provider : clinicManager.getProviders()) {
            if (provider instanceof Technician) {
                if (provider.getProfile().toString().equals(name)) {
                    return (Technician) provider;
                }
            }
        }
        return null;
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
