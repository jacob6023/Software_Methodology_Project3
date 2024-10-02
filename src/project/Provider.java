package Project_1.src.project;

/**
 * @author Jack Crosby
 */
public enum Provider {
    PATEL(Location.BRIDGEWATER, Specialty.FAMILY),
    LIM(Location.BRIDGEWATER, Specialty.PEDIATRICIAN),
    ZIMNES(Location.CLARK, Specialty.FAMILY),
    HARPER(Location.CLARK, Specialty.FAMILY),
    KAUR(Location.PRINCETON, Specialty.ALLERGIST),
    TAYLOR(Location.PISCATAWAY, Specialty.PEDIATRICIAN),
    RAMESH(Location.MORRISTOWN, Specialty.ALLERGIST),
    CERAVOLO(Location.EDISON, Specialty.PEDIATRICIAN);

    private final Location location;
    private final Specialty specialty;

    /**
     * Converts the location and speciality value of the enum toString
     * @return
     */
    @Override
    public String toString() {
        return  "[" + this.name() + ", " + this.location.name() + ", " + this.location.toString() + ", " + this.specialty.name() + "]"  ;
       // return location.toString() + specialty.name();
    }

    /**
     * Constructor
     * @param location
     * @param specialty
     */
    Provider(Location location, Specialty specialty) {
        this.location = location;
        this.specialty = specialty;
    }

    /**
     * Getters
     * @return
     */
    public Location getLocation() {return location;}
    public Specialty getSpecialty() {return specialty;}
}


