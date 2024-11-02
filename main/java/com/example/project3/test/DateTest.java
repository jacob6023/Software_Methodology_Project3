package com.example.project3.test;
import com.example.project3.clinic.src.project.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for isValid() method of Date class.
 *
 * @author Jack Crosby
 */
public class DateTest {

    /**
     * Test of isValid method, of class Date.
     * 4 invalid and two valid test cases.
     */
    @Test
    public void isValid() {
        // Invalid test cases
        Date invalidYear = new Date(0, 5, 15);  // Year is below MIN_YEAR
        assertFalse( invalidYear.isValid());

        Date invalidMonthBelow = new Date(2024, 0, 10);  // Month is below 1
        assertFalse( invalidMonthBelow.isValid());

        Date invalidMonthAbove = new Date(2024, 13, 15);  // Month is above 12
        assertFalse( invalidMonthAbove.isValid());

        Date invalidDay = new Date(2024, 4, 31);  // April has only 30 days
        assertFalse(invalidDay.isValid());

        // Valid test cases
        Date validRegularDate = new Date(2023, 7, 15);  // Regular valid date
        assertTrue(validRegularDate.isValid());

        Date validLeapYear = new Date(2024, 2, 29);  // Valid leap year date
        assertTrue(validLeapYear.isValid());
    }

}