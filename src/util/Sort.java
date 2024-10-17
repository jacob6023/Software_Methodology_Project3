package Project_1.src.util;
import Project_1.src.project.Appointment;
import Project_1.src.project.Patient;
import Project_1.src.project.Provider;
import java.util.Comparator;

/**
 * This utility class handles the sorting.
 * You should define only static methods for sorting. For example, sort the appointments ordered by different keys.
 * Your team should make design decisions so you can reuse the code as much as possible.
 *
 * TODO: you should define only static methods for sorting.
 *
 * @author Jack Crosby
 */
public class Sort {

    /**
     * General-purpose sort method for sorting a list based on a provided comparator.
     *
     * @param list the list to sort
     * @param comparator the comparator used to determine the order of the list
     * @param <E> the type of elements in the list
     */
    private static <E> void sort(List<E> list, Comparator<E> comparator) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    E temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Sort appointments based on a given key.
     * The key can be used to decide sorting by date, timeslot, patient, or provider.
     * TODO: sorting by county
     * @param list the list of appointments to sort
     * @param key the key to determine the sorting criteria ('d' for date, 't' for timeslot, etc.)
     */
    public static void appointments(List<Appointment> list, char key) {
        Comparator<Appointment> comparator = null;
        switch (key) {
            case 'a': // Sort by appointment date, then timeslot, then provider's name (last name, first name).
                comparator = Comparator
                        .comparing(Appointment::getDate)
                        .thenComparing(Appointment::getTimeslot)
                        .thenComparing(a -> a.getProvider().getProfile().get_lname())
                        .thenComparing(a -> a.getProvider().getProfile().get_fname());
                break;
            case 'p': // Sort by profile (last name, first name, date of birth), then date, then timeslot.
                comparator = Comparator
                        .comparing((Appointment a) -> a.getPatient().getProfile().get_lname())
                        .thenComparing(a -> a.getPatient().getProfile().get_fname())
                        .thenComparing(a -> a.getPatient().getProfile().get_dob())
                        .thenComparing(Appointment::getDate)
                        .thenComparing(Appointment::getTimeslot);
                break;
           case 'd': // Sort by date, then timeslot.
                comparator = Comparator
                        .comparing(Appointment::getDate)
                        .thenComparing(Appointment::getTimeslot);
                break;

            default:
                throw new IllegalArgumentException("Unexpected value: " + key);
        }
        sort(list, comparator);
    }

    /**
     * Sort providers alphabetically based on their profile.
     *
     * @param list the list of providers to sort
     */
    public static void providers(List<Provider> list, char key) {
        Comparator<Provider> comparator = null;
        switch(key){
            case 'i': // initial sort for rotation list
                sort(list, Comparator.comparing(Provider::getLocation));
                break;
            case 'l': // Sort by location (county, zip), then last name, then first name.
                comparator = Comparator
                        .comparing((Provider a) -> a.getLocation().getCounty())
                        .thenComparing(a -> a.getLocation().getZip())
                        .thenComparing(a -> a.getProfile().get_lname())
                        .thenComparing(a -> a.getProfile().get_fname());
                break;
            case 'c': // Provider profile last name, then first name.
                comparator = Comparator
                        .comparing((Provider a) -> a.getProfile().get_lname())
                        .thenComparing(a -> a.getProfile().get_fname());
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + key);
        }
        sort(list, comparator);
    }

    public static void medicalRecord(List<Patient> list) {
        Comparator<Patient> comparator = Comparator
                .comparing((Patient a) -> a.getProfile().get_lname())
                .thenComparing(a -> a.getProfile().get_fname())
                .thenComparing(a -> a.getProfile().get_dob());
        sort(list, comparator);
    }

}
