package utils;

import medicationCertificates.MedicalCertificates;

import java.util.ArrayList;
import java.util.List;

public class MedicalCertificatesDB {
    private final String filePath = "MedicalCertificates.txt";

    public List<MedicalCertificates> getMedicalCertificates() {
        List<String> readLines = FileUtils.read(filePath);
        List <MedicalCertificates> medicalCertificates = new ArrayList<>();
        for (String line : readLines) {
            String[] tokens = line.split("\\|");
            MedicalCertificates medicalCertificate = new MedicalCertificates(
                    tokens[0],
                    tokens[1],
                    tokens[2],
                    tokens[3],
                    tokens[4],
                    tokens[5],
                    Integer.parseInt(tokens[6])
            );
            medicalCertificates.add(medicalCertificate);
        }
        return medicalCertificates;
    }

    public void addMedicalCertificate(List <MedicalCertificates> medicalCertificates) {
        List<String> writeLines = new ArrayList<>();
        for (MedicalCertificates medicalCertificate : medicalCertificates) {
            writeLines.add(String.join(
                    "|",
                    medicalCertificate.getId(),
                    medicalCertificate.getPatientName(),
                    medicalCertificate.getPatientId(),
                    medicalCertificate.getDoctorName(),
                    medicalCertificate.getDoctorId(),
                    medicalCertificate.getIssueDate(),
                    Integer.toString(medicalCertificate.getNumOfDays())
            ));
        }
        FileUtils.overwrite(filePath, writeLines);
    }

    public MedicalCertificates getMedicalCertificate(List <MedicalCertificates> medicalCertificates, String id) {
        for (MedicalCertificates medicalCertificate : medicalCertificates) {
            if (medicalCertificate.getId().equals(id)) {
                return medicalCertificate;
            }
        }
        return null;
    }
}
