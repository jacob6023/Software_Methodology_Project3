package Project_1.src.project;

/**
 * @author Jack Crosby
 */
public enum Timeslot {
    SLOT1(9, 0),
    SLOT2(10, 45),
    SLOT3(11, 15),
    SLOT4(13, 30),
    SLOT5(15, 0),
    SLOT6(16, 15);

    private final int hour;
    private final int minute;

    /**
     * @return formatted string of the timeslot
     */
    @Override
    public String toString() {
        int displayHour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String period = (hour >= 12) ? "PM" : "AM";
        return String.format("%02d:%02d %s", displayHour, minute, period);
    }

    /**
     * Helper method to compute total minutes to use in compareTo
     * @return
     */
    private int toTotalMinutes() {
        return this.hour * 60 + this.minute;
    }

    /**
     * compare timeslots based on total minutes
     * @param timeslot the object to be compared.
     * @return < 0 if before, > 0 is after, 0 if equal
     */
    public int compareTime(Timeslot timeslot) {
        return Integer.compare(this.toTotalMinutes(), timeslot.toTotalMinutes());
    }



    /**
     * constructor
     * @param hour
     * @param minute
     */
    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * getters
     * @return
     */
    public int getHour() {return hour;}
    public int getMinute() {return minute;}

}


