package Project_1.src.project;

/**
 * This enum class defines the city locations with properties county and zip code.
 *
 * @author Jack Crosby
 */
public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    PISCATAWAY("Middlesex", "08854"),
    EDISON("Middlesex", "08817"),
    PRINCETON("Mercer County", "08542"),
    MORRISTOWN("Morris County", "07960"),
    CLARK("07066", "Union County");

    // Instance Variables
    private final String county;
    private final String zip;

    // Getters
    public String getCounty() {
        return county;
    }

    public String getZip() {
        return zip;
    }

    /**
     * Enum Constructor.
     *
     * @param county the location's county.
     * @param zip the location's zip code.
     */
    Location(String county, String zip){
        this.county = county;
        this.zip = zip;
    }

    /**
     * Output location in format "City, county zip".
     *
     * @return String formatted in values count + zip. Typically, had location.name() before calling this.
     */
    @Override
    public String toString(){
        return this.name() + ", " + county + " " + zip;
    }

    /**
     * Compare the location.
     *
     * @param location of another appointment.
     * @return in declaration order: -1 if before, 0 if same, 1 if after.
     */
    public int compareLocation(Location location){
        int countyComparison = this.county.compareTo(location.getCounty());
        if(countyComparison != 0){
            return countyComparison;
        }
        return this.zip.compareTo(location.getZip());
    }

    /**
     * Determine if the location provides a Radiology imaging service.
     *
     * @param service the radiology enum.
     * @return true if the location provides a radiology imaging service, false otherwise.
     */
    public boolean hasRadiologyService(Radiology service) {
        return switch (this) {
            case BRIDGEWATER, PISCATAWAY ->
                    service == Radiology.CATSCAN || service == Radiology.ULTRASOUND || service == Radiology.XRAY;
            default -> false;
        };
    }

}
