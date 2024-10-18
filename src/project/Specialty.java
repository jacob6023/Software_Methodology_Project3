package Project_1.src.project;

/**
 *  This enum class defines the providers' specialties with an additional property "charge", which defines the charge per visit.
 *
 * @author Jack Crosby
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    // Getter
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
