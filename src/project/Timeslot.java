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
    private static final int DURATION = 45; //will use to determine if there is no overlap

    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * getters
     * @return
     */
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }

    /**
     * @return formatted string of the timeslot
     */
    public String timeString() {
        int displayHour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String period = (hour >= 12) ? "PM" : "AM";
        return String.format("%02d:%02d %s", displayHour, minute, period);
    }

    /**
     * @param timeslot
     * @return the total time so we can compare in Appointment
     */
    public int computeTime(Timeslot timeslot) {
        // Convert the time to minutes since midnight for comparison
        int totalTime;
        totalTime = this.hour * 60 + this.minute;
        return totalTime;
    }

    /**
     *
     * @return total start time in minutes
     */
    public int computeStartTime() {
        return this.hour * 60 + this.minute;
    }

    /**
     *
     * @return end time in minutes
     */
    public int computeEndTime() {
        return computeStartTime() + DURATION;  // Assuming a 45-minute duration for each slot
    }

    /**
     *
     * @param other timeslot
     * @return true if a timeslot will overlap
     */
    public boolean overlapsWith(Timeslot other) {
        return this.computeStartTime() < other.computeEndTime() && this.computeEndTime() > other.computeStartTime();
    }

}


