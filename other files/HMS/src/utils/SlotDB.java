package utils;

import appointments.Slot;
import users.Staff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SlotDB {
    private List<Slot> slots = new ArrayList<>();
    public static final String SEPARATOR = "|";
    private static final String FILE_NAME = "AppointmentSlots_List.txt";

    public SlotDB() {
        try {
            showSlots();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSlots() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Delimiter is |
                String[] info = line.split("\\|");

                if (info.length < 7) {
                    System.out.println(line);
                    continue;
                }
                String appointmentID = info[0].trim();
                String doctorName = info[1].trim();
                String date = info[2].trim();
                String time = info[3].trim();
                boolean isAvailable = Boolean.parseBoolean(info[4].trim());
                String appointmentStatus = info[5].trim();
                String patientId = info[6].trim();
                String doctorId = (info.length > 7) ? info[7].trim() : "";
                slots.add(new Slot(
                        appointmentID,
                        doctorName,
                        date,
                        time,
                        isAvailable,
                        appointmentStatus,
                        patientId,
                        doctorId
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getPatientOfDoctor(List<Slot> slotList, String doctorId) {
        List<String> patients = new ArrayList<>();
        for (Slot slot : slotList) {
            if (slot.getDoctorId().equals(doctorId)) {
                patients.add(slot.getPatientId());
            }
        }
        return patients;
    }

    public List<Slot> getAvailableSlots() {
        List<Slot> availableSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.isAvailable()) {
                availableSlots.add(slot);
            }
        }

        return availableSlots;

    }

    public List<Slot> getSlotsByDoctor(String doctorName) {
        List<Slot> slotsByDoctor = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getDoctorName().equals(doctorName) && !slot.getAppointmentStatus().equals("settled")) {
                slotsByDoctor.add(slot);
            }
        }

        return slotsByDoctor;
    }

    public void updateSlots(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Slot slot : slots) {
                String line = String.join(SEPARATOR, slot.getAppointmentId(), slot.getDoctorName(), slot.getDate(), slot.getTime(), String.valueOf(slot.isAvailable()), slot.getAppointmentStatus(), slot.getPatientId(), slot.getDoctorId());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public List<Slot> getSlots() {
        return new ArrayList<>(slots);
    }

    public List<Slot> getAppointmentByUserId(String userId) {
        List<Slot> userAppointments = new ArrayList<>();
        if (slots.isEmpty()) {
            return userAppointments;
        }
        for (Slot slot : slots) {
            if (slot.getPatientId().equals(userId) && (slot.getAppointmentStatus().equals("confirmed") || slot.getAppointmentStatus().equals("pending"))) {
                userAppointments.add(slot);
            }
        }
        return userAppointments;
    }

    public List<Slot> getAppointmentHistoryByUserId(String userId) {
        List<Slot> userAppointments = new ArrayList<>();
        if (slots.isEmpty()) {
            return userAppointments;
        }
        for (Slot slot : slots) {
            if (slot.getPatientId().equals(userId)) {
                userAppointments.add(slot);
            }
        }
        return userAppointments;
    }

    public List<Slot> getAllAppointmentByUserId(String userId) {
        List<Slot> userAppointments = new ArrayList<>();
        if (slots.isEmpty()) {
            return userAppointments;
        }
        for (Slot slot : slots) {
            if (slot.getPatientId().equals(userId)) {
                userAppointments.add(slot);
            }
        }
        return userAppointments;
    }

    public void addNewSlot(Slot slot, String filename) throws IOException {
        slots.add(slot);
        updateSlots(filename);

    }


//    public void showAvailableSlots() {
//        System.out.println("Available Appointment slots:");
//        for (Slot slot : slots) {
//            if(slot.isAvailable()) {
//                System.out.println(slot);
//            }
//        }
//    }

}
