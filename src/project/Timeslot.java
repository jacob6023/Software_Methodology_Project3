package Project_1.src.project;

/**
 * This enum provides the timeslots available for a provider.
 *
 * @author Jack Crosby
 */
public class Timeslot implements Comparable<Timeslot>{
    private int hour;
    private int minute;

    // Constants for default constructor
    private static final int DEFAULT_HOUR = -1;
    private static final int DEFAULT_MINUTE = -1;

    // Constant for computing total time in minutes for afternoon appointments
    private static final int NOON = 12;

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
        this.hour = DEFAULT_HOUR;
        this.minute = DEFAULT_MINUTE;
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

    /**
     * Check if two timeslots are the same.
     *
     * @param object
     * @return
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


