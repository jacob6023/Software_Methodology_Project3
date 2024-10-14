package Project_1.src.project;

/**
 * TODO: Defines the profile of each instance in the software system.
 * This is the superclass of the Patient and Provider class
 *
 * EVERYTHING HERE SHOULD BE GOOD AND DONE
 *
 * @author Jack Crosby
 */
public class Person implements Comparable<Person>{
    protected Profile profile;

    // Getter
    public Profile getProfile(){return profile;}

    // Setter
    public void setProfile(Profile profile){this.profile = profile;}

    /**
     * Default Constructor
     */
    public Person(){
        this.profile = null;
    }

    /**
     * Parameterized Constructor
     */
    public Person(Profile profile){
        this.profile = profile;
    }

    /**
     * Copy Constructor
     */
    public Person(Person copyPerson){
        this.profile = copyPerson.profile;
    }

    /**
     * CompareTo
     */
    @Override
    public int compareTo(Person person){
        return this.profile.compareTo(person.profile);
    }

    /**
     * Equals
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Person person){
            return this.profile.equals(person.profile);
        }
        return false;
    }

    /**
     * toString
     */
    @Override
    public String toString(){
        return this.profile.toString();
    }

}
