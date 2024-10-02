package Project_1.src.project;

/**
 * @author Jack Crosby
 * this enum class defines the city locations with two addition properties listed below
 */
public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    PISCATAWAY("Middlesex", "08854"),
    EDISON("Middlesex", "08817"),
    PRINCETON("Mercer County", "08542"),
    MORRISTOWN("Morris County", "07960"),
    CLARK("07066", "Union County");

    private final String county;
    private final String zip;

    /**
     * setters
     * @param county
     * @param zip
     */
    private Location(String county, String zip){
        this.county = county;
        this.zip = zip;
    }

    /**
     * getters
     * @return
     */
    public String getCounty() {
        return county;
    }

    public String getZip() {
        return zip;
    }

    /**
     *
     * @param county
     * @param zip
     * @return location based on county and zip
     */
    public static Location getLocation(String county, String zip) {
        for (Location location : Location.values()) {
            if (location.county.equalsIgnoreCase(county) && location.zip.equals(zip)) {
                return location;
            }
        }
        return null;
    }
}
