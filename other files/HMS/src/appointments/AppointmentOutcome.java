package appointments;

import medication.PrescribeMedication;

import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcome {
    private String appointmentId;
    private String date;
    private String time;
    private String patientId;
    private String consulationNotes;
    private List<PrescribeMedication> prescribeMedication;
    private String status;
    private String doctorId;

    public AppointmentOutcome(String appointmentId,
                              String date,
                              String time,
                              String patientId,
                              String consultationNotes,
                              List<PrescribeMedication> prescribeMedication,
                              String status,
                              String doctorId
    ) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.patientId = patientId;
        this.consulationNotes = consultationNotes;
        this.prescribeMedication = prescribeMedication;
        this.status = status;
        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDate() {
        return date;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getConsulationNotes() {
        return consulationNotes;
    }

    public void setConsulationNotes(String consulationNotes) {
        this.consulationNotes = consulationNotes;
    }

    public List<PrescribeMedication> getPrescriptions() {
        return prescribeMedication;
    }

    public void setPrescriptions(List<PrescribeMedication> prescribeMedication) {
        this.prescribeMedication = prescribeMedication;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        boolean newStatus = true;
        for (PrescribeMedication p : prescribeMedication) {
            if (!p.isGiven()) {
                newStatus = false;
                break;
            }
        }
        this.status = newStatus ? "completed" : this.status;
    }

    public String nicePrint() {
        StringBuilder prescribeMedsString = new StringBuilder();
        for (PrescribeMedication p : prescribeMedication) {
            prescribeMedsString.append(String.format("%s:%s:%d:%b", p.getMedicationId(), p.getDosage(), p.getQuantity(), p.isGiven()));
        }

        return String.format("Status:%s \tConsultation notes:%s \tMedications:%s",
                status,
                consulationNotes,
                prescribeMedsString
        );
    }

    public String toPrint() {
        StringBuilder prescribeMedicationString = new StringBuilder();
        if (!prescribeMedication.isEmpty()) {
            for (PrescribeMedication p : prescribeMedication) {
                prescribeMedicationString.append(String.format("%s:%s:%d:%b", p.getMedicationId(), p.getDosage(), p.getQuantity(), p.isGiven()));
            }
        }

        return String.format(
                "%s|%s|%s|%s|%s|%s|%s",
                appointmentId,
                date,
                time,
                patientId,
                consulationNotes,
                prescribeMedicationString,
                status
        );
    }
}
