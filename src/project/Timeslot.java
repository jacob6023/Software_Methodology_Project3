package Project_1.src.project;

/**
 * This enum provides the timeslots available for a provider.
 *
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

    // Getters
    public int getHour() {return hour;}
    public int getMinute() {return minute;}

    /**
     * Parameterized Constructor to create timeslot.
     *
     * @param hour the hour of the timeslot.
     * @param minute the minute of the timeslot.
     */
    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Output the timeslot.
     * Does not output the timeslot name, ex. SLOT3.
     *
     * @return formatted string of the timeslot.
     */
    @Override
    public String toString() {
        int displayHour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String period = (hour >= 12) ? "PM" : "AM";
        return String.format("%02d:%02d %s", displayHour, minute, period);
    }

    /**
     * Helper method to compute total minutes to use in compareTo.
     *
     * @return the total minutes.
     */
    private int toTotalMinutes() {
        return this.hour * 60 + this.minute;
    }

    /**
     * Compare timeslots based on total minutes.
     *
     * @param timeslot the object to be compared.
     * @return < 0 if before, > 0 if after, 0 if equal.
     */
    public int compareTime(Timeslot timeslot) {
        return Integer.compare(this.toTotalMinutes(), timeslot.toTotalMinutes());
    }

}


