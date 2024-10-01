package Project_1.src.project;

public class List {
    private Appointment[] appointments;
    private int size; //num appointments in the array

    private int find(Appointment appointent) //helper method

    private void grow() //helper method to increase the capacity by 4

    public boolean contains(Appointment appointment)
    public void add(Appointment appointment)
    public void remove(Appointment appointment)
    public void printByPatient() //ordered by patient profile, date/timeslot
    public void printByLocation() //ordered by county, data/timeslot
    public void printByAppointment() //ordered by date/timeslot, provider name

    /* This is an array-based implementation of a linear data structure “List” to hold the list of appointment  objects.
    A new appointment is always added to the end of the array. An instance of this class is a growable  list with an initial array capacity of 4,
    and it automatically increases the capacity by 4 whenever it is full.  The list does not decrease in capacity.
    */

    /* You can add necessary constants, constructors, and methods. However, you CANNOT change or add  instance variables. -2 points for each violation.
 You MUST implement and use the methods listed above; you CANNOT change the signatures of the  methods. -2 points for each violation.
 You CAN use System.out ONLY in the three print() methods listed above.
 The find() method searches for an appointment in the list and returns the index if it is found; it returns NOT_FOUND if it is not in the list. Define a constant value -1 for NOT_FOUND, or -2 point.   You must use an “in-place” sorting algorithm to implement the sorting, i.e., the order of the objects in the  array will be rearranged after the sorting without using an additional array. You CANNOT use  Arrays.sort() or System.arraycopy() or any other Java library classes or utilities for sorting. You must  write the code yourself to sort it. You will lose 10 points for the violation.
    */

    /* The charge() method traverses the linked list “visits” and returns the charge of the patient, -3 points if  this method is missing. */

    /**
     * TODO: charge(), equals(), toString(), compareTo()
     */







}