package utils;

import medication.MedicationReplenishment;

import java.util.ArrayList;
import java.util.List;

public class MedicationReplenishmentDB {
    private static final String SEPARATOR = "\\|";
    private static final String FILENAME = "MedicationsReplenish.txt";

//    private List<MedicationReplenishment> medicationReplenishments;
//
//    public MedicationReplenishmentDB() {
//        loadDB();
//    }
//
//    private void loadDB() {
//        this.medicationReplenishments = readReplenishmentRequest();
//    }

    public List<MedicationReplenishment> readReplenishmentRequest() {
        List <MedicationReplenishment> replenishments = new ArrayList<>();
        List <String> readTXTFile = FileUtils.read(FILENAME);
        for (String line : readTXTFile) {
            String[] eachTXTLine = line.split(SEPARATOR);
            MedicationReplenishment mr = new MedicationReplenishment(
                    eachTXTLine[0],
                    Integer.parseInt(eachTXTLine[1]),
                    eachTXTLine[2]
            );
            replenishments.add(mr);
        }
        return replenishments;
    }

    public void addReplenishmentRequest (MedicationReplenishment mr) {
        FileUtils.write(FILENAME, mr.toPrint());
    }

    public void updateReplenishmentDB(List<MedicationReplenishment> listReplenishment) {
        List <String> ls = new ArrayList<>();
        for (MedicationReplenishment medicationReplenishments: listReplenishment) {
            ls.add(medicationReplenishments.toPrint());
        }
        FileUtils.overwrite(FILENAME, ls);
    }
}
