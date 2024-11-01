package com.example.project3.util;

import com.example.project3.clinic.*;
import java.util.Comparator;

/**
 * This utility class handles the sorting.
 *
 * @author Jack Crosby
 */
public class Sort {

    /**
     * General-purpose sort method for sorting a list based on a provided comparator.
     *
     * @param list the list to sort.
     * @param comparator the comparator used to determine the order of the list.
     * @param <E> the type of elements in the list.
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
     *
     * The key can be used to decide sorting by date, timeslot, patient, or provider.
     * @param list the list of appointments to sort.
     * @param key the key to determine the sorting criteria ('d' for date, 't' for timeslot, etc.).
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
            case 'c': // Sort by county, then date, then timeslot
                comparator = Comparator
                        .comparing( (Appointment a) ->
                        {
                            if (a.getProvider() instanceof Provider) {
                                return ((Provider) a.getProvider()).getLocation().getCounty();
                            }
                            return "";
                        } )
                        .thenComparing(Appointment::getDate)
                        .thenComparing(Appointment::getTimeslot);
                break;
            case 'i': // Sort imaging/radiology appointments with Piscataway first, then Bridgewater, by date and time
                comparator = Comparator
                        .comparing( (Appointment a) -> {
                            if (a.getProvider() instanceof Provider) {
                                Location location = ((Provider) a.getProvider()).getLocation();
                                return location == Location.PISCATAWAY ? 0 : 1; // Prioritize Piscataway
                            }
                            return Integer.MAX_VALUE;
                        } )
                        .thenComparing(Appointment::getDate)
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
     * @param list the list of providers to sort.
     * @param key the key to determine the sorting criteria ('i' for initial, 'l' for location, 'c' for profile).
     */
    public static void providers(List<Provider> list, char key) {
        Comparator<Provider> comparator = null;
        switch(key){
            case 'i': // initial sort for rotation list
                sort(list, Comparator.comparing(Provider::getLocation));
                break;
            case 'l': // Sort by location
                comparator = Comparator.comparing((Provider a) -> a.getLocation().getCounty());
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

    /**
     * Sort medical record of patients based on their profile.
     *
     * @param list the list of patients to sort in the medical record.
     */
    public static void medicalRecord(List<Patient> list) {
        Comparator<Patient> comparator = Comparator
                .comparing((Patient a) -> a.getProfile().get_lname())
                .thenComparing(a -> a.getProfile().get_fname())
                .thenComparing(a -> a.getProfile().get_dob());
        sort(list, comparator);
    }

}
