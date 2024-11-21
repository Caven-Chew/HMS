package appointments;

import java.util.UUID;

public class Slot {
    private String appointmentId;
    private String doctorName;
    private String date;
    private String time;
    private boolean isAvailable;
    private String appointmentStatus;
    private String patientId;
    private String doctorId;

    public Slot(
            String doctorName,
            String date,
            String time,
            boolean isAvailable,
            String appointmentStatus,
            String patientId,
            String doctorId
    ) {
        this.appointmentId = UUID.randomUUID().toString();
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.isAvailable = isAvailable;
        this.appointmentStatus = isAvailable ? "available" : appointmentStatus;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public Slot(
            String appointmentId,
            String doctorName,
            String date,
            String time,
            boolean isAvailable,
            String appointmentStatus,
            String patientId,
            String doctorId
    ) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.isAvailable = isAvailable;
        this.appointmentStatus = isAvailable ? "available" : appointmentStatus;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorId(){ return doctorId; }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public void confirmAppointment() {
        if (isAvailable) {
            isAvailable = false;
            appointmentStatus = "confirmed";
        }
    }

    public void pendingAppointment() {
        if (isAvailable) {
            isAvailable = false;
            appointmentStatus = "pending";
        }
    }

    public String nicePrint() {
        return String.format(
                "Patient Id:%s \tDoctor Id:%s \tAppointment Status:%s \tDatetime:%s ",
                patientId,
                doctorId,
                appointmentStatus,
                date + time
        );
    }

    public String toString() {
        return doctorName + ", " + date + ", " + time + ", " + isAvailable + ", " + appointmentStatus;
    }


}
