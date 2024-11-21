package utils;

import users.Staff;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class StaffDB {
    private static final String SEPARATOR = "\\|";
    private static final String FILENAME = "Staffs_List.txt";

    public static List<Staff> getAllStaffs() {
        List<String> readFromTXT = FileUtils.read(FILENAME);
        List<Staff> staffList = new ArrayList<>();

        for (String line : readFromTXT) {
            String[] lineInfo = line.split(SEPARATOR);
            String userId = lineInfo[0];
            String password = lineInfo[1];
            String name = lineInfo[2];
            String role = lineInfo[3];
            String gender = lineInfo[4];
            int age = Integer.parseInt(lineInfo[5]);
            Staff staff = new Staff(userId, password, name, role, gender, age);
            staffList.add(staff);
        }
        return staffList;
    }

//    public static List<Staff> getAllStaffs() {
//        List<String> readFromTXT = FileUtils.read(FILENAME);
//        List<Staff> staffs = new ArrayList<>();
//        for (String line : readFromTXT) {
//            String[] lineInfo = line.split(SEPARATOR);
//            String userId = lineInfo[0];
//            String password = lineInfo[1];
//            String name = lineInfo[2];
//            String role = lineInfo[3];
//            String gender = lineInfo[4];
//            int age = Integer.parseInt(lineInfo[5]);
//            Staff staff = new Staff(userId, password, name, role, gender, age);
//            staffs.add(staff);
//        }
//        return staffs;
//
////        // read String from text file
////        ArrayList stringArray = (ArrayList) read(filename);
////        // to store Staffs data
////        ArrayList slr = new ArrayList();
////
////        for (int i = 0; i < stringArray.size(); i++) {
////            String st = (String) stringArray.get(i);
////            // get individual 'fields' of the string separated by SEPARATOR
////            // pass in the string to the string tokenizer using delimiter
////            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
////
////            String userId = star.nextToken().trim();    // first token
////            String password = star.nextToken().trim();    // second token
////            String name = star.nextToken().trim();    // third token
////            String role = star.nextToken().trim();    // forth token
////            String gender = star.nextToken().trim();    // fifth token
////            int age = Integer.parseInt(star.nextToken().trim());    // sixth token
////            // create staff object from file data
////            Staff staff = new Staff(userId, password, name, role, gender, age);
////            // add to Staffs list
////            slr.add(staff);
////        }
////        return slr;
//    }


    public static Staff validateStaffCredentials(List<Staff> staffList, String userRole, String userId, String password) {
        for (Staff staff : staffList) {
            if (
                    staff.getUserId().equals(userId) &&
                            staff.getPassword().equals(password)
            ) {
                return staff;
            }
        }

        return null;
    }

//    public static Staff getStaffInfo(String filename, String userId, String password) throws IOException {
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Delimiter is |
//                String[] tokens = line.split("\\|");
//
//                // Checks whether it matches
//                if (tokens[0].equals(userId) && tokens[1].equals(password)) {
//                    int age = Integer.parseInt(tokens[5].trim());
//                    return new Staff(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], age);
//                }
//            }
//        }
//        // No match found
//        return null;
//    }

    public static void addStaff(String data) {
        FileUtils.write(FILENAME, data);
    }

    public static void overwriteStaffDB(List<Staff> staffList) {
        List<String> staffs = new ArrayList<>();
        for (Staff staff : staffList) {
            staffs.add(staff.toPrint());
        }
        FileUtils.overwrite(FILENAME, staffs);
    }

//    public static void main(String[] aArgs) {
//        //StaffDB staffDB = new StaffDB();
//        String filename = "Staffs_List.txt";
//        try {
//            // read file containing Patients records.
//            List<Staff> staffs =  StaffDB.getAllStaffs();
//            for (Staff staff : staffs) {
//                System.out.println("Id " + staff.getUserId());
//                System.out.println("Password " + staff.getPassword());
//                System.out.println("Name " + staff.getName());
//                System.out.println("Role " + staff.getRole());
//                System.out.println("Gender " + staff.getGender());
//                System.out.println("Age " + staff.getAge());
//            }
//
//        } catch (IOException e) {
//            System.out.println("IOException > " + e.getMessage());
//        }
//    }
}
