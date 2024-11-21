package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import medication.Medication;
import printers.MedicationPrinterImpl;

public class MedicationDB {
    private static final String SEPARATOR = "\\|";
    private static final String FILENAME = "medications.txt";

    public Medication readMedication(String inputStr) {
        String[] splitStr = inputStr.split(SEPARATOR);
        return new Medication(
                splitStr[0],
                splitStr[1],
                Integer.parseInt(splitStr[2]),
                Integer.parseInt(splitStr[4])
        );
    }

    public List<Medication> getAllMedication() {
        List<Medication> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(readMedication(line));
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
        return result;
    }

    public static void updateDB(List<Medication> medications) {
        MedicationPrinterImpl medicationPrinterImpl = new MedicationPrinterImpl();
        List<String> data = new ArrayList<>();
        for (Medication medication : medications) {
            data.add(medicationPrinterImpl.medicationFileFormat(medication));
        }
        FileUtils.overwrite(FILENAME, data);
    }
}
