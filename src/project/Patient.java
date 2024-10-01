package Project_1.src.project;

public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit next; //a LL of visits (completed appt. )

    /**
     * traverse the LL to compute the charge
     * @return
     */
    public int charge(){

    }

    /**
     * getters
     */
    public Profile getProfile(){return profile;}
    public Visit getVisit(){return next;}

    /**
     * Setters
     */
    public void setProfile(Profile profile){this.profile = profile;}
    public void setVisits(Visit next){this.next = next;}

    /**
     * constructor
     * @param patient
     */
    public Patient(Patient patient){
        this.profile = profile;
        this.next = next;
    }

    public Patient(){
        this.profile = null;
        this.visits = null;
    }

    //override for equals, toString, and compareTo
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Visit next){
            return this.next.equals(next);
        }
        return false;
    }
    @Override
    public String toString() {
        return profile.toString();
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

