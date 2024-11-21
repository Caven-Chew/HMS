package utils;

import appointments.Slot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int promptInt(String message, String invalidMessage) {
        int validInteger;

        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();

            try {
                validInteger = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            }
        }
        return validInteger;
    }

    public static int promptInt(String message, String invalidMessage, boolean onlyPositive) {
        int validInteger;

        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();

            try {
                validInteger = Integer.parseInt(input);

                if (onlyPositive && validInteger > 0) {
                    break;
                }

                else if (!onlyPositive && validInteger < 0) {
                    break;
                } else {
                    System.out.println(invalidMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            }
        }
        return validInteger;
    }

    public static int promptInt(String message, String invalidMessage, List<Integer> validInts) {
        int validInteger;

        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();

            try {
                validInteger = Integer.parseInt(input);

                if (validInts.contains(validInteger)) {
                    break;
                } else {
                    System.out.println(invalidMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            }
        }
        return validInteger;
    }

    public static int promptIntWithEnd(String message, String invalidMessage, List<Integer> validInts) {
        Integer validInteger;

        while (true) {
            System.out.println(message+ "\nEnter 'END' to exit.");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("END")) {
                return Integer.MAX_VALUE;
            }

            try {
                validInteger = Integer.parseInt(input);

                if (validInts.contains(validInteger)) {
                    break;
                } else {
                    System.out.println(invalidMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            }
        }
        return validInteger;
    }


    public static String promptString(String message, String invalidMessage) {
        String input;

        while (true) {
            System.out.println(message);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                break;
            } else {
                System.out.println(invalidMessage);
            }
        }
        return input;
    }

    public static String promptString(String message, String invalidMessage, List<String> validStrings) {
        String input;

        while (true) {
            System.out.println(message);
            input = scanner.nextLine().trim();

            if (!input.isEmpty() && validStrings.contains(input)) {
                break;
            } else {
                System.out.println(invalidMessage);
            }
        }
        return input;
    }

    public static String promptStringWithEnd(String message, String invalidMessage, List<String> validStrings) {
        String input;

        while (true) {
            System.out.println(message + "\nEnter 'END' to escape");
            input = scanner.nextLine().trim();


            if (input.equalsIgnoreCase("END")) {
                return null;
            }


            if (!input.isEmpty() && validStrings.contains(input)) {
                break;
            } else {
                System.out.println(invalidMessage);
            }
        }
        return input;
    }

    public static boolean checkValidEmail(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean checkValidPhoneNumber(String phoneNumber, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(phoneNumber)
                .matches();
    }

    public static LocalDate promptDate(String message, String invalidMessage) {
        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = todayDate.plusDays(1);
        String date;
        while(true) {
            System.out.println(message);
            date = scanner.nextLine().trim();

            try{
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate parsedDate = LocalDate.parse(date, dateTimeFormatter);

                if(!parsedDate.isBefore(tomorrowDate)) {
                    return parsedDate;
                }
                else{
                    System.out.println(invalidMessage);
                }
            }
            catch (DateTimeParseException e) {
                System.out.println(invalidMessage);
            }

        }
    }

    public static LocalDate promptDate1(String message, String invalidMessage) {
        String date;
        while (true) {
            System.out.println(message);
            date = scanner.nextLine().trim();

            try {
                // Use the dd/MM/yyyy format
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate parsedDate = LocalDate.parse(date, dateTimeFormatter);

                // Return the parsed date if it is valid
                return parsedDate;
            } catch (DateTimeParseException e) {
                // Invalid date format
                System.out.println(invalidMessage);
            }
        }
    }

    public static String promptTime(String message, String invalidMessage) {
        String time;
        while (true) {
            System.out.println(message);
            time = scanner.nextLine().trim();

            if(time.matches("([01]\\d|2[0-3]):[0-5]\\d")){
                return time;
            }
            else{
                System.out.println(invalidMessage);
            }
        }
    }


}
