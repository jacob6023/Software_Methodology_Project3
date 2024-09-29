package Project_1.src;

public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit visits; //a LL of visits (completed appt. )

    //constructor for Patients
    public Patient(Profile profile){
        this.profile = profile;
        this.visits = null;
    }

    //override for equals, toString, and compareTo
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient diff = (Patient) obj;
        return profile.equals(diff.profile);
    }
    @Override
    public String toString() {
        //later
    }

    @Override
    public int compareTo(Patient other) {
        //later
    }

    //traverse linked list to find the total charge
    public int charge(){
        int total = 0;
        Visist currentVisit=visits;
        while (currentVisit!=null){
            total += currentVisit.getAppointment().getProvider().getSpecialty().getCharge();
            currentVisit = currentVisit.getNext();
        }
        return total;
    } //traverse the LL to compute the charge

}

