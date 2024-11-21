package medication;

public class Medication implements IMedication {
    protected String medicationId;
    protected String name;
    protected int stockCount;
    protected boolean raiseAlert;
    protected int alertAt;

    public Medication(String medicationId, String name, int stockCount, int alertAt) {
        this.medicationId = medicationId;
        this.name = name;
        this.stockCount = stockCount;
        this.alertAt = alertAt;
        checkAlert();
    }

    @Override
    public String getMedicationId() {
        return medicationId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStockCount() {
        return stockCount;
    }

    @Override
    public boolean isRaiseAlert() {
        return raiseAlert;
    }

    @Override
    public int getAlertAt() {
        return alertAt;
    }

    public void checkAlert() {
        raiseAlert = stockCount < alertAt;
    }

    public void updateStock(int quantity) {
        stockCount += quantity;
        checkAlert();
    }

    public void changeStockAlert(int newValue) {
        alertAt = newValue;
    }

}


