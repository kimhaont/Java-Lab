package tools;

import java.util.Scanner;
import business.CustomerManager;
import business.FeastMenuManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Customer;
import model.Order;

/**
 * Handles user input (strings, integers, doubles) with validation.
 *
 * @author phamnguyenhoanganh
 */
public class Inputter {

    private final Scanner input;
    private final CustomerManager customerManager;
    private final FeastMenuManager feastMenuManager;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     *
     * @param customerService
     * @param feastMenuService
     */
    public Inputter(CustomerManager customerManager, FeastMenuManager feastMenuManager) {
        this.input = new Scanner(System.in);
        this.customerManager = customerManager;
        this.feastMenuManager = feastMenuManager;
    }

    /**
     * Prompts and returns a string input.
     *
     * @param prompt
     * @return
     */
    public String getString(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    /**
     * Parses a string into an integer with validation.
     *
     * @param value
     * @return
     */
   private int parseInt(String value) {
    try {
        int result = Integer.parseInt(value);
        if (result > 0) {
            return result;
        }
        System.out.println("Value must be greater than 0.");
    } catch (NumberFormatException e) {
        System.out.println("Invalid number format.");
    }
    return -1;  
}


    /**
     * Parses a string into a future date.
     *
     * @param value
     * @return
     */
    private LocalDate parseDate(String value) {
    try {
        LocalDate date = LocalDate.parse(value, DATE_FORMATTER);
        if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) { 
            return date;
        }
        System.out.println("Event date must be today or in the future!");
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Use dd/MM/yyyy.");
    }
    return null;
}



    /**
     * Prompts and returns a valid integer input.
     *
     * @param prompt
     * @return
     */
    public int getInt(String prompt) {
        int result;
        do {
            result = parseInt(getString(prompt));
        } while (result == -1);
        return result;
    }

    /**
     * Prompts and returns a valid double input.
     *
     * @param prompt
     * @return
     */
    public double getDouble(String prompt) {
        double result;
        do {
            try {
                result = Double.parseDouble(getString(prompt));
                return result;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        } while (true);
    }

    /**
     * Prompts and returns a positive integer.
     *
     * @param prompt
     * @return
     */
    public int getPositiveInt(String prompt) {
        return getInt(prompt);
    }

    /**
     * Prompts and returns an optional integer input.
     *
     * @param prompt
     * @return
     */
    public Integer getOptionalInt(String prompt) {
    Integer result;
    do {
        String value = getString(prompt);
        if (value.isEmpty()) return null; 
        result = parseInt(value);
    } while (result == -1); 
    return result;
}


    /**
     * Prompts and returns a valid future date.
     *
     * @param prompt
     * @return
     */
    public LocalDate getFutureDate(String prompt) {
        LocalDate date;
        do {
            date = parseDate(getString(prompt));
        } while (date == null);
        return date;
    }

    /**
     * Prompts and returns an optional future date.
     *
     * @param prompt
     * @return
     */
    public LocalDate getOptionalFutureDate(String prompt) {
    while (true) {
        String value = getString(prompt);
        if (value.isEmpty()) return null;  
        LocalDate result = parseDate(value);
        if (result != null) return result;
    }
}



    /**
     * Method to get user input that matches a given pattern. Repeats the prompt
     * until a valid input is provided.
     *
     * @param prompt The message displayed to the user.
     * @param pattern The regex pattern the input must match.
     * @param isUpdate If true, allows invalid input (used for updates).
     * @return The valid user input.
     */
    public String inputAndLoop(String prompt, String pattern, boolean isUpdate) {
        String result;
        do {
            result = getString(prompt);
            if (!Acceptable.isValid(result, pattern) && !isUpdate) {
                System.out.println("Invalid input! Please try again...");
            }
        } while (!Acceptable.isValid(result, pattern) && !isUpdate);
        return result;
    }

    /**
     * Gets user input or retains the previous value if input is empty.
     *
     * @param prompt The message displayed to the user.
     * @param pattern The regex pattern the input must match.
     * @param isUpdate If true, allows invalid input (used for updates).
     * @param oldValue The old value to retain if the input is empty.
     * @return The new input or the old value if input is empty.
     */
    public String getInputOrDefault(String prompt, String pattern, boolean isUpdate, String oldValue) {
        String newValue = inputAndLoop(prompt, pattern, isUpdate);
        return newValue.isEmpty() ? oldValue : newValue;
    }

    /**
     * Generates a unique customer code by ensuring no duplicates exist.
     *
     * @return A unique customer code.
     */
    public String getUniqueCustomerCode() {
        String code;
        do {
            code = inputAndLoop("Customer code [C/G/K + 4 digits]: ", Acceptable.CUSTOMER_COD_VALID, false);
            if (customerManager.isExistCode(code)) {
                System.out.println("Customer code already exists, please re-enter.");
            } else {
                return code;
            }
        } while (true);
    }

    /**
     * Collects customer information from user input. Supports both new customer
     * creation and updates to existing customers.
     *
     * @param isUpdate Indicates if the method is used for updating an existing
     * customer.
     * @param oldCus The existing customer data (null if creating a new
     * customer).
     * @return A Customer object with updated details.
     */
    public Customer inputCustomerInfo(boolean isUpdate, Customer oldCus) {
        Customer customer = new Customer();
        if (!isUpdate) {
            customer.setCustomerCode(getUniqueCustomerCode());
        } else if (oldCus != null) {
            customer.setCustomerCode(oldCus.getCustomerCode());
        }

        customer.setName(getInputOrDefault("Name [2 - 25 characters]: ", Acceptable.NAME_VALID, isUpdate, oldCus != null ? oldCus.getName() : ""));
        customer.setPhone(getInputOrDefault("Phone number [10 digits]: ", Acceptable.PHONE_VALID, isUpdate, oldCus != null ? oldCus.getPhone() : ""));
        customer.setEmail(getInputOrDefault("Email [example@domain.com]: ", Acceptable.EMAIL_VALID, isUpdate, oldCus != null ? oldCus.getEmail() : ""));

        return customer;
    }

    /**
     * Prompts the user to enter a valid customer code. Ensures the entered code
     * exists in the system.
     *
     * @return A valid customer code.
     */
    public String getCusCode() {
        String cusCode;
        do {
            cusCode = getString("Customer code: ").toLowerCase();
            if (cusCode.isEmpty()) {
                System.out.println("Customer code cannot be empty. Please enter again.");
                continue;
            }
            if (customerManager.searchCusByCode(cusCode) != null) {
                return cusCode;
            }
            System.out.println("This customer does not exist. Please enter again.");
        } while (true);
    }

    /**
     * Prompts the user to enter a valid menu code. Ensures the entered code
     * exists in the system.
     *
     * @return A valid menu code.
     */
    public String getMenuCode() {
        String menuCode;
        do {
            menuCode = getString("Code of SetMenu: ");
            if (feastMenuManager.getCode(menuCode) != null) {
                return menuCode;
            } 
            System.out.println("This menu code does not exist. Please enter again.");
            
        } while (true);
    }
}
