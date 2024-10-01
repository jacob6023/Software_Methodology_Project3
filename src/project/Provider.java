package Project_1.src.project;

/**
 * @author Jack Crosby
 */
public enum Provider {
    PATEL(Location.BRIDGEWATER, Speciality.FAMILY),
    LIM(Location.BRIDGEWATER, Speciality.PEDIATRICIAN),
    ZIMNES(Location.CLARK, Speciality.FAMILY),
    HARPER(Location.CLARK, Speciality.FAMILY),
    KAUR(Location.PRINCETON, Speciality.ALLERGIST),
    TAYLOR(Location.PISCATAWAY, Speciality.PEDIATRICIAN),
    RAMESH(Location.MORRISTOWN, Speciality.ALLERGIST),
    CERAVOLO(Location.EDISON, Speciality.PEDIATRICIAN);

    private final Location location;
    private final Speciality speciality;

    // Constructor
    Provider(Location location, Speciality speciality) {
        this.location = location;
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return ;
    }

    // Getter for location
    public Location getLocation() {
        return location;
    }

    // Getter for speciality
    public Speciality getSpeciality() {
        return speciality;
    }
}


