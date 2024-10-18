package clinic.test;
import clinic.src.project.Doctor;
import clinic.src.project.Profile;
import clinic.src.project.Provider;
import clinic.src.project.Technician;
import clinic.src.project.util.Date;
import clinic.src.project.util.List;
import org.junit.Test;
import static clinic.src.project.Location.BRIDGEWATER;
import static clinic.src.project.Specialty.ALLERGIST;
import static org.junit.Assert.*;

public class ListTest {
    Doctor doctor;
    Technician technician;
    List<Provider> providerList;

    /**
     * Test of add method, of class List.
     * One test case for adding a Doctor object and one test case for adding a Technician object to a List<Provider> object.
     */
    @Test
    public void add() {
        doctor = new Doctor(new Profile("Jack", "Crosby", new Date(1, 1, 2000)), BRIDGEWATER, ALLERGIST, "14125");
        technician = new Technician(new Profile("John", "Doe", new Date(1, 1, 2000)), BRIDGEWATER, 350);
        providerList = new List<Provider>();
        assertFalse(providerList.contains(doctor));
        assertFalse(providerList.contains(technician));
        providerList.add(doctor);
        providerList.add(technician);
        assertTrue(providerList.contains(doctor));
        assertTrue(providerList.contains(technician));

    }

    /**
     * Test of get remove, of class List.
     * One test case for removing a Doctor object One test case for removing a Technician object from a List<Provider> object.
     */
    @Test
    public void remove() {
        doctor = new Doctor(new Profile("Jack", "Crosby", new Date(1, 1, 2000)), BRIDGEWATER, ALLERGIST, "14125");
        technician = new Technician(new Profile("John", "Doe", new Date(1, 1, 2000)), BRIDGEWATER, 350);
        providerList = new List<Provider>();
        assertFalse(providerList.contains(doctor));
        assertFalse(providerList.contains(technician));
        providerList.remove(doctor);
        providerList.remove(technician);
        assertTrue(providerList.contains(doctor));
        assertTrue(providerList.contains(technician));
    }
}