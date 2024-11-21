package medication;

public class PrescribeMedication {
    private String medicationId;
    private String dosage;
    private int quantity;
    private boolean isGiven;


    public PrescribeMedication(String medicationId, String dosage, boolean isGiven, int quantity) {
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.isGiven = isGiven;
        this.quantity = quantity;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public String getDosage() {
        return dosage;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setGiven(boolean given) {
        isGiven = given;
    }

}