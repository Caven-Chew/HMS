package medicationCertificates;

import utils.MedicalCertificatesDB;

import java.util.ArrayList;
import java.util.List;

public class MCService {
    public void addMC(MedicalCertificates medicalCertificates) {
        MedicalCertificatesDB medicalCertificatesDB = new MedicalCertificatesDB();
        List<MedicalCertificates> medicalCertificatesList = medicalCertificatesDB.getMedicalCertificates();
        medicalCertificatesList.add(medicalCertificates);
        medicalCertificatesDB.addMedicalCertificate(medicalCertificatesList);
    }

    public List<MedicalCertificates> getMedicalCertificates(String patientId) {
        List<MedicalCertificates> result = new ArrayList<>();
        MedicalCertificatesDB medicalCertificatesDB = new MedicalCertificatesDB();
        List<MedicalCertificates> medicalCertificatesList = medicalCertificatesDB.getMedicalCertificates();
        for (MedicalCertificates medicalCertificate : medicalCertificatesList) {
            if (medicalCertificate.getPatientId().equals(patientId)) {
                result.add(medicalCertificate);
            }
        }
        return result;
    }
}
