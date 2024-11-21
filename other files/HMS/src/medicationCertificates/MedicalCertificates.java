package medicationCertificates;

public class MedicalCertificates {
    private String id;
    private String patientName;
    private String patientId;
    private String doctorName;
    private String doctorId;
    private String issueDate;
    private int numOfDays;

    public MedicalCertificates(String id, String patientName, String patientId, String doctorName, String doctorId, String issueDate, int numOfDays) {
        this.id = id;
        this.patientName = patientName;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.issueDate = issueDate;
        this.numOfDays = numOfDays;
    }

    public String getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public int getNumOfDays() {
        return numOfDays;
    }
}
