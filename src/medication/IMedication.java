package medication;

public interface IMedication {
    String getMedicationId();
    String getName();
    int getStockCount();
    boolean isRaiseAlert();
    int getAlertAt();

}
