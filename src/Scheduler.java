package Project_1.src;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Scheduler {
    private List appointments;
    private MedicalRecord medRecord;

    public Scheduler() {
        appointments = new List();
        medRecord = new MedicalRecord();
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
                    List.printByAppointment();
                    break;
                case "PP":
                    List.printByPatient();
                    break;
                case "PL":
                    List.printByLocation();
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
        Timeslot timeslot = Timeslot.valueOf("SLOT" + tokens[2].trim());
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        String providerLastName = tokens[6].trim();

        if (!appointmentDate.isValid()){
            System.out.println("Invalid appointment date");
            return;
        }

        if (timeslot != "SLOT1" && timeslot != "SLOT2"  && timeslot != "SLOT3" && timeslot != "SLOT4" && timeslot != "SLOT5" && timeslot != "SLOT6") {
            System.out.println("Invalid timeslot.");
            return;
        }

        Profile patientProfile = new Profile( patientFirstName, patientLastName,patientDOB);

        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        List.add(newAppointment);

    }

    private void cancelAppointment(String command) {
        String[] tokens = command.split(",");
        //cancels an appointment
        Date appointmentDate = new Date(tokens[1].trim());
        Timeslot timeslot = Timeslot.valueOf("SLOT" + tokens[2].trim());
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        String providerLastName = tokens[6].trim();

        if (!appointmentDate.isValid()){
            System.out.println("Invalid appointment date");
            return;
        }

        if (timeslot != "SLOT1" && timeslot != "SLOT2"  && timeslot != "SLOT3" && timeslot != "SLOT4" && timeslot != "SLOT5" && timeslot != "SLOT6") {
            System.out.println("Invalid timeslot.");
            return;
        }

        Profile patientProfile = new Profile( patientFirstName, patientLastName,patientDOB);

        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        List.remove(newAppointment);
    }

    private void rescheduleAppointment(String command) {
        String[] tokens = command.split(",");

        Date appointmentDate = new Date(tokens[1].trim());
        Timeslot timeslot = Timeslot.valueOf("SLOT" + tokens[2].trim());
        String patientFirstName = tokens[3].trim();
        String patientLastName = tokens[4].trim();
        Date patientDOB = new Date(tokens[5].trim());
        String providerLastName = tokens[6].trim();

        Profile patientProfile = new Profile( patientFirstName, patientLastName,patientDOB);

        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patientProfile, provider);
        List.find(newAppointment);
    }

    private void printBillingStatements() {
        // Traverse the medical record and calculate charges for each patient

    }
}

