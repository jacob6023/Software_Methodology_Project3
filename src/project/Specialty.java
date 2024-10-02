package Project_1.src.project;
/* this enum class defines the providers' specialties with an additional property "charge", which defines the charge per visit*/
/**
 * @author Jack Crosby
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    @Override
    public String toString(){
        return String.valueOf(this.charge);
    }

    /**
     * Constructor
     * @param charge
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Getter
     * @return
     */
    public int getCharge() {
        return charge;
    }
}
