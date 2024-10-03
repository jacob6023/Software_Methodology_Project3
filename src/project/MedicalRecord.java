package Project_1.src.project;

public class MedicalRecord {
    private Patient[] patients;
    private int size; //number of patient objects in the array

    /**
     * Helper method that creates new array of double size and puts old array into
     */
    private void enlarge() {
        Patient[] newPatientsArray = new Patient[patients.length * 2];
        System.arraycopy(patients, 0, newPatientsArray, 0, patients.length);
        patients = newPatientsArray;
    }


    public void add(Patient patient) {
        // If the array is full, grow the array
        if (size == patients.length) {
            enlarge();
        }
        patients[size++] = patient;
    }


    /**
     * getters
     */
    public Patient[] getPatients(){return patients;}
    public int getSize(){return size;}

    /**
     * setters
     */
    public void setPatients(){this.patients = patients;}
    public void setSize(){this.size = size;}

    public MedicalRecord(Patient[] patients, int size){
        this.patients = patients;
        this.size = size;
    }

    /**
     * set medical record to size 10 if not specified
     */
    public MedicalRecord(){
        this.patients = new Patient[10];
        this.size = 0;
    }

    public MedicalRecord(MedicalRecord medicalRecord){
        this.patients = medicalRecord.patients;
        this.size = medicalRecord.size;
    }



}
