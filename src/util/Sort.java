package Project_1.src.util;
import Project_1.src.project.Appointment;
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
    public static <E> void sort(List<E> list, Comparator<E> comparator) {
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
    public static void appointment(List<Appointment> list, char key) {
        Comparator<Appointment> comparator;

        switch (key) {
            case 'd': // Sort by date
                comparator = Comparator.comparing(Appointment::getDate);
                break;
            case 't': // Sort by timeslot
                comparator = Comparator.comparing(Appointment::getTimeslot);
                break;
            case 'p': // Sort by patient
                comparator = Comparator.comparing(Appointment::getPatient);
                break;
            case 'r': // Sort by provider
                comparator = Comparator.comparing(Appointment::getProvider);
                break;
            default:
                throw new IllegalArgumentException("Invalid key for sorting: " + key);
        }

        sort(list, comparator);
    }

    /**
     * Sort providers alphabetically based on their profile.
     *
     * @param list the list of providers to sort
     */
    public static void provider(List<Provider> list) {
        sort(list, Comparator.comparing(Provider::getProfile));
    }

}
