import java.util.List;
import java.util.Scanner;

import users.*;
import utils.PatientDB;
import utils.StaffDB;
import utils.ValidationUtils;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            System.out.println();
            System.out.println("Welcome to HMS! ");
            System.out.println("Please select an option ");
            System.out.println("1. Login as Patient ");
            System.out.println("2. Login as Doc ");
            System.out.println("3. Login as Pharm ");
            System.out.println("4. Login as Admin ");
            System.out.println("5. Exit");

            selection = sc.nextInt();
            sc.nextLine();


            switch (selection) {
                case 1:
                    // login
                    System.out.println("Please enter your userId : ");
                    String userId = sc.nextLine();
                    System.out.println("Please enter your password : ");
                    String password = sc.nextLine();

                    // validation
                    try {
                        boolean isValid = PatientDB.validatePatientCredentials("Patients_List.txt", userId, password);
                        if (isValid) {
                            List<Patient> patientList = PatientDB.readPatients("Patients_List.txt");

                            Patient patient = getPatient(patientList, userId, password);

                            if (patient != null) {
                                System.out.println("Login successful");
                                if (password.equals("password")) {
                                    String newPassword = ValidationUtils.promptString(
                                            "You will need to change password",
                                            "Password cannot be empty"
                                    );
                                    patient.setPassword(newPassword);
                                    PatientDB.writeToDB("Patients_List.txt", patientList);
                                }
                                patient.showMainPage();
                            } else {
                                System.out.println("Patient not found");
                            }
                        } else {
                            System.out.println("Login failed");
                        }
                    } catch (Exception e) {
                        System.out.println("fail reading");
                    }
                    break;
                case 2:
                    // login
                    System.out.println("Please enter your docId : ");
                    String docId = sc.nextLine();
                    System.out.println("Please enter your password : ");
                    String docPassword = sc.nextLine();

                    // validation
                    List<Staff> docStaffList = StaffDB.getAllStaffs();
                    Staff loginDoctorStaff = StaffDB.validateStaffCredentials(docStaffList, "Doctor", docId, docPassword);
                    if (loginDoctorStaff == null) {
                        System.out.println("Login Fail");
                    } else {
                        System.out.println("Login successful");
                        if (loginDoctorStaff.getPassword().equals("password")) {
                            String newPassword = ValidationUtils.promptString(
                                    "You will need to change password",
                                    "Password cannot be empty"
                            );
                            loginDoctorStaff.setPassword(newPassword);
                            StaffDB.overwriteStaffDB(docStaffList);
                        }

                        Doctor doctor = new Doctor(
                                loginDoctorStaff.getUserId(),
                                loginDoctorStaff.getPassword(),
                                loginDoctorStaff.getName(),
                                loginDoctorStaff.getRole(),
                                loginDoctorStaff.getGender(),
                                loginDoctorStaff.getAge()
                        );
                        doctor.showMainPage();
                    }
                    break;
                case 3:
                    // login
                    System.out.println("Please enter your pharmId : ");
                    String pharmId = sc.nextLine();
                    System.out.println("Please enter your password : ");
                    String pharmPassword = sc.nextLine();

                    // validation
                    List<Staff> pharmStaffList = StaffDB.getAllStaffs();
                    Staff loginPharmStaff = StaffDB.validateStaffCredentials(pharmStaffList, "Pharmacist", pharmId, pharmPassword);
                    if (loginPharmStaff == null) {
                        System.out.println("Login Fail");
                    } else {
                        System.out.println("Login successful");
                        if (loginPharmStaff.getPassword().equals("password")) {
                            String newPassword = ValidationUtils.promptString(
                                    "You will need to change password",
                                    "Password cannot be empty"
                            );
                            loginPharmStaff.setPassword(newPassword);
                            StaffDB.overwriteStaffDB(pharmStaffList);
                        }

                        Pharmacist pharmacist = new Pharmacist(
                                loginPharmStaff.getUserId(),
                                loginPharmStaff.getPassword(),
                                loginPharmStaff.getName(),
                                loginPharmStaff.getRole(),
                                loginPharmStaff.getGender(),
                                loginPharmStaff.getAge()
                        );
                        pharmacist.showMainPage();
                    }
                    break;
                case 4:
                    // login
                    System.out.println("Please enter your adminId : ");
                    String adminId = sc.nextLine();
                    System.out.println("Please enter your password : ");
                    String adminPassword = sc.nextLine();

                    // validation
                    List<Staff> adminStaffList = StaffDB.getAllStaffs();
                    Staff loginAdminStaff = StaffDB.validateStaffCredentials(adminStaffList, "Pharmacist", adminId, adminPassword);
                    if (loginAdminStaff == null) {
                        System.out.println("Login Fail");
                    } else {
                        System.out.println("Login successful");
                        if (loginAdminStaff.getPassword().equals("password")) {
                            String newPassword = ValidationUtils.promptString(
                                    "You will need to change password",
                                    "Password cannot be empty"
                            );
                            loginAdminStaff.setPassword(newPassword);
                            StaffDB.overwriteStaffDB(adminStaffList);
                        }

                        Administrator administrator = new Administrator(
                                loginAdminStaff.getUserId(),
                                loginAdminStaff.getPassword(),
                                loginAdminStaff.getName(),
                                loginAdminStaff.getRole(),
                                loginAdminStaff.getGender(),
                                loginAdminStaff.getAge()
                        );
                        administrator.showMainPage();
                    }
                    break;
                case 5:
                    System.out.println("Exited");
                    break;
                default:
                    System.out.println("Invalid selection");


            }
        }
        while (selection != 5);


    }

    private static Patient getPatient(List<Patient> patientList, String patientId, String password) {
        for (Patient p : patientList) {
            if (p.getUserId().equals(patientId) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }
}