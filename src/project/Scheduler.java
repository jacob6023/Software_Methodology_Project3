package Project_1.src.project;
/**
 * @author Vikram kadyan
 */
import java.util.Scanner;
import java.util.StringTokenizer;

public class Scheduler {
    private List appointments;
    private MedicalRecord medRecord;

    public Scheduler() {
        this.appointments = new List();
        this.medRecord = new MedicalRecord();
    }

    // Parameterized constructor
    public Scheduler(List appointments, MedicalRecord medRecord) {
        this.appointments = appointments;
        this.medRecord = medRecord;
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
            switch (comm) { //runs each command separtely
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
                    printBillingStatements();
                    break;
                case "Q":
                    System.out.println("Scheduler terminated.");
                    return;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private void scheduleAppointment(String command) {
        String[] tokens = command.split(",");

        Date appointmentDate = new Date(tokens[1].trim());

        int slotNum = Integer.parseInt(tokens[2].trim());
        if(slotNum > 6) return; // if timeslot invalid
        Timeslot timeslot = Timeslot.valueOf("SLOT" + slotNum);

        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());

        String providerName = tokens[6].trim();
        Provider provider;

        try {
            provider = Provider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid provider name: " + providerName);
            return;
        }



        //check if real date
        if (!appointmentDate.isValid() && !appointmentDate.schedulableDate()){
            System.out.println("Invalid appointment date");
            return;
        }

        //check dob
        if(!patientDOB.isValid() && !patientDOB.realDOB()){
            System.out.println("Invalid DOB");
        }

        Profile patientProfile = new Profile( patientFirstName, patientLastName,patientDOB);
       // Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
//
//
//
   //     appointments.add(newAppointment);
    //    Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // Check if an appointment already exists for this patient, date, and timeslot
        if (appointments.appointmentExists(patientProfile, timeslot, appointmentDate)) {
            System.out.println("Appointment already exists for this patient, date, and timeslot");
            return;
        }

        // Check if the provider is available at the specified timeslot and date
        if (!appointments.isProviderAvailable(provider, timeslot, appointmentDate)) {
            System.out.println("Provider is not available at the specified timeslot");
            return;
        }

        // Create and add the new appointment
        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        appointments.add(newAppointment);
        System.out.println("Appointment scheduled successfully");

    }

    private void cancelAppointment(String command) {
        String[] tokens = command.split(",");
        //cancels an appointment
        Date appointmentDate = new Date(tokens[1].trim());

        int slotNum = Integer.parseInt(tokens[2].trim());
        if(slotNum > 6) return;
        Timeslot timeslot = Timeslot.valueOf("SLOT" + slotNum);

        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());

        String providerName = tokens[6].trim();
        Provider provider = Provider.valueOf(providerName);

        if (!appointmentDate.isValid() && !appointmentDate.schedulableDate()){
            System.out.println("Invalid appointment date");
            return;
        }

        Profile patientProfile = new Profile(patientFirstName, patientLastName, patientDOB);

        // Identify the appointment to remove instead of creating a new one
        Appointment existingAppointment = appointments.identifyAppointment(patientProfile, provider, timeslot, appointmentDate);
        if (existingAppointment != null) {
            appointments.remove(existingAppointment);
            System.out.println("Appointment cancelled successfully");
        } else {
            System.out.println("Appointment not found");
        }
    }

    private void rescheduleAppointment(String command) {
        String[] tokens = command.split(",");

        Date appointmentDate = new Date(tokens[1].trim());

        int slotNum = Integer.parseInt(tokens[2].trim());
        if(slotNum > 6) return;
        Timeslot timeslot = Timeslot.valueOf("SLOT" + slotNum);

        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());

        String providerName = tokens[6].trim();
        Provider provider = Provider.valueOf(providerName);


        Profile patientProfile = new Profile( patientFirstName, patientLastName,patientDOB);
        Appointment oldAppointment = appointments.identifyAppointment(patientProfile, provider, timeslot, appointmentDate);
        if(oldAppointment!= null) {
            appointments.remove(oldAppointment);
        }

        //schedule new appointment
        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        appointments.add(newAppointment);
    }

    private void printBillingStatements() {
        // Get the patients array from medRecord
        Patient[] patients = medRecord.getPatients();  // Use a getter method

        // Loop through the array to calculate and print billing information
        for (int i = 0; i < medRecord.getSize(); i++) {
            Patient currentPatient = patients[i];
            int total = currentPatient.charge(); // Call charge() method for each patient

            System.out.printf("Patient: %s, [Amount Due: $%d ] %n", currentPatient.getProfile().toString(), total);
        }
    }

    // Getter for appointments
    public List getAppointments() {
        return appointments;
    }

    // Setter for appointments
    public void setAppointments(List appointments) {
        this.appointments = appointments;
    }

    // Getter for medRecord
    public MedicalRecord getMedRecord() {
        return medRecord;
    }

    // Setter for medRecord
    public void setMedRecord(MedicalRecord medRecord) {
        this.medRecord = medRecord;
    }
}



