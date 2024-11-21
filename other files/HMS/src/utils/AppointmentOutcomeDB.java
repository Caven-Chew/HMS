package utils;

import appointments.AppointmentOutcome;
import appointments.Slot;
import medication.PrescribeMedication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeDB {
    private static final String FILENAME = "AppointmentOutcomes_List.txt";
    private static final String SEPARATOR = "\\|";

    public List<AppointmentOutcome> readOutcomes() {
        List<String> readFromTXT = FileUtils.read(FILENAME);
        List<AppointmentOutcome> outcomes = new ArrayList<>();

        for (String line : readFromTXT) {
            String[] lineInfo = line.split(SEPARATOR);

            String appointmentID = lineInfo[0];
            String date = lineInfo[1];
            String time = lineInfo[2];
            String patientId = lineInfo[3];
            String consultationNotes = lineInfo[4];
            String status = lineInfo[6];
            String doctorID = lineInfo[7];

            List<PrescribeMedication> prescribeMedicationList = new ArrayList<>();

            if (!lineInfo[5].isEmpty()) {
                String[] prescriptionTexts = lineInfo[5].split(",");

                for (String prescriptionField : prescriptionTexts) {
                    String[] eachPrescriptionField = prescriptionField.split(":");

                    String medicationId = eachPrescriptionField[0];
                    String dosage = eachPrescriptionField[1];
                    int quantity = Integer.parseInt(eachPrescriptionField[2]);
                    boolean isGiven = Boolean.parseBoolean(eachPrescriptionField[3]);

                    prescribeMedicationList.add(new PrescribeMedication(
                            medicationId, dosage, isGiven, quantity
                    ));
                }
            }

            AppointmentOutcome outcome = new AppointmentOutcome(
                    appointmentID, date, time, patientId, consultationNotes, prescribeMedicationList, status, doctorID
            );

            outcomes.add(outcome);
        }
        return outcomes;
    }

    public List<String> getPatientsOfDoctor(List<AppointmentOutcome> outcomes, String doctorId) {
        List<String> patientIds = new ArrayList<>();

        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getDoctorId().equals(doctorId)) {
                patientIds.add(outcome.getPatientId());
            }
        }

        return patientIds;
    }

    public void addOutcome(Slot slot, String consultationNotes, String medicine, String status) {
        String newline = String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                slot.getAppointmentId(),
                slot.getDate(),
                slot.getTime(),
                slot.getPatientId(),
                consultationNotes,
                medicine,
                status,
                slot.getDoctorId()
        );

        FileUtils.write(FILENAME, newline);
    }

    public List<AppointmentOutcome> getOutcomesByPatientId(String patientId) {
        List<AppointmentOutcome> outcomes = readOutcomes();
        List<AppointmentOutcome> res = new ArrayList<>();

        for (AppointmentOutcome outcome : outcomes) {
            if (outcome.getPatientId().equals(patientId)) {
                res.add(outcome);
            }
        }
        return res;
    }

    public void updateAppointmentOutcome(List<AppointmentOutcome> outcomes) {
        List<String> outData = new ArrayList<>();
        for (AppointmentOutcome outcome : outcomes) {
            StringBuilder prescribeMedicationStr = new StringBuilder();
            for (PrescribeMedication pm : outcome.getPrescriptions()) {
                prescribeMedicationStr.append(String.format("%s:%s:%d:%b", pm.getMedicationId(), pm.getDosage(), pm.getQuantity(), pm.isGiven())).append(",");
            }

            if (!prescribeMedicationStr.isEmpty()) {
                prescribeMedicationStr.setLength(prescribeMedicationStr.length() - 1);
            }

            String newline = String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                    outcome.getAppointmentId(),
                    outcome.getDate(),
                    outcome.getTime(),
                    outcome.getPatientId(),
                    outcome.getConsulationNotes(),
                    prescribeMedicationStr,
                    outcome.getStatus(),
                    outcome.getDoctorId());
            outData.add(newline);
        }
        FileUtils.overwrite(FILENAME, outData);
    }
}
