package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    public static void write(String fileName, String data) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(data);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void overwrite(String fileName, List<String> data) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (String line : data) {
                out.println(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static List<String> read(String fileName) {
        List<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return data;
    }
}
