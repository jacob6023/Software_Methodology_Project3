package com.example.project3.test;
import com.example.project3.clinic.src.project.Profile;
import com.example.project3.clinic.src.project.util.Date;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for compareTo() method of Profile class.
 */
public class ProfileTest {
    Profile profile1 = new Profile();
    Profile profile2 = new Profile();

    /**
     * Three test cases return -1, three test cases return 1 and one test case returns 0.
     */
    @Test
    public void compareTo() {
        System.out.println("First return '-1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 2000));
        profile2.set_fname("Jack");
        profile2.set_lname("Drosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(-1, profile1.compareTo(profile2));

        System.out.println("Second '-1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 1999));
        profile2.set_fname("Knack");
        profile2.set_lname("Crosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(-1, profile1.compareTo(profile2));


        System.out.println("Third '-1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 1999));
        profile2.set_fname("Jack");
        profile2.set_lname("Crosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(-1, profile1.compareTo(profile2));


        System.out.println("First '1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 2000));
        profile2.set_fname("Jack");
        profile2.set_lname("Brosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(1, profile1.compareTo(profile2));

        System.out.println("Second '1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 2000));
        profile2.set_fname("Cack");
        profile2.set_lname("Crosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(1, profile1.compareTo(profile2));

        System.out.println("Third '1' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 2001));
        profile2.set_fname("Jack");
        profile2.set_lname("Crosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(1, profile1.compareTo(profile2));

        System.out.println("'0' test case");
        profile1.set_fname("Jack");
        profile1.set_lname("Crosby");
        profile1.set_dob(new Date(1, 1, 2000));
        profile2.set_fname("Jack");
        profile2.set_lname("Crosby");
        profile2.set_dob(new Date(1, 1, 2000));
        assertEquals(0, profile1.compareTo(profile2));

    }
}