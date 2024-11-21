package utils;

import appointments.Slot;
import users.Patient;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PatientDB {
    public static final String SEPARATOR = "|";

    public static ArrayList readPatients(String filename) throws IOException {
        ArrayList stringArray = (ArrayList)read(filename);
        ArrayList plr = new ArrayList() ;

        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);

            String  userId = star.nextToken().trim();
            String  password = star.nextToken().trim();
            String  name = star.nextToken().trim();
            String  dateOfBirth = star.nextToken().trim();
            String  gender = star.nextToken().trim();
            String  bloodType = star.nextToken().trim();
            String  email = star.nextToken().trim();
            String  phone = star.nextToken().trim();
            String diagnosis = star.nextToken().trim();
            Patient patient = new Patient(userId, password, name, dateOfBirth, gender, bloodType, email, phone, diagnosis);

            plr.add(patient) ;
        }
        return plr ;
    }

    public static void savePatients(String filename, List al) throws IOException {
        List plw = new ArrayList() ;

        for (int i = 0 ; i < al.size() ; i++) {
            Patient patient = (Patient)al.get(i);
            StringBuilder st =  new StringBuilder() ;
            st.append(patient.getUserId().trim());
            st.append(SEPARATOR);
            st.append(patient.getPassword().trim());
            st.append(SEPARATOR);
            st.append(patient.getName().trim());
            st.append(SEPARATOR);
            st.append(patient.getDateOfBirth().trim());
            st.append(SEPARATOR);
            st.append(patient.getGender().trim());
            st.append(SEPARATOR);
            st.append(patient.getBloodType().trim());
            st.append(SEPARATOR);
            st.append(patient.getEmail().trim());
            st.append(SEPARATOR);
            st.append(patient.getPhoneNumber().trim());
            st.append(SEPARATOR);
            st.append(patient.getDiagnosis().trim());
            plw.add(st.toString()) ;
        }
        write(filename,plw);
    }

    public static boolean validatePatientCredentials(String filename, String userId, String password) throws IOException {
        List<Patient> patients = readPatients(filename);

        for (Patient patient : patients) {
            if (patient.getUserId().equals(userId) && patient.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static Patient getPatientByUserId(String filename, String patientId) throws IOException {
        Patient patient = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens[0].equals(patientId)) {
                    patient = new Patient(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return patient;

    }


    public static void updatePatientContactInfo(String filename, String userId, String newEmail, String phoneNumber) throws IOException {
        List<Patient> patients = readPatients(filename);

        for (Patient patient : patients) {
            if (patient.getUserId().equals(userId)) {
                if(newEmail != null) {
                    patient.setEmail(newEmail);
                    System.out.println("Email has been updated.");
                }
                if(phoneNumber != null) {
                    patient.setPhoneNumber(phoneNumber);
                    System.out.println("Phone number has been updated.");
                }
                break;
            }
        }

        savePatients(filename, patients);

    }

    public static void updatePatientDiagnosis(String filename, String userId, String newDiagnosis) throws IOException {
        List<Patient> patients = readPatients(filename);

        for (Patient patient : patients) {
            if (patient.getUserId().equals(userId)) {
                patient.setDiagnosis(newDiagnosis);
                break;
            }
        }

        savePatients(filename, patients);

    }


    public static void write(String fileName, List data) throws IOException  {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        try {
            for (int i =0; i < data.size() ; i++) {
                out.println((String)data.get(i));
            }
        }
        finally {
            out.close();
        }
    }

    public static void writeToDB(String fileName, List<Patient> patientList) {
        List <String> result = new ArrayList();
        for (Patient patient : patientList) {
            String eachPatient = String.format(
                    "%s|%s|%s|%s|%s|%s|%s|%s|%s",
                    patient.getUserId(),
                    patient.getPassword(),
                    patient.getName(),
                    patient.getDateOfBirth(),
                    patient.getGender(),
                    patient.getBloodType(),
                    patient.getEmail(),
                    patient.getPhoneNumber(),
                    patient.getDiagnosis()
            );
            result.add(eachPatient);
        }
        FileUtils.overwrite(fileName, result);
    }


    public static List read(String fileName) throws IOException {
        List data = new ArrayList() ;
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }
        finally{
            scanner.close();
        }
        return data;
    }

    public static void main(String[] aArgs)  {
        PatientDB patientDB = new PatientDB();
        String filename = "Patient_List.txt" ;
        try {
            ArrayList pl = PatientDB.readPatients(filename) ;
            for (int i = 0 ; i < pl.size() ; i++) {
                Patient patient = (Patient)pl.get(i);
                System.out.println("Id " + patient.getUserId() );
                System.out.println("Password " + patient.getPassword() );
                System.out.println("Name " + patient.getName() );
                System.out.println("Date of Birth " + patient.getDateOfBirth() );
                System.out.println("Gender " + patient.getGender() );
                System.out.println("BloodType " + patient.getBloodType() );
                System.out.println("Contact info " + patient.getEmail() + patient.getPhoneNumber() );
                System.out.println("Diagnosis " + patient.getDiagnosis());
            }

        }catch (IOException e) {
            System.out.println("IOException > " + e.getMessage());
        }
    }
}
