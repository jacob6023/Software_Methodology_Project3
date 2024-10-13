package Project_1.src.project;

/**
 * This enum class is the provider that takes the appointment and contains enums Location and Specialty as its values.
 *
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

    // Getters
    public Location getLocation() {return location;}
    public Specialty getSpecialty() {return specialty;}

    /**
     * Parameterized Constructor to create the provider enum.
     *
     * @param location the location of the provider.
     * @param specialty the specialty of the provider.
     */
    Provider(Location location, Specialty specialty) {
        this.location = location;
        this.specialty = specialty;
    }

    /**
     * Output the provider.
     *
     * @return String in format "[provider name, provider location, provider location county, provider location zip, provider speciality]".
     */
    @Override
    public String toString() {
        return  "[" + this.name() + ", " + this.location.name() + ", " + this.location.toString() + ", " + this.specialty.name() + "]"  ;
       // return location.toString() + specialty.name();
    }

}


