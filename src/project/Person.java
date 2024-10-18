package clinic.src.project;

/**
 *Defines the profile of each instance in the software system.
 *
 * @author Jack Crosby
 */
public class Person implements Comparable<Person>{
    /**
     * The profile of the person.
     */
    protected Profile profile;

    /**
     * Getter
     *
     * @return the profile of the person.
     */
    public Profile getProfile(){
        return profile;
    }

    /**
     * Setter
     *
     * @param profile the profile to set.
     */
    public void setProfile(Profile profile){
        this.profile = profile;
    }

    /**
     * Default Constructor
     */
    public Person(){
        this.profile = null;
    }

    /**
     * Copy Constructor
     * @param copyPerson the person to be copied.
     */
    public Person(Person copyPerson){
        this.profile = copyPerson.profile;
    }

    /**
     * Parameterized Constructor
     * @param profile the profile of the person.
     */
    public Person(Profile profile){
        this.profile = profile;
    }

    /**
     * Compare two Person objects.
     *
     * @param person the object to be compared.
     * @return 0 if equal, 1 if greater, -1 if less.
     */
    @Override
    public int compareTo(Person person){
        return this.profile.compareTo(person.profile);
    }

    /**
     * Determine if two Person objects are equal.
     *
     * @return true if equal, false if not.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Person person){
            return this.profile.equals(person.profile);
        }
        return false;
    }

    /**
     * Display profile information.
     */
    @Override
    public String toString(){
        return this.profile.toString();
    }

}
