package com.example.project3.clinic;

/**
 *  This enum class defines the providers' specialties with an additional property "charge", which defines the charge per visit.
 *
 * @author Jack Crosby
 */
public enum Specialty {
    /**
     * Enum values for the specialties of the providers.
     */
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    /**
     * The charge associated with the enum value.
     */
    private final int charge;

    /**
     * Get the charge of the enum value.
     *
     * @return the charge of the enum value.
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Parameterized Constructor to create Specialty.
     *
     * @param charge the charge of the enum value it's associated with.
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Output the charge.
     *
     * @return String value of the charge associated with the enum name.
     */
    @Override
    public String toString(){
        return String.valueOf(this.charge);
    }

}
