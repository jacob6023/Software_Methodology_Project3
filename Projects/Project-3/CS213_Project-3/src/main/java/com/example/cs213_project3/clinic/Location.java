package com.example.cs213_project3.clinic;

/**
 * This enum class defines the city locations with properties county and zip code.
 *
 * @author Jack Crosby
 */
public enum Location {
    /**
     * Enum values for the locations.
     */
    BRIDGEWATER("Somerset", "08807"),
    PISCATAWAY("Middlesex", "08854"),
    EDISON("Middlesex", "08817"),
    PRINCETON("Mercer County", "08542"),
    MORRISTOWN("Morris County", "07960"),
    CLARK("Union County ", "07066");

    /**
     * The location's county.
     */
    private final String county;

    /**
     * The location's zip code.
     */
    private final String zip;

    /**
     * Get the location's county.
     *
     * @return the location's county.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Get the location's zip code.
     *
     * @return the location's zip code.
     */
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
