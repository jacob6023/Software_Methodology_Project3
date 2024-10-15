package Project_1.src.util;
import Project_1.src.project.Appointment;
import java.util.Iterator;

/**
 * This is a generic class for code reuse. It is a custom list class that will be used to store appointments.
 * TODO: Always use this class whenever you want to hold a collection of objects.

 * Extend this class if you need to add anything.
 * TODO: You must use a single instance of List<Appointment> to hold a list of office and imaging appointments
 * TODO: -> and use a single instance of List<Provider> to hold a list of all providers.
 * ^^^^^^^^ THESE 2 INSTANCES ARE IN THE USER INTERFACE CLASS ^^^^^^^
 * TODO: Delete Medical Record. Just do it later so you don't have a bunch of red line everywhere right now.
 *
 * @author Jack Crosby
 */
public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size; //num appointments in the array

    // Constant to indicate if appointment has not been found
    private static final int NOT_FOUND = -1;

    /**
     * Default constructor with an initial capacity/length of 4.
     */
    @SuppressWarnings("unchecked")
    public List(){
        this.objects = (E[]) new Object[4];
        this.size = 0;
    }

    /**
     * Helper method to search for an appointment in the list.
     *
     * @param e the appointment being searched for.
     * @return NOT_FOUND if appointment isn't in appointments[].
     */
    private int find(E e){
        if(isEmpty()){
            return NOT_FOUND;
        }
        for(int i = 0; i < size; i++){
            if(objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Helper method to increase the capacity by 4.
     */
    @SuppressWarnings({"manualArrayCopy", "unchecked"})
    private void grow(){
        E[] temp = (E[]) new Object[objects.length + 4];
        for(int i = 0; i < size; i++){
            temp[i] = objects[i];
        }
        objects = temp;
    }

    /**
     * Determine if the object is in the list of objects.
     *
     * @param e object we are determining if is in the list.
     * @return true if list contains the argument appointment, false if not found.
     */
    public boolean contains(E e){
        return find(e) != NOT_FOUND;
    }

    /**
     * Add Object to the list.
     *
     * @param e Object being added to the list.
     */
    public void add(E e){
        if(isEmpty()){
            grow();
        }
        if(size == objects.length){
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Remove appointment from the list.
     *
     * @param e object being removed from the list.
     */
    public void remove(E e){
        int index = find(e);
        if(index == NOT_FOUND){
            return;
        }
        for(int i = index; i < size - 1; i++){
            objects[i] = objects[i + 1];
        }
        size--;
    }

    /**
     * Determine if the object list is empty.
     *
     * @return true if object list is empty, false otherwise.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Access size of the object list.
     *
     * @return size of the object list.
     */
    public int size(){
        return size;
    }

    /**
     * Creates an iterator for the list.
     *
     * @return an iterator for the list.
     */
    public Iterator<E> iterator(){
        return new ListIterator<E>();
    }

    /**
     * Return the object at the index.
     *
     * @param index the index of the object.
     * @return the object at the at index.
     */
    public E get(int index){
        return objects[index];
    }

    /**
     * Put the object e at the index.
     *
     * @param index the index to put the object at.
     * @param e the object to put at the index.
     */
    public void set(int index, E e){
        objects[index] = e;
    }

    /**
     * Get the index of the object.
     *
     * @param e the object to find.
     * @return index of the object or -1.
     */
    public int indexOf(E e){
        return find(e);
    }

    /**
     * We defined a generic class that holds a list of objects. We need to implement the iterator interface to iterate through the list to use for-each loop.
     *
     * @param <E> the type of object in the list.
     */
    private class ListIterator<E> implements Iterator<E>{

        private int currentIndex = 0;

        /**
         * Abstract method to determine if the list has a next object.
         *
         * @return false if it's empty or end of list.
         */
        @Override
        public boolean hasNext(){
            return currentIndex < size;
        }

        /**
         * Abstract method to get the next object in the list.
         *
         * @return the next object in the list.
         */
        @SuppressWarnings("unchecked")
        @Override
        public E next(){
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
    