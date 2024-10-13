package Project_1.src.project;

/**
 * This is an array based implementation of a linear data structure "bag", to hold a list of patient objects.
 * the bag allows adding a patient object but does not allow removal.
 *
 * @author Jack Crosby
 * @author Vikram kadyan
 */
public class MedicalRecord {
    private Patient[] patients;
    private int size; //number of patient objects in the array

    // Getters
    public Patient[] getPatients(){
        return patients;
    }
    public int getSize(){
        return size;
    }

    // Setters
    public void setPatients(Patient[] patients){
        this.patients = patients;
    }
    public void setSize(int size){
        this.size = size;
    }

    /**
     * Parameterized Constructor to create Medical Record.
     *
     * @param patients the patients list being added to the medical record.
     * @param size the size of the patients list.
     */
    public MedicalRecord(Patient[] patients, int size){
        this.patients = patients;
        this.size = size;
    }

    /**
     * Default Constructor that sets the patient list length to 10 and size to 0.
     */
    public MedicalRecord(){
        this.patients = new Patient[10];
        this.size = 0;
    }

    /**
     * Copy Constructor to create a new medical record using existing medical record.
     *
     * @param medicalRecord the medical record being copied.
     */
    public MedicalRecord(MedicalRecord medicalRecord){
        this.patients = medicalRecord.patients;
        this.size = medicalRecord.size;
    }

    /**
     * Helper method that creates new array of double size and puts old array into.
     */
    private void grow() {
        if(patients == null){
            patients = new Patient[10];
        }
        Patient [] oldPatientArray = patients;
        patients = new Patient[patients.length * 2];
        for(int i = 0; i < oldPatientArray.length; i++){
            patients[i] = oldPatientArray[i];
        }

    }

    /**
     * Adds new instance of patient.
     *
     * @param patient the patient being added.
     */
    public void add(Patient patient) {
        if (size == patients.length) {
            grow();
        }
        patients[size] = patient;
        size++;
    }

    /**
     * PS command to display the billing statements for all patients.
     * Each visit is charged based on the provider's specialty.
     * When this command is entered, assume all appointments have been completed and moved from the appointment calendar to the medical record.
     */
    public void printBillingStatement(){
        if(size == 0) System.out.println("no patients in the record");
        System.out.println("** Billing statement ordered by patient **");
        for(int i = 0; i < size; i++){
            System.out.println("(" + i + ") " + patients[i].getProfile().toString() + " [amount due: $" + patients[i].toString() + "]");
        }
        System.out.println("** end of list **");
        System.out.println();
    }

}
