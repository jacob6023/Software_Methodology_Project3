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
            String command = scanner.nextLine();
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

    private void scheduleAppointment(StringTokenizer token) {

    }

        private void cancelAppointment(StringTokenizer token) {
        // Implement the logic to cancel an appointment
    }

    private void rescheduleAppointment(StringTokenizer token) {
        // Implement the logic to reschedule an appointment
    }

    private void printBillingStatements() {
        // Traverse the medical record and calculate charges for each patient
    }
}

