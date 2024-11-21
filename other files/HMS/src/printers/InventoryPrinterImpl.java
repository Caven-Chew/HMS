package printers;

import medication.Medication;

import java.util.List;

public class InventoryPrinterImpl implements InventoryPrinter {
    @Override
    public String inventoryDisplayFormat(List<Medication> medications) {
        StringBuilder res = new StringBuilder(
                "      |Name                |ID        |Stock count   |Alert At  |Is Stock Low\n"
        );
        for ( int i = 0; i< medications.size();i++) {
            res.append(String.format(
                    "%-6d|%-20s|%-10s|%-14s|%-10s|%b\n",
                    i,
                    medications.get(i).getName(),
                    medications.get(i).getMedicationId(),
                    medications.get(i).getStockCount(),
                    medications.get(i).getAlertAt(),
                    medications.get(i).isRaiseAlert()
            ));
        }
        return res.toString();
    }

    @Override
    public String medicationFileFormat(Medication medication) {
        return String.format(
                "%s|%s|%d|%b|%d",
                medication.getMedicationId(),
                medication.getName(),
                medication.getStockCount(),
                medication.isRaiseAlert(),
                medication.getAlertAt()
        );
    }
}
