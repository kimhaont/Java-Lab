
package dispatcher;

import business.CustomerManager;
import business.FeastMenuManager;
import business.OrderManager;
import java.time.LocalDate;
import java.util.Optional;
import model.Customer;
import model.Order;
import tools.Inputter;


public class Main {
    public static void main(String[] args) {
        // Initialize managers
        CustomerManager customerManager = new CustomerManager();
        FeastMenuManager feastMenuManager = new FeastMenuManager();
        OrderManager orderManager = new OrderManager(customerManager, feastMenuManager); 
        Inputter input = new Inputter(customerManager, feastMenuManager);
        

        String[] options = {
            "Register customers.",
            "Update customer information.",
            "Search for customer information by name.",
            "Display feast menus.",
            "Place a feast order.",
            "Update order information.",
            "Save data to file.",
            "Display Customer or Order lists",
            "Exit"
        };

        int choice;
        do {
            System.out.println("\n===== Traditional Feast Order Management System =====");
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            
            choice = input.getInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerCustomer(input, customerManager);
                    break;
                case 2:
                    updateCustomer(input, customerManager);
                    break;
                case 3:
                     searchCusByName(input, customerManager);
                    break;
                case 4:
                    feastMenuManager.displayFeastMenus();
                    break;
                case 5:
                    placeOrder(input, orderManager);
                    break;
                case 6:
                    updateOrder(input, orderManager, feastMenuManager);
                    break;
                case 7:
                    saveData(customerManager, orderManager);
                    break;
                case 8:
                    displayData(input, customerManager, orderManager);
                default:
                    exitProgram(input, customerManager, orderManager);
            }
        } while (choice != 8);
    }
 
    //Function 1: Register customers
    public static void registerCustomer(Inputter input, CustomerManager customerManager) {
        do {
            Customer newCustomer = input.inputCustomerInfo(false, null);
            customerManager.addNew(newCustomer);
            System.out.println("Registered Successfully!\n");
        } while (input.getString("Do you want to add more customers? (Y/N):").trim().equalsIgnoreCase("Y"));
    }
    
   //Function 2: Update customer information
    public static void updateCustomer(Inputter input, CustomerManager customerManager){
        do {
            String idUpdate = input.getString("Enter Customer code to update: ");
            Customer existingCustomer = customerManager.searchCusByCode(idUpdate);

            if (existingCustomer != null) {
                Customer updatedCustomer = input.inputCustomerInfo(true, existingCustomer);
                customerManager.update(updatedCustomer);
                System.out.println("Updated successfully.\n");
            } else {
                System.out.println("This customer does not exist.\n");
            }
        } while (input.getString("Do you want to update more customers? (Y/N): ").trim().equalsIgnoreCase("y"));
    }
    
    //Function 3: Search for customer information by name
    public static void searchCusByName(Inputter input, CustomerManager customerManager) {
        String searchName = input.getString("Enter customer name to search: ");
        customerManager.searchCusByName(searchName);
    }
    
    //Function 5: Place a feast order
    public static void placeOrder(Inputter input, OrderManager orderManager) {
        String cusCode = input.getCusCode();
        String menuCode = input.getMenuCode();
        int table = input.getPositiveInt("Number of tables > 0: ");
        LocalDate eventDate = input.getFutureDate("Date (dd/MM/yyyy): ");

        Optional<Order> newOrder = orderManager.placeFeastOrder(cusCode, menuCode, table, eventDate);

        if (newOrder.isPresent()) {
            orderManager.printOrderDetails(newOrder.get().getOrderCode());
        } else {
            System.out.println("Duplicate data!\n");
        }

    }
    
    //Function 6: Update order information
    public static void updateOrder(Inputter input, OrderManager orderManager, FeastMenuManager feastMenuManager) {
        do {
            String orderId = input.getString("Enter Order ID to update: ").trim();
            Order existingOrder = orderManager.searchOrderID(orderId);

            if (existingOrder == null) {
                System.out.println("Order not found.");
                return;
            }

            System.out.println("Current order details:");
            orderManager.printOrderDetails(orderId);

            String newMenuCode;
            do {
                newMenuCode = input.getString("New Menu Code (press Enter to keep current): ").trim();
                if (newMenuCode.isEmpty()) {
                    newMenuCode = existingOrder.getSetMenuCode();
                    break;
                }
                if (!feastMenuManager.isValidMenuCode(newMenuCode)) {
                    System.out.println("Invalid Menu Code! Please enter a valid code.");
                }
            } while (!feastMenuManager.isValidMenuCode(newMenuCode));

            Integer newTables = input.getOptionalInt("New number of tables (press Enter to keep current): ");
            if (newTables == null) {
                newTables = existingOrder.getNumberOfTables();
            }

            LocalDate newEventDate = input.getOptionalFutureDate("New event date (dd/MM/yyyy, press Enter to keep current): ");
            if (newEventDate == null) {
                newEventDate = existingOrder.getEventDate();
            }

            existingOrder.setSetMenuCode(newMenuCode);
            existingOrder.setNumberOfTables(newTables);
            existingOrder.setEventDate(newEventDate);
            orderManager.update(existingOrder);
        
        
        } while (input.getString("Do you want to update more orders? (Y/N): ").trim().equalsIgnoreCase("y"));
    }
    
    // Function 7: Save data to file
    public static void saveData(CustomerManager customerManager, OrderManager orderManager) {
        customerManager.saveCusToFile();
        orderManager.saveOrderToFile();
        System.out.println("Customer data has been successfully saved to “customers.txt”.");
        System.out.println("Order data has been successfully saved to “feast_order_service.txt”. \n");
    }
    
    //Function 8: Display Customer or Order lists
    public static void displayData(Inputter input, CustomerManager customerManager, OrderManager orderManager) {
        System.out.println("1. Display customers.");
        System.out.println("2. Display orders.");


        int option = input.getInt("Enter your choice: ");
        if (option == 1 && !customerManager.isEmpty()) {
            customerManager.showAll();
        } else if (option == 2 && !orderManager.values().isEmpty()) {
            orderManager.showAllOrders();
        } else {
            System.out.println("No data available.\n");
        }
    }
       
    public static void exitProgram(Inputter input, CustomerManager customerManager, OrderManager orderManager) {
        if (!customerManager.isSaved() || !orderManager.isSaved()) {
            while (true) {
                String beforeExit = input.getString("You have unsaved changes. Do you want to save before exiting? (Y/N): ").trim();
                if (beforeExit.equalsIgnoreCase("y")) {
                    saveData(customerManager, orderManager);
                    System.out.println("Bye bye .... ");
                    break;
                } else if (beforeExit.equalsIgnoreCase("n")) {
                    System.out.println("Bye bye... \n");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter Y or N. \n");
                }
            }
        } else {
            System.out.println("Bye bye .... ");
        }
    }


}
