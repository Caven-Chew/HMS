package medication;

public class MedicationReplenishment {
    private static final String SEPARATOR = "|";
    private String medicationId;
    private int quantity;
    private String status;

    public MedicationReplenishment(String medicationId, int quantity, String status) {
        this.medicationId = medicationId;
        this.quantity = quantity;
        this.status = status;
    }

    public String getMedicationId() {return medicationId;}

    public void approveStatus() {
        status = "APPROVED";
    }

    public void rejectStatus() {
        status = "REJECTED";
    }

    public String toPrint() {
        return String.format("%s%s%s%s%s",
                medicationId,
                SEPARATOR,
                quantity,
                SEPARATOR,
                status
        );
    }

    public int getQuantity() {return quantity;}
    public String getStatus() {return status;}
}
