package Project_1.src;

public class Patient implements Comparable<Patient>{
    private Profile profile;
    private Visit visits; //a LL of visits (completed appt. )
    public int charge(){} //traverse the LL to compute the charge
}
