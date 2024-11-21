package users;

import appointments.AppointmentOutcome;
import medication.Medication;
import medication.MedicationInventory;
import medication.PrescribeMedication;
import medicationCertificates.MCService;
import medicationCertificates.MedicalCertificates;
import utils.*;
import appointments.Slot;
import java.util.UUID;

import java.util.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Doctor extends Staff {
    public Doctor(String userId, String password, String name, String role, String gender, int age) {
        super(userId, password, name, role, gender, age);
    }

    @Override
    public boolean login(String userId, String password) {
        return super.login(userId, password);
    }

    @Override
    public void showMainPage() {
        System.out.println("Hello Doctor " + getName() + ", how are you");

        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println("Doc Main Page ");
            System.out.println("1. View Patient Records");
            System.out.println("2. Update Patient Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availablity for Appointments");
            System.out.println("5. Accept or decline appointments");
            System.out.println("6. View upcoming appointments");
            System.out.println("7. Record appointment outcome");
            System.out.println("8. Logout");

            selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    viewPatientRecords();
                    break;
                case 2:
                    updateDiagnosis();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    setAvailabilityForAppointments();
                    break;
                case 5:
                    acceptOrDeclineAppointment();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    System.out.println("Logged out");
                    break;
                default:
                    System.out.println("Invalid selection");

            }
        }
        while (selection != 8);

    }

    private void viewPatientRecords() {
        List<String> patientOfDoctor = new ArrayList<>();

        SlotDB slotDB = new SlotDB();
        List<Slot> slotList = slotDB.getSlots();
        patientOfDoctor = slotDB.getPatientOfDoctor(slotList, userId);

        AppointmentOutcomeDB appointmentOutcomeDB = new AppointmentOutcomeDB();
        List<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeDB.readOutcomes();
        patientOfDoctor.addAll(appointmentOutcomeDB.getPatientsOfDoctor(appointmentOutcomes, userId));

        //remove duplicated
        patientOfDoctor = new ArrayList<>(new HashSet<>(patientOfDoctor));
        String patientId = ValidationUtils.promptStringWithEnd(
                "Enter patient id to view Patient Records.",
                "The id provided is not your patient",
                patientOfDoctor
        );

        if (patientId == null) {
            return;
        }

        try {
            Patient patient = PatientDB.getPatientByUserId("Patients_List.txt", patientId);
            if (patient != null) {
                String userStr = String.format(
                        """
                                Patient Records for %s (%s)
                                DOB: %s
                                Gender: %s
                                Blood type: %s
                                Email: %s
                                Phone Number: %s
                                Diagnosis: %s
                                """,
                        patient.getName(),
                        patient.getUserId(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getBloodType(),
                        patient.getEmail(),
                        patient.getPhoneNumber(),
                        patient.getDiagnosis()
                );
                System.out.println(userStr);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDiagnosis() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the patient id ");
        String patientId = sc.nextLine();
        System.out.println("Please enter new diagnosis or update current diagnosis");

        if (patientId == null) {
            return;
        }

        String newDiagnosis = sc.nextLine();
        try {
            Patient patient = PatientDB.getPatientByUserId("Patients_List.txt", patientId);
            patient.setDiagnosis(newDiagnosis);
            PatientDB.updatePatientDiagnosis("Patients_List.txt", patientId, newDiagnosis);
            System.out.println("Diagnosis has been updated, " + newDiagnosis);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void viewPersonalSchedule() {
        SlotDB slotDB = new SlotDB();
        List<Slot> doctorSlots = slotDB.getSlotsByDoctor(getName());
        if (doctorSlots.isEmpty()) {
            System.out.println("No slot " + getName() + " found");
        } else {
            int i = 1;
            for (Slot slot : doctorSlots) {
                System.out.println(i + ") Date: " + slot.getDate() + " Time: " + slot.getTime() + " PatientId: " + slot.getPatientId() + " Appointment Status:" + slot.getAppointmentStatus());
                i++;
            }
        }

    }

    public void setAvailabilityForAppointments() {
        SlotDB slotDB = new SlotDB();
        Scanner sc = new Scanner(System.in);

        LocalDate localDate = ValidationUtils.promptDate("Set the appointment date(format: DD-MM-YYYY):", "Invalid date.Please enter a valid date from tomorrow onwards");

        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String time = ValidationUtils.promptTime("Set the appointment time(format: HH:MM, 24-hour based):", "Invalid time format, please user HH:MM, 24-hour based format");


        System.out.println("Set the appointment time(format: HH:MM, 24-hour based");

        boolean isAvailable = true;

        Slot newSlot = new Slot(getName(), formattedDate, time, isAvailable, "available", "NONE", userId);
        slotDB.getSlots().add(newSlot);

        try {
            slotDB.addNewSlot(newSlot, "AppointmentSlots_List.txt");
            System.out.println("New slot has been added successfully.");

        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }


    }

    public void acceptOrDeclineAppointment() {
        SlotDB slotDB = new SlotDB();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter patientId for appointment request");
        String patientId = sc.nextLine();

        List<Slot> appoinmments = slotDB.getAllAppointmentByUserId(patientId);

        if (appoinmments.isEmpty()) {
            System.out.println("Invalid patient or no appointments found");
            return;
        }

        List<Slot> pendingAppointments = new ArrayList<>();

        for (Slot slot : appoinmments) {
            if (slot.getAppointmentStatus().equals("pending")) {
                pendingAppointments.add(slot);
            }
        }

        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments found");
        }

        int i = 1;
        System.out.println("Pending appointments:");
        for (Slot slot : pendingAppointments) {

            System.out.println(i + ") Date: " + slot.getDate() + ", Time: " + slot.getTime() + ", Appointment Status: " + slot.getAppointmentStatus() + ", PatientID: " + slot.getPatientId());
            i++;
        }


        System.out.println("Select/Enter the slot number to accept/decline appointment: ");
        int selection = sc.nextInt() - 1;

        sc.nextLine();

        if (selection < 0 || selection >= pendingAppointments.size()) {
            System.out.println("Invalid selection");
            return;
        }

        Slot slot = pendingAppointments.get(selection);


        System.out.println("Do you want to accept or decline appointment?(Please enter 'accept' or 'decline'");
        String response = sc.nextLine().toLowerCase();

        if (response.equals("accept")) {
            slot.setAppointmentStatus("confirmed");
            slot.setIsAvailable(false);
            System.out.println("Appointment confirmed");
        } else if (response.equals("decline")) {
            slot.setAppointmentStatus("declined");
            slot.setIsAvailable(false);
            System.out.println("Appointment declined");
        } else {
            System.out.println("Invalid selection");
        }


        try {
            slotDB.updateSlots("AppointmentSlots_List.txt");
            System.out.println("updated appointment slots successfully");
        } catch (Exception e) {
            System.out.println("Error updating appointment slides" + e.getMessage());
        }


    }

    public void viewUpcomingAppointments() {
        SlotDB slotDB = new SlotDB();
        List<Slot> confirmedAppointments = slotDB.getSlotsByDoctor(getName());
        System.out.println("Upcoming appointments for: " + getName());
        int i = 1;
        for (Slot slot : confirmedAppointments) {
            if ("confirmed".equals(slot.getAppointmentStatus())) {
                System.out.println(i + ") Date: " + slot.getDate() + " Time: " + slot.getTime() + " PatientId: " + slot.getPatientId());
                i++;
            }
        }
    }

    public void recordAppointmentOutcome() {
        SlotDB slotDB = new SlotDB();
        AppointmentOutcomeDB appointmentOutcomeDB = new AppointmentOutcomeDB();
        Scanner sc = new Scanner(System.in);

        List<Slot> appointments = slotDB.getSlots();
        List<Slot> confirmedAppointments = new ArrayList<>();
        boolean gotConfirmedAppts = false;
        int j = 1;

        for (Slot appointment : appointments) {
            if (appointment.getAppointmentStatus().equals("confirmed")) {
                gotConfirmedAppts = true;

                confirmedAppointments.add(appointment);
                System.out.printf("%d) %s | %s | %s | %s |%n",
                        j++,
                        appointment.getAppointmentId(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getPatientId());
            }
        }

        if (!gotConfirmedAppts) {
            System.out.println("No appointments to record down");
            return;
        }


        System.out.print("Enter the index number for the outcome: ");
        String outcomeForStr = sc.nextLine();
        int outcomeFor = Integer.parseInt(outcomeForStr) - 1;

        if (outcomeFor < 0 || outcomeFor >= confirmedAppointments.size()) {
            System.out.println("Invalid selection");
            return;
        }


        System.out.println("Enter outcome info: ");
        String outcomeInfo = sc.nextLine();

        List<String> medicinesWithStatus = new ArrayList<>();

        while (true) {
            System.out.println("Enter each medicine one by one. Type 'END' to finish:");
            String medicine = sc.nextLine();
            if (medicine.equalsIgnoreCase("END")) {
                break;
            } else {
                MedicationInventory medicationInventory = new MedicationInventory();
                MedicationDB medicationDB = new MedicationDB();
                List<Medication> inventoryList = medicationDB.getAllMedication();

                Medication med = medicationInventory.getMedication(inventoryList, medicine);
                if (med == null) {
                    System.out.println("Medication not found");
                } else {
                    System.out.println("Dosage for " + med.getName());
                    String dosage = sc.nextLine();

                    System.out.println("Quantity to be given");
                    int quantity = Integer.parseInt(sc.nextLine());

                    PrescribeMedication prescribeMedication = new PrescribeMedication(
                            med.getMedicationId(),
                            dosage,
                            false,
                            quantity
                    );
                    medicinesWithStatus.add(String.format("%s:%s:%d:%b", prescribeMedication.getMedicationId(), prescribeMedication.getDosage(), prescribeMedication.getQuantity(), prescribeMedication.isGiven()));
                }
            }
        }

        String medicines = String.join(",", medicinesWithStatus);

        String status = "Pending medications";
        if (medicinesWithStatus.isEmpty()) {
            status = "completed";
        }

        appointmentOutcomeDB.addOutcome(appointments.get(outcomeFor), outcomeInfo, medicines, status);
        appointments.get(outcomeFor).setAppointmentStatus("settled");

        // add mc
        List<String> validInput = new ArrayList<>(List.of("YES", "Yes", "yes", "NO", "No", "no"));
        String needMc = ValidationUtils.promptString(
                "Do the patient need MC? Yes/No",
                "Only 'Yes' or 'No' allowed",
                validInput
        ).toLowerCase();

        if (needMc.equals("yes")) {
            addMc(appointments.get(outcomeFor));
        }

        try {
            slotDB.updateSlots("AppointmentSlots_List.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addMc(Slot slot) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String name = PatientDB.getPatientByUserId("Patients_List.txt", slot.getPatientId()).getName();

            MCService mcService = new MCService();
            String id = UUID.randomUUID().toString();


            String issueDate = ValidationUtils.promptDate1(
                    "Enter issue date in dd/mm/yyyy (eg.01/01/2000)",
                    "Can only be in dd/mm/yyyy format"
            ).format(formatter);

            int numOfDays = ValidationUtils.promptInt(
                    "Number of days on MC?",
                    "Only positive int are allowed",
                    true
            );

            MedicalCertificates medicalCertificates = new MedicalCertificates(
                    id,
                    name,
                    slot.getPatientId(),
                    slot.getDoctorName(),
                    slot.getDoctorId(),
                    issueDate,
                    numOfDays
            );


            mcService.addMC(medicalCertificates);
            System.out.println("MC added successfully!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }

}
