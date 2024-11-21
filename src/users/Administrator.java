package users;

import java.util.*;

import appointments.AppointmentOutcome;
import appointments.Slot;
import medication.Medication;
import medication.MedicationInventory;
import medication.MedicationReplenishment;
import printers.InventoryPrinterImpl;
import utils.*;

public class Administrator extends Staff {
    public Administrator(String userId, String password, String name, String role, String gender, int age) {
        super(userId, password, name, role, gender, age);
    }

    @Override
    public void showMainPage() {
        String administrator_menu = String.format("""
                
                Hello admin, %s, how are you?
                Administrator Main page
                1. View Staff
                2. Add Staff
                3. Update Staff
                4. Remove Staff
                5. View Appointments
                6. View Inventory
                7. Change Alert Value
                8. Approve Replenish Request
                9. Reject Replenish Request
                10. Add New Medication
                99. Logout
                """, getName());

        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println(administrator_menu);
            selection = sc.nextInt();
            sc.nextLine();

            switch (selection) {
                case 1:
                    System.out.println("View Staff");
                    viewStaff();
                    break;
                case 2:
                    System.out.println("Add Staff");
                    addStaff();
                    break;
                case 3:
                    System.out.println("Update Staff");
                    updateStaff();
                    break;
                case 4:
                    System.out.println("Remove Staff");
                    removeStaff();
                    break;
                case 5:
                    System.out.println("View Appointments");
                    viewAppointments();
                    break;
                case 6:
                    System.out.println("View Inventory");
                    viewInventory();
                    break;
                case 7:
                    System.out.println("Change Alert Value");
                    changeAlertVal();
                    break;
                case 8:
                    System.out.println("Approve Replenish Request");
                    approveReplenishRequest();
                    break;
                case 9:
                    System.out.println("Reject Replenish Request");
                    rejectReplenishRequest();
                    break;
                case 10:
                    System.out.println("Add New Medication");
                    addNewMedication();
                    break;
                case 99:
                    System.out.println("Logging out...");
                    break;
            }
        }
        while (selection != 99);
    }

    public void viewStaff() {
        String promptMsg = """
                Show order by
                    1) Name
                    2) Role
                    3) Gender
                    4) Age
                    5) Id
                """;
        int userInput = ValidationUtils.promptInt(
                promptMsg,
                "Only options 1-5 are available.",
                List.of(1, 2, 3, 4, 5)
        );

        List<Staff> staffs = StaffDB.getAllStaffs();

        switch (userInput) {
            case 1 -> {
                System.out.println("Order by name");
                Collections.sort(staffs, Comparator.comparing(Staff::getName, String.CASE_INSENSITIVE_ORDER));
            }
            case 2 -> {
                System.out.println("Order by role");
                Collections.sort(staffs, Comparator.comparing(Staff::getRole, String.CASE_INSENSITIVE_ORDER));
            }
            case 3 -> {
                System.out.println("Order by gender");
                Collections.sort(staffs, Comparator.comparing(Staff::getGender, String.CASE_INSENSITIVE_ORDER));
            }
            case 4 -> {
                System.out.println("Order by age");
                Collections.sort(staffs, Comparator.comparingInt(Staff::getAge));
            }
            case 5 -> {
                System.out.println("Order by id");
                Collections.sort(staffs, Comparator.comparing(Staff::getUserId, String.CASE_INSENSITIVE_ORDER));
            }
        }

        if (staffs.isEmpty()) {
            System.out.println("There is no staff in the database");
        } else {
            System.out.println("ID       Name                 Role            Gender     Age");
            for (Staff staff : staffs) {
                System.out.printf("%-8s %-20s %-15s %-10s %-5d\n",
                        staff.getUserId(),
                        staff.getName(),
                        staff.getRole(),
                        staff.getGender(),
                        staff.getAge());
            }
        }
    }

    public void addStaff() {
        Scanner sc = new Scanner(System.in);
        String role, id, password, name, gender, age;

        System.out.println("Adding staff into the database");

        while (true) {
            System.out.println("Select Role:");
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            System.out.print("Enter choice (1-3): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    role = "Doctor";
                    break;
                case "2":
                    role = "Pharmacist";
                    break;
                case "3":
                    role = "Administrator";
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    continue;
            }
            break;
        }

        System.out.println("Enter new ID?");
        id = sc.nextLine();

        password = "password";

        System.out.println("Enter new Name?");
        name = sc.nextLine();

        while (true) {
            System.out.println("Select Gender:");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.print("Enter choice (1-2): ");

            String genderChoice = sc.nextLine().trim();
            switch (genderChoice) {
                case "1":
                    gender = "Male";
                    break;
                case "2":
                    gender = "Female";
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    continue;
            }
            break;
        }

        System.out.println("Enter new Age?");
        age = sc.nextLine();

        String staffStr = String.format("%s|%s|%s|%s|%s|%s", id, password, name, role, gender, age);
        StaffDB.addStaff(staffStr);
    }

    public void updateStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter StaffId to update");
        String id = sc.nextLine();

        try {
            List<Staff> staffs = StaffDB.getAllStaffs();
            for (Staff staff : staffs) {
                if (staff.getUserId().equals(id)) {
                    while (true) {
                        System.out.println("What to update?");
                        System.out.println("1. Password");
                        System.out.println("2. Name");
                        System.out.print("Enter choice (1-2): ");

                        String editChoice = sc.nextLine().trim();
                        switch (editChoice) {
                            case "1":
                                System.out.println("Change password");
                                System.out.println("What is the new password?");
                                String newPassword = sc.nextLine();
                                staff.setPassword(newPassword);
                                break;
                            case "2":
                                System.out.println("Change name");
                                System.out.println("What is the new name?");
                                String newName = sc.nextLine();
                                staff.setName(newName);
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter 1 or 2.");
                                continue;
                        }
                        break;
                    }
                    StaffDB.overwriteStaffDB(staffs);
                    System.out.println("Update Successfully!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to remove");
            throw new RuntimeException(e);
        }


    }

    public void removeStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter StaffId to remove");
        String id = sc.nextLine();

        List<Staff> staffs = StaffDB.getAllStaffs();
        if (staffs.isEmpty()) {
            System.out.println("There is no staff in the database");
        } else {
            boolean found = false;
            for (int i = 0; i < staffs.size(); i++) {
                if (staffs.get(i).getUserId().equals(id)) {
                    found = true;
                    System.out.println("Removing staff: " + staffs.get(i).getName() +
                            "(" + staffs.get(i).getUserId() + ")" + " from the database");
                    staffs.remove(i);
                    StaffDB.overwriteStaffDB(staffs);
                    break;
                }
            }
            if (!found) {
                System.out.println("There is no staff in the database");
            }
        }
    }

    public void viewAppointments() {
        SlotDB slotDB = new SlotDB();
        AppointmentOutcomeDB appointmentOutcomeDB = new AppointmentOutcomeDB();
        List<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeDB.readOutcomes();

        List<Slot> appointmentSlots = slotDB.getSlots();
        for (Slot appointment : appointmentSlots) {
            System.out.print(appointment.nicePrint());

            boolean found = false;
            for (AppointmentOutcome outcome : appointmentOutcomes) {
                if (outcome.getAppointmentId().equals(appointment.getAppointmentId())) {
                    System.out.print("\tAppointment out record:<" + outcome.nicePrint() + ">");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.print("\tAppointment out record:<NIL>");
            }
            System.out.println();
        }
    }

    public void viewInventory() {
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> medications = medicationDB.getAllMedication();
        InventoryPrinterImpl inventoryPrinter = new InventoryPrinterImpl();
        System.out.println(inventoryPrinter.inventoryDisplayFormat(medications));
    }

    public void changeAlertVal() {
        MedicationDB medicationDB = new MedicationDB();
        List<Medication> medications = medicationDB.getAllMedication();
        MedicationInventory medicationInventory = new MedicationInventory();
        List<Integer> validInputs = new ArrayList<>();
        for (int i = 0; i < medications.size(); i++) {
            validInputs.add(i + 1);
            String res = String.format(
                    "%-2d)  Name:%s   Alert limit:%d",
                    i + 1,
                    medications.get(i).getName(),
                    medications.get(i).getAlertAt()
            );
            System.out.println(res);
        }

        if (!validInputs.isEmpty()) {
            int medicationIndex = ValidationUtils.promptInt(
                    "Enter index of medication to change alert limit",
                    "Only index shown is allowed",
                    validInputs
            ) - 1;

            int newQuantity = ValidationUtils.promptInt("Enter new alert limit", "Only positive integer are allowed", true);
            medicationInventory.changeAlertValue(medications, medicationIndex, newQuantity);

            for (Medication medication : medications) {
                medication.checkAlert();
            }

            MedicationDB.updateDB(medications);

            System.out.println("Alert limit have been changed.");
        } else {
            System.out.println("No medication in inventory.");
        }
    }

    public void approveReplenishRequest() {
        MedicationReplenishmentDB medicationReplenishmentDB = new MedicationReplenishmentDB();
        MedicationDB medicationDB = new MedicationDB();
        List<MedicationReplenishment> medicationReplenishments = medicationReplenishmentDB.readReplenishmentRequest();
        List<Medication> medications = medicationDB.getAllMedication();
        MedicationInventory medicationInventory = new MedicationInventory();

        List<Integer> validInputs = new ArrayList<>();
        List<MedicationReplenishment>[] splitList = split_on_status(medicationReplenishments);

        for (int i = 0; i < splitList[1].size(); i++) {
            MedicationReplenishment medicationReplenishment = splitList[1].get(i);
            String printMessage = String.format(
                    " %s) Replenish request for %d %s\n",
                    i + 1,
                    medicationReplenishment.getQuantity(),
                    medicationInventory.getNameById(medications, medicationReplenishment.getMedicationId())
            );
            System.out.print(printMessage);
            validInputs.add(i + 1);
        }

        if (splitList[1].isEmpty()) {
            System.out.println("There is no medication replenishment request");
        } else {
            int userInpt = ValidationUtils.promptIntWithEnd(
                    "Enter index of replenish request to approve",
                    "Only index show above is available",
                    validInputs
            ) - 1;

            if (userInpt == Integer.MAX_VALUE - 1) {
                System.out.println("Exiting approve replenishment request.");
            } else {
                splitList[1].get(userInpt).approveStatus();

                medicationInventory.replenishMedication(
                        medications,
                        splitList[1].get(userInpt).getMedicationId(),
                        splitList[1].get(userInpt).getQuantity()
                );
                medicationReplenishmentDB.updateReplenishmentDB(medicationReplenishments);
                MedicationDB.updateDB(medications);
                System.out.println("Replenish request is APPROVED.");
            }

        }
    }

    public void rejectReplenishRequest() {
        MedicationReplenishmentDB medicationReplenishmentDB = new MedicationReplenishmentDB();
        MedicationDB medicationDB = new MedicationDB();
        List<MedicationReplenishment> medicationReplenishments = medicationReplenishmentDB.readReplenishmentRequest();
        List<Medication> medications = medicationDB.getAllMedication();
        MedicationInventory medicationInventory = new MedicationInventory();

        List<Integer> validInputs = new ArrayList<>();
        List<MedicationReplenishment>[] splitList = split_on_status(medicationReplenishments);

        for (int i = 0; i < splitList[1].size(); i++) {
            MedicationReplenishment medicationReplenishment = splitList[1].get(i);
            String printMessage = String.format(
                    " %s) Replenish request for %d %s\n",
                    i + 1,
                    medicationReplenishment.getQuantity(),
                    medicationInventory.getNameById(medications, medicationReplenishment.getMedicationId())
            );
            System.out.print(printMessage);
            validInputs.add(i + 1);
        }

        if (splitList[1].isEmpty()) {
            System.out.println("There is no medication replenishment request");
        } else {
            int userInpt = ValidationUtils.promptIntWithEnd(
                    "Enter index of replenish request to reject.",
                    "Only index show above is available",
                    validInputs
            ) - 1;

            if (userInpt == Integer.MAX_VALUE - 1) {
                System.out.println("Exiting reject replenishment request.");
            } else {
                splitList[1].get(userInpt).rejectStatus();
                System.out.println("Replenish request is REJECTED.");
            }
        }
    }

    public void addNewMedication() {
        MedicationDB medicationDB = new MedicationDB();

        String newMedicationId = ValidationUtils.promptString(
                "Enter Id of medication.",
                "This field cannot be empty"
        );

        String newMedicationName = ValidationUtils.promptString(
                "Enter name of medication.",
                "This field cannot be empty"
        );

        int newMedicationAlertAt = ValidationUtils.promptInt(
                "Enter value to raise alert at.",
                "Only positive integers are allowed!",
                true
        );

        Medication newMedication = new Medication(newMedicationId, newMedicationName, 0, newMedicationAlertAt);
        List<Medication> medicationList = medicationDB.getAllMedication();
        medicationList.add(newMedication);
        MedicationDB.updateDB(medicationList);
        System.out.println("New medication added");
    }

    private List<MedicationReplenishment>[] split_on_status(List<MedicationReplenishment> inptList) {
        List<MedicationReplenishment>[] res = new List[2];
        res[0] = new ArrayList<>();
        res[1] = new ArrayList<>();

        for (MedicationReplenishment med : inptList) {
            if (Objects.equals(med.getStatus(), "APPROVED") || Objects.equals(med.getStatus(), "REJECTED")) {
                res[0].add(med);
            } else {
                res[1].add(med);
            }
        }
        return res;
    }
}
