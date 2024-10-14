package Project_1.src.project;

/**
 * This enum provides the timeslots available for a provider.
 * TODO: make as a java class
 * The system shall provide 6 slots in the morning and 6 slots in the afternoon. A total of 12 slots per weekday.
 * -> Each slot is 30 minutes
 * the first in the morning is 9:00 am
 * the first in the afternoon is 2:00 pm
 * The last appointment is 4:30 pm.
 *
 * @author Jack Crosby
 */
public class Timeslot implements Comparable<Timeslot>{
    private int hour;
    private int minute;

    // Constants for default constructor
    public static final int INVALID_HOUR = -1;
    public static final int INVALID_MINUTE = -1;

    // Constant for computing total time in minutes for afternoon appointments
    public static final int NOON = 12;

    // Getters
    public int getHour() {return hour;}
    public int getMinute() {return minute;}

    // Setters
    public void setHour(int hour){this.hour = hour;}
    public void setMinute(int minute){this.minute = minute;}

    /**
     * Default Constructor
     * TODO: in interface, when handling timeslot input, declare this, first and then use assignSlot method to assign the slot number.
     */
    public Timeslot(){
        this.hour = INVALID_HOUR;
        this.minute = INVALID_MINUTE;
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
     * Copy Constructor
     */
    public Timeslot(Timeslot copyTimeslot){
        this.hour = copyTimeslot.hour;
        this.minute = copyTimeslot.minute;
    }

    /**
     * Helper method to compute total minutes for slot numbers.
     *
     * @return the total minutes.
     */
    private static int hourToTotalMinutes(int hour) {
        if(hour <= NOON){
            return hour * 60;
        }
        return (hour + NOON) * 60; // afternoon in total minutes
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
     * Output the timeslot.
     * Does not output the timeslot name, ex. SLOT3.
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
     * @return < 0 if before, > 0 if after, 0 if equal.
     */
   @Override
    public int compareTo(Timeslot timeslot){
       int compareHour = Integer.compare(hour, timeslot.getHour());
       if(compareHour != 0){
           return compareHour;
       }
       return Integer.compare(this.minute, timeslot.getMinute());
   }

   @Override
   public boolean equals(Object object){
       if(object instanceof Timeslot timeslot){
           timeslot = (Timeslot) object;
           return this.hour == timeslot.getHour() && this.minute == timeslot.getMinute();
       }
       return false;
   }

}


