package Project_1.src.project;

public class MedicalRecord {
    private Patient[] patients;
    private int size; //number of patient objects in the array

    /* This is an array-based implementation of a linear data structure, “Bag,” to hold a list of patient objects.  The bag allows adding a patient object but does not allow removal.
 TODO: You must implement an add() method, or -2 points.
 TODO: You can add constructors and methods, but you CAN NOT add/change the instance variables, or -2 points for each violation */

    /* This is an array-based implementation of a linear data structure, “Bag,” to hold a list of patient objects.  The bag allows adding a patient object but does not allow removal.
 TODO: You must implement an add() method, or -2 points.
 TODO: You can add constructors and methods, but you CAN NOT add/change the instance variables, or -2 points for each violation */
    public MedicalRecord() {
        int startingCap = 2;
        patients = new Patient[startingCap];
        size = 0;
    }

    private void grow(){
        Patient[] newPatient = new Patient[patients.length+1];
        System.arraycopy(patients, 0, newPatient, 0, patients.length);
        patients = newPatient;
    }

    public void add(Patient patient){
        if (size == patients.length){
            grow();
        }
        patients[size++] = patient;
    }

}
