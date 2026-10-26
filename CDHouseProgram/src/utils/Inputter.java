
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

            String result = inputString(message);

            if (!result.isEmpty()) {
                return result;
            }

            System.out.println("Input cannot be blank.");
        }
    }

    public int inputInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println("Invalid integer.");
            }
        }
    }

    public double inputDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println("Invalid number.");
            }
        }
    }
}

