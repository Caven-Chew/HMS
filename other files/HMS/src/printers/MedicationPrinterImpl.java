package printers;

import medication.Medication;

public class MedicationPrinterImpl implements MedicationPrinter {
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
