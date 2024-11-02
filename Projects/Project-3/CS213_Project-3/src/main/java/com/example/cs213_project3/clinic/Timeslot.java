package com.example.cs213_project3.clinic;

/**
 * This enum provides the timeslots available for a provider.
 *
 * @author Jack Crosby
 */
public class Timeslot implements Comparable<Timeslot>{
    /**
     * The hour and minute of the timeslot.
     */
    private int hour;

    /**
     * The minute of the timeslot.
     */
    private int minute;

    /**
     * Default values for the timeslot.
     */
    private static final int DEFAULT_HOUR = -1;

    /**
     * Default values for the timeslot.
     */
    private static final int DEFAULT_MINUTE = -1;

    /**
     * The hour of noon.
     */
    private static final int NOON = 12;

    /**
     * Getters
     * @return the hour and minute of the timeslot.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Get the minute of the timeslot.
     * @return the minute of the timeslot.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Setters
     * @param hour the hour to set.
     */
    public void setHour(int hour){
        this.hour = hour;
    }

    /**
     * Set the minute of the timeslot.
     * @param minute the minute to set.
     */
    public void setMinute(int minute){
        this.minute = minute;
    }

    /**
     * Default Constructor to create a timeslot.
     */
    public Timeslot(){
        this.hour = DEFAULT_HOUR;
        this.minute = DEFAULT_MINUTE;
    }

    /**
     * Copy Constructor to copy a timeslot.
     * @param copyTimeslot the timeslot to be copied.
     */
    public Timeslot(Timeslot copyTimeslot){
        this.hour = copyTimeslot.hour;
        this.minute = copyTimeslot.minute;
    }

    /**
     * Parameterized Constructor to create timeslot.
     *
     * @param hour the hour of the timeslot.
     * @param minute the minute of the timeslot.
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructor to create timeslot based on slot number.
     *
     * @param slotNum the slot number to be assigned.
     */
    public Timeslot(int slotNum){
        assignSlot(slotNum);
    }

    /**
     * Assign a slot number to a timeslot.
     * In interface, we first use the default constructor and then assign the slot number.
     *
     * @param slotNumber the slot number to be assigned.
     */
    public void assignSlot(int slotNumber) {
        switch (slotNumber) {
            case 1:
                this.hour = 9;
                this.minute = 0;
                break;
            case 2:
                this.hour = 9;
                this.minute = 30;
                break;
            case 3:
                this.hour = 10;
                this.minute = 0;
                break;
            case 4:
                this.hour = 10;
                this.minute = 30;
                break;
            case 5:
                this.hour = 11;
                this.minute = 0;
                break;
            case 6:
                this.hour = 11;
                this.minute = 30;
                break;
            case 7:
                this.hour = 14;
                this.minute = 0;
                break;
            case 8:
                this.hour = 14;
                this.minute = 30;
                break;
            case 9:
                this.hour = 15;
                this.minute = 0;
                break;
            case 10:
                this.hour = 15;
                this.minute = 30;
                break;
            case 11:
                this.hour = 16;
                this.minute = 0;
                break;
            case 12:
                this.hour = 16;
                this.minute = 30;
                break;
            default:
                throw new IllegalArgumentException("Invalid slot number. Must be between 1 and 12.");
        }
    }

    /**
     * Output the timeslot in format "hh:mm AM/PM".
     *
     * @return formatted string of the timeslot.
     */
    @Override
    public String toString() {
        int displayHour = hour;
        String period = (hour >= NOON) ? "PM" : "AM";
        if(hour > NOON){
            displayHour -= NOON;
        }
        return String.format("%d:%02d %s", displayHour, minute, period);
    }

    /**
     * Compare timeslots based on total minutes.
     *
     * @param timeslot the object to be compared.
     * @return less than 0 if before, greater than 0 if after, 0 if equal.
     */
   @Override
    public int compareTo(Timeslot timeslot){
       int compareHour = Integer.compare(hour, timeslot.getHour());
       if(compareHour != 0){
           return compareHour;
       }
       return Integer.compare(this.minute, timeslot.getMinute());
   }

   /**
    * * Check if two timeslots are the same.
     *
     * @param object the timeslot being compared to.
     * @return true if the timeslots are the same, false otherwise.
     */
   @Override
   public boolean equals(Object object){
       if(object instanceof Timeslot timeslot){
           timeslot = (Timeslot) object;
           return this.hour == timeslot.getHour() && this.minute == timeslot.getMinute();
       }
       return false;
   }

}


