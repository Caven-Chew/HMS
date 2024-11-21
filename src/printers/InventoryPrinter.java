package printers;

import medication.Medication;

import java.util.List;

public interface InventoryPrinter extends MedicationPrinter{
    String inventoryDisplayFormat(List<Medication> medications);
}
