cd

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

    @Override
    public String toString(){
        return county + " " + zip;
    }

    /**
     * Constructor
     * @param county
     * @param zip
     */
    Location(String county, String zip){
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

}
