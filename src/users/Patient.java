package users;

import appointments.AppointmentOutcome;
import appointments.Slot;
import medication.Medication;
import medication.MedicationInventory;
import medication.PrescribeMedication;
import medicationCertificates.MCService;
import medicationCertificates.MedicalCertificates;
import utils.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String dateOfBirth;
    private final String gender;
    private final String bloodType;
    private String email;
    private String phoneNumber;
    private String diagnosis;

    public Patient(String userId, String password, String name, String dateOfBirth, String gender, String bloodType, String email, String phoneNumber, String diagnosis) {
        super(userId, password, name);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.diagnosis = diagnosis;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public boolean login(String userId, String password) {
        return super.login(userId, password);
    }

    @Override
    public void showMainPage() {
        System.out.println("Hi " + getName() + " how are you?");

        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println("Patient Main Page ");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update contact information");
            System.out.println("3. View Available Appointment SLots");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Reschedule Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcomes");
            System.out.println("9. View Appointment History");
            System.out.println("10. View Health Tips");
            System.out.println("11. View MCs");
            System.out.println("12. Logout");

            selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updateContactInfo();
                    break;
                case 3:
                    viewAvailableSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomes();
                    break;
                case 9:
                    viewAppointmentHistory();
                    break;
                case 10:
                    viewHealthTips();
                    break;
                case 11:
                    viewAllMC();
                    break;
                case 12:
                    System.out.println("Logged out");
                    break;
                default:
                    System.out.println("Invalid selection");

            }
        }
        while (selection != 12);

    }

    private void viewMedicalRecord() {

        String filePath = "Patients_List.txt";

        try {
            Patient patient = PatientDB.getPatientByUserId(filePath, getUserId());

            if (patient != null) {

                System.out.println("\nID: " + userId);
                System.out.println("Name: " + name);
                System.out.println("DOB: " + dateOfBirth);
                System.out.println("Gender: " + gender);
                System.out.println("Blood Type: " + bloodType);
                System.out.println("Email: " + email);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Diagnosis: " + diagnosis + "\n");

            } else {
                System.out.println("No record found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContactInfo() {
        Scanner sc = new Scanner(System.in);

//        System.out.println("Select the contact information to update:");
//        System.out.println("1) Email");
//        System.out.println("2) Phone Number");
//        int selection = sc.nextInt();
//        sc.nextLine();


        List<String> choices = Arrays.asList("1", "2");
        String selection = ValidationUtils.promptStringWithEnd("Select the contact information to update: \n1) Email\n2) Phone Number ", "Invalid selection, Please enter 1 or 2", choices);

        if (selection == null) {
            System.out.println("Successful left the page");
            return;
        }

        switch (selection) {
            case "1":
                System.out.println("Enter new email: ");
                String newEmail = sc.nextLine();

                String newEmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if (ValidationUtils.checkValidEmail(newEmail, newEmailRegex)) {
                    try {
                        PatientDB.updatePatientContactInfo("Patients_List.txt", getUserId(), newEmail, null);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else {
                    System.out.println("Invalid email");
                    System.out.println("Please try again, enter new email: ");
                    newEmail = sc.nextLine();

                    if (ValidationUtils.checkValidEmail(newEmail, newEmailRegex)) {
                        try {
                            PatientDB.updatePatientContactInfo("Patients_List.txt", getUserId(), newEmail, null);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    } else {
                        System.out.println("Invalid email entered 2 times in a row, please try again later");
                    }
                }
                break;
            case "2":
                System.out.println("Enter new phone number: ");
                String newPhoneNum = sc.nextLine();

                String newPhoneNumRegex = "^[89]\\d{7}$";
                if (ValidationUtils.checkValidPhoneNumber(newPhoneNum, newPhoneNumRegex)) {
                    try {
                        PatientDB.updatePatientContactInfo("Patients_List.txt", getUserId(), null, newPhoneNum);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else {
                    System.out.println("Invalid phone number");
                    System.out.println("Enter new phone number: ");
                    newPhoneNum = sc.nextLine();

                    if (ValidationUtils.checkValidPhoneNumber(newPhoneNum, newPhoneNumRegex)) {
                        try {
                            PatientDB.updatePatientContactInfo("Patients_List.txt", getUserId(), null, newPhoneNum);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    } else {
                        System.out.println("Invalid phone number entered 2 times in a row, please try again later");
                    }
                }
                break;
            default:
                System.out.println("Invalid selection");
        }

        try {
            Patient updatedPatient = PatientDB.getPatientByUserId("Patients_list.txt", getUserId());
            this.email = updatedPatient.getEmail();
            this.phoneNumber = updatedPatient.getPhoneNumber();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public void viewAvailableSlots() {
        SlotDB slotDB = new SlotDB();
        List<Slot> availableSlots = slotDB.getAvailableSlots();

        if (availableSlots.isEmpty()) {
            System.out.println("No appointment slots available");
        } else {
            System.out.println("Available slots: ");
            int i = 1;
            for (Slot slot : availableSlots) {
                System.out.println(i + ") Doctor: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime());
                i++;
            }
        }
    }

    public void scheduleAppointment() {

        Scanner sc = new Scanner(System.in);

        SlotDB slotDB = new SlotDB();
        List<Slot> availableSlots = slotDB.getAvailableSlots();

        if (availableSlots.isEmpty()) {
            System.out.println("No appointment slots available");
            return;
        }

        System.out.println("Available slots: ");
        for (int i = 0; i < availableSlots.size(); i++) {
            Slot slot = availableSlots.get(i);
            System.out.println((i + 1) + ", Doctor: " + slot.getDoctorName() + ",Date: " + slot.getDate() + ", Time: " + slot.getTime());
        }

        System.out.println("Select/Enter the slot number to schedule appointment: ");
        int selection = sc.nextInt() - 1;

        if (selection >= 0 && selection < availableSlots.size()) {
            Slot slot = availableSlots.get(selection);
            slot.pendingAppointment();
            slot.setPatientId(getUserId());

            try {
                slotDB.updateSlots("AppointmentSlots_List.txt");
                System.out.println("Appointment requested, need to be scheduled/accepted by doctor");
                return;
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Invalid selection");
        }

        System.out.println("Appointment not scheduled");
    }

    public void rescheduleAppointment() {
        SlotDB slotDB = new SlotDB();
        List<Slot> slots = slotDB.getSlots();
        System.out.println("Current confirmed appointments: ");
        int i = 1;
        List<Slot> confirmedAppointments = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getAppointmentStatus().equals("confirmed")) {
                confirmedAppointments.add(slot);
                System.out.println(i + ") Doctor: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime());
                i++;
            }
        }

        if (confirmedAppointments.isEmpty()) {
            System.out.println("No appointments scheduled");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Select/Enter the slot number to reschedule appointment: ");
        int selection = sc.nextInt() - 1;

        if (selection < 0 || selection >= confirmedAppointments.size()) {
            System.out.println("Invalid selection");
            return;
        }

        Slot curSlot = confirmedAppointments.get(selection);
        curSlot.setIsAvailable(true);
        curSlot.setAppointmentStatus("available");
        curSlot.setPatientId("NONE");

        List<Slot> availableSlots = slotDB.getAvailableSlots();

        System.out.println("Available slots: ");
        for (int j = 0; j < availableSlots.size(); j++) {
            Slot slot = availableSlots.get(j);
            System.out.println((j + 1) + ", Doctor: " + slot.getDoctorName() + ",Date: " + slot.getDate() + ", Time: " + slot.getTime());
        }

        System.out.println("Select/Enter the slot number to schedule appointment: ");
        int selection2 = sc.nextInt() - 1;

        if (selection2 < 0 || selection2 >= availableSlots.size()) {
            System.out.println("Invalid selection");
            return;
        }

        Slot newSlot = availableSlots.get(selection2);

        newSlot.pendingAppointment();
        newSlot.setPatientId(getUserId());

        try {
            slotDB.updateSlots("AppointmentSlots_List.txt");
            System.out.println("Appointment requested, need to be scheduled/accepted by doctor");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cancelAppointment() {
        SlotDB slotDB = new SlotDB();
        List<Slot> slots = slotDB.getSlots();

        System.out.println("Current scheduled appointments: ");
        int i = 1;
        List<Slot> confirmedAppointments = new ArrayList<>();
        for (Slot slot : slots) {
            if ((slot.getAppointmentStatus().equals("confirmed") || slot.getAppointmentStatus().equals("pending")) && slot.getPatientId().equals(getUserId())) {
                confirmedAppointments.add(slot);
                System.out.println(i + ") Doctor: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus());
                i++;
            }

        }


        if (confirmedAppointments.isEmpty()) {
            System.out.println("No appointments scheduled");
            return;
        }


        Scanner sc = new Scanner(System.in);
        System.out.println("Select/Enter the slot number to schedule appointment: ");
        int selection3 = sc.nextInt() - 1;

        Slot curSlot = confirmedAppointments.get(selection3);
        curSlot.setIsAvailable(true);
        curSlot.setAppointmentStatus("available");
        curSlot.setPatientId("NONE");

        try {
            slotDB.updateSlots("AppointmentSlots_List.txt");
            System.out.println("Appointment cancelled successfully");
        } catch (Exception e) {
            System.out.println("Cancellation request failed" + e.getMessage());
        }

    }

    public void viewScheduledAppointments() {
        SlotDB slotDB = new SlotDB();
        List<Slot> patientAppts = slotDB.getAppointmentByUserId(getUserId());

        if (patientAppts.isEmpty()) {
            System.out.println("No appointment scheduled");
        } else {
            System.out.println("Appointment scheduled");
            int i = 1;
            for (Slot slot : patientAppts) {
                System.out.println(i + ")  Doctor name: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus());
                i++;
            }
        }

    }


    public void viewPastAppointmentOutcomes() {
        AppointmentOutcomeDB outcomeDB = new AppointmentOutcomeDB();
        List<AppointmentOutcome> outcomes = outcomeDB.getOutcomesByPatientId(getUserId());
        MedicationInventory medicationInventory = new MedicationInventory();
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> medications = medicationDB.getAllMedication();
        if (outcomes.isEmpty()) {
            System.out.println("Appointments not found");
        }
        int i = 1;
        for (AppointmentOutcome outcome : outcomes) {
            StringBuilder prescribeMedicationMsg = new StringBuilder();
            for (PrescribeMedication prescribeMedication : outcome.getPrescriptions()) {
                String medicationName = medicationInventory.getNameById(medications, prescribeMedication.getMedicationId());
                prescribeMedicationMsg.append(String.format("%s", medicationName, prescribeMedication.getQuantity())).append(",");
            }
            prescribeMedicationMsg = new StringBuilder(
                    prescribeMedicationMsg.substring(0, prescribeMedicationMsg.length() - 1)
            );


            String showMessage = String.format(
                    i + ") Doctor Id: %s, Date: %s , Time: %s , Consultation Notes: %s , Prescriptions: %s",
                    outcome.getDoctorId(),
                    outcome.getDate(),
                    outcome.getTime(),
                    outcome.getConsulationNotes(),
                    prescribeMedicationMsg
            );
            System.out.println(showMessage);
            i++;
        }
    }


    public void viewAppointmentHistory() {
        SlotDB slotDB = new SlotDB();
        List<Slot> patientAppts = slotDB.getAppointmentHistoryByUserId(getUserId());

        if (patientAppts.isEmpty()) {
            System.out.println("No past appointments");
        } else {
            System.out.println("Appointment History\n");

            List<Slot> presentAppointments = new ArrayList<>();
            List<Slot> pastAppointments = new ArrayList<>();
            List<Slot> declinedAppointments = new ArrayList<>();

            for (Slot slot : patientAppts) {
                if (slot.getAppointmentStatus().equals("declined")) {
                    declinedAppointments.add(slot);
                } else if (checkAppointment(slot)) {
                    pastAppointments.add(slot);
                } else {
                    presentAppointments.add(slot);
                }
            }


            if (!presentAppointments.isEmpty()) {
                System.out.println("Upcoming/Present Appointments");
                int i = 1;
                for (Slot slot : presentAppointments) {
                    System.out.println(i + ")  Doctor name: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus());
                    i++;
                }
            }


            if (!declinedAppointments.isEmpty()) {
                System.out.println("\nDeclined Appointments");
                int i = 1;
                for (Slot slot : declinedAppointments) {
                    System.out.println(i + ")  Doctor name: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus());
                    i++;
                }
            }


            if (!pastAppointments.isEmpty()) {
                System.out.println("\nPast Appointments");
                int i = 1;
                for (Slot slot : pastAppointments) {
                    System.out.println(i + ")  Doctor name: " + slot.getDoctorName() + ", Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus());
                    i++;
                }
            }
        }

    }


    private boolean checkAppointment(Slot slot) {
        return (slot.getAppointmentStatus().equals("settled"));
    }

    private void viewAllMC() {
        MCService mcService = new MCService();
        List<MedicalCertificates> medicalCertificatesList = mcService.getMedicalCertificates(userId);
        for (MedicalCertificates medicalCertificates : medicalCertificatesList) {
            System.out.println(String.format(
                    "MC for %d days issued on %s",
                    medicalCertificates.getNumOfDays(),
                    medicalCertificates.getIssueDate()
            ));
        }
        if (medicalCertificatesList.isEmpty()) {
            System.out.println("No medical certificates");
        }
    }

    public void viewHealthTips() {
        String healthTip = HealthTipsUtils.getHealthTip(diagnosis);
        System.out.println("Health Tips for your condition of " + diagnosis + ":");
        System.out.println(healthTip);


    }

}