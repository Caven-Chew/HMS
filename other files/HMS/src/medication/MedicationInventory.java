package medication;

import java.util.List;

public class MedicationInventory {
    public Medication getMedicationById(List<Medication> medications, String Id) {
        for (Medication medication : medications) {
            if (Id.equals(medication.medicationId)) {
                return medication;
            }
        }
        return null;
    }

    public void replenishMedication(List<Medication> medications, String Id, int quantity) {
        getMedicationById(medications, Id).updateStock(quantity);
    }

    public void dispenseMedication(List<Medication> medications, String Id, int quantity) {
        getMedicationById(medications, Id).updateStock(-quantity);
    }

    public void changeAlertValue(List<Medication> medications, int index, int quantity) {
        medications.get(index).changeStockAlert(quantity);
    }

    public String getNameById(List<Medication> medications, String id) {
        for (Medication m : medications) {
            if (m.getMedicationId().equals(id)) {
                return m.name;
            }
        }
        return null;
    }

    public Medication getMedication(List<Medication> medications, String medicationName) {
        for (Medication medication : medications) {
            if (medication.getName().equals(medicationName)) {
                return medication;
            }
        }
        return null;
    }
}
