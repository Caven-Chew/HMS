package users;

import appointments.AppointmentOutcome;
import medication.Medication;
import medication.MedicationInventory;
import medication.MedicationReplenishment;
import medication.PrescribeMedication;
import printers.InventoryPrinterImpl;
import utils.AppointmentOutcomeDB;
import utils.MedicationDB;
import utils.MedicationReplenishmentDB;
import utils.ValidationUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends Staff {
    public Pharmacist(String userId, String password, String name, String role, String gender, int age) {
        super(userId, password, name, role, gender, age);
    }

    @Override
    public void showMainPage() {
        String pharmacist_menu = String.format("""
                
                Hello pharmacist %s, how are you?
                Pharmacist main page
                1. View Medication in Outcome Record
                2. Update Prescription Status
                3. Monitor Medication Inventory
                4. Submit Replenish Request
                99. logout
                """, getName());

        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println(pharmacist_menu);
            selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    System.out.println("View Medication in Outcome Record");
                    viewMedicationInOutcomeRecord();
                    break;
                case 2:
                    System.out.println("Update Prescription Status");
                    updatePrescriptionStatus();
                    break;
                case 3:
                    System.out.println("Monitor Medication Inventory");
                    monitorMedicationInventory();
                    break;
                case 4:
                    System.out.println("Submit Replenish Request");
                    submitReplenishRequest();
                    break;
                case 99:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        while (selection != 99);
    }

    public ArrayList<Integer> viewMedicationInOutcomeRecord() {
        AppointmentOutcomeDB appointmentOutcomeDB = new AppointmentOutcomeDB();
        ArrayList<Integer> validInputs = new ArrayList<>();
        MedicationInventory medicationInventory = new MedicationInventory();
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> medications = medicationDB.getAllMedication();
//        try {
        List<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeDB.readOutcomes();

        int printCounter = 0;
        for (int i = 0; i < appointmentOutcomes.size(); i++) {
            AppointmentOutcome appointmentOutcome = appointmentOutcomes.get(i);
            if (appointmentOutcome.getStatus().equals("completed")) {
                printCounter++;
                continue;
            }
            validInputs.add(i + 1);
            StringBuilder toShow = new StringBuilder();
            toShow.append(i + 1).append(") ");
            toShow.append(appointmentOutcome.getAppointmentId()).append(" ");
            toShow.append(appointmentOutcome.getPatientId()).append(" ");
            toShow.append(appointmentOutcome.getStatus()).append(" ");
            toShow.append("\n\tMeds:\n");

            for (PrescribeMedication prescribeMedication : appointmentOutcome.getPrescriptions()) {
                String medStatus = "Pending";
                if (prescribeMedication.isGiven()) {
                    medStatus = "Given";
                }
                toShow.append(String.format("\t%s-%d-%s-%s\n",
                        medStatus,
                        prescribeMedication.getQuantity(),
                        medicationInventory.getNameById(medications, prescribeMedication.getMedicationId()),
                        prescribeMedication.getDosage()
                ));
            }
            System.out.println(toShow);
        }

        if (printCounter == appointmentOutcomes.size()) {
            System.out.println("No pending medications");
        }

//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        return validInputs;
    }

    public void updatePrescriptionStatus() {
        AppointmentOutcomeDB appointmentOutcomeDB = new AppointmentOutcomeDB();
        List<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeDB.readOutcomes();
        MedicationInventory medicationInventory = new MedicationInventory();
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> medications = medicationDB.getAllMedication();

        ArrayList<Integer> validInputs = viewMedicationInOutcomeRecord();

        if (!validInputs.isEmpty()) {
            int inputId = ValidationUtils.promptInt(
                    "Enter index of appointment outcome to dispense",
                    "You have entered an invalid input",
                    validInputs
            ) - 1;

            AppointmentOutcome appointmentOutcome = appointmentOutcomes.get(inputId);

            List<PrescribeMedication> completedPrescribeMedications = new LinkedList<>();
            ArrayList<Integer> validInputs2 = new ArrayList<>();
            System.out.println("To prepare");
            for (int i = 0; i < appointmentOutcome.getPrescriptions().size(); i++) {
                PrescribeMedication prescribeMedication = appointmentOutcome.getPrescriptions().get(i);
                if (prescribeMedication.isGiven()) {
                    completedPrescribeMedications.add(prescribeMedication);
                } else {
                    validInputs2.add(i + 1);
                    System.out.printf("%d) %s - %d%n",
                            i + 1,
                            medicationInventory.getNameById(medications, prescribeMedication.getMedicationId()),
                            prescribeMedication.getQuantity()
                    );
                }
            }

            System.out.println("Dispensed medication");
            for (PrescribeMedication prescribeMedication : completedPrescribeMedications) {
                System.out.printf("%s - %d%n",
                        medicationInventory.getNameById(medications, prescribeMedication.getMedicationId()),
                        prescribeMedication.getQuantity()
                );
            }

            int inp = ValidationUtils.promptInt(
                    "Enter index of medication once dispensed",
                    "You have entered a invalid int",
                    validInputs2
            ) - 1;

            appointmentOutcome.getPrescriptions().get(inp).setGiven(true);
            appointmentOutcome.setStatus();

            medicationInventory.dispenseMedication(
                    medications,
                    appointmentOutcome.getPrescriptions().get(inp).getMedicationId(),
                    appointmentOutcome.getPrescriptions().get(inp).getQuantity()
            );
            MedicationDB.updateDB(medications);
            appointmentOutcomeDB.updateAppointmentOutcome(appointmentOutcomes);
//        viewMedicationInOutcomeRecord();
        }
    }

    public void monitorMedicationInventory() {
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> inventoryList = medicationDB.getAllMedication();
        InventoryPrinterImpl inventoryPrinter = new InventoryPrinterImpl();
        System.out.println(inventoryPrinter.inventoryDisplayFormat(inventoryList));
//        for (Medication medication : inventoryList) {
//            System.out.println(String.format(
//                    "id: %s \t name:%-13s \tstock count:%d \tis stock low:%b \talert at:%d",
//                    medication.getMedicationId(),
//                    medication.getName(),
//                    medication.getStockCount(),
//                    medication.isRaiseAlert(),
//                    medication.getAlertAt()
//            ));
//        }
    }

    public void submitReplenishRequest() {
        MedicationDB medicationDB = new MedicationDB();

        MedicationReplenishmentDB medicationReplenishmentDB = new MedicationReplenishmentDB();
        List<Medication> inventoryList = medicationDB.getAllMedication();
        List<Integer> validInputs = new ArrayList<>();

        for (int i = 0; i < inventoryList.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, inventoryList.get(i).getName());
            validInputs.add(i + 1);
        }

        int userInput = ValidationUtils.promptInt(
                "Enter the index to add medication to replenish.",
                "Only index available in inventory are allowed",
                validInputs) - 1;

        int quautity = ValidationUtils.promptInt(
                "Enter quantity to replenish",
                "Only positive integer are allowed!",
                true);
        Medication m = inventoryList.get(userInput);

        MedicationReplenishment medicationReplenishment = new MedicationReplenishment(
                m.getMedicationId(),
                quautity,
                "SUBMITTED");

        medicationReplenishmentDB.addReplenishmentRequest(medicationReplenishment);
        System.out.printf("Medication replenishment for %s of %s is submitted successfully, pending approval.\n",
                quautity,
                m.getName()
        );
    }
}
