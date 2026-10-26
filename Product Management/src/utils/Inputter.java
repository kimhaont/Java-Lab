package utils;

import java.util.Scanner;

public class Inputter {

    private Scanner scanner;

    public Inputter() {
        scanner = new Scanner(System.in);
    }

    public String inputString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    public String inputNonBlank(String message) {

        while (true) {

            String value = inputString(message);

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be blank.");
        }
    }

    public int inputInt(String message, int min, int max) {

        while (true) {

            try {

                String input = inputString(message);

                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println(
                        "Value must be from " + min + " to " + max + "."
                );

            } catch (NumberFormatException e) {

                System.out.println("Please enter an integer.");
            }
        }
    }

    public double inputDouble(String message,
                              double min,
                              double max) {

        while (true) {

            try {

                String input = inputString(message);

                double value = Double.parseDouble(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println(
                        "Value must be from " + min + " to " + max + "."
                );

            } catch (NumberFormatException e) {

                System.out.println("Please enter a real number.");
            }
        }
    }
}