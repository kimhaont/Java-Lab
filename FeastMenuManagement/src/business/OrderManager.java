
package business;

import model.Order;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.FeastMenu;

public class OrderManager extends HashMap<String, Order> {
    
    private static final Map<String, Integer> feastMenu = new HashMap<>();
    private String pathFile;
    private boolean saved;
    private CustomerManager customerManager;
    private FeastMenuManager feastMenuManager;
  
    
    
    private final String HEADER_TABLE
            = "-------------------------------------------------------------------------------------------------------\n"
            + "ID  | Event date   | Customer ID  | Set Menu  | Price   | Tables    |        Cost\n"
            + "--------------------------------------------------------------------------------------------------------";
    private final String FOOTER_TABLE
            = "--------------------------------------------------------------------------------------------------------\n";

    
     
    public OrderManager() {
        this.customerManager = new CustomerManager(); // Tạo mới CustomerManager
        this.feastMenuManager = new FeastMenuManager(); // Tạo mới FeastMenuManager
        this.pathFile = "./feast_order_service.txt";
        this.saved = true;
        loadFromFile();
        readOrderFromFile();
    }
    
    public OrderManager(CustomerManager customerManager, FeastMenuManager feastMenuManager) {
        super();
        this.pathFile = "./feast_order_service.txt"; 
        this.saved = true;
        this.customerManager = customerManager;
        this.feastMenuManager = feastMenuManager;
        loadFromFile();
        readOrderFromFile();
    }
      
    public boolean isSaved() {
        return saved;
    }
    
    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("./FeastMenu.csv"))) {
            br.readLine(); // Skip header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    feastMenu.put(parts[0].trim(), Integer.parseInt(parts[2].trim()));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Searches for an order by its ID.
    public Order searchOrderID(String code) {
        return this.get(code);
    }
    
    //Displays all orders sorted by event date.
    public void showAllOrders() {
        List<Order> sortedOrders = new ArrayList<>(this.values());
        // Sort by event date in ascending order.
        sortedOrders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getEventDate().compareTo(o2.getEventDate());
            }
        });

        printOrderTable(sortedOrders);
    }
    
    //Places a new feast order.
    public Optional<Order> placeFeastOrder(String customerCode, String menuCode, int tables, LocalDate eventDate) {
        for (Order o : this.values()) {
            if (o.getCustomerCode().equalsIgnoreCase(customerCode)
                    && o.getSetMenuCode().equalsIgnoreCase(menuCode)
                    && o.getEventDate().equals(eventDate)) {
                return Optional.empty();
            }
        }
        double price = feastMenuManager.getMenuPrice(menuCode);
        if (price == 0) {
            System.out.println("Invalid menu code");
        }
        String orderID = UUID.randomUUID().toString().substring(0, 8);
        Order newOrder = new Order(orderID, customerCode, menuCode, tables, eventDate, price);
        this.put(orderID, newOrder);
        this.saved = false;
        return Optional.of(newOrder);
    }
    
    //Updates an existing order.
    public void update(Order newOrder) {
        if (newOrder == null) {
            return;
        }
        
        Order oldOrder = searchOrderID(newOrder.getOrderCode());
        if (oldOrder != null) {
            oldOrder.setSetMenuCode((newOrder.getSetMenuCode().isEmpty() && feastMenuManager.isValidMenuCode(newOrder.getSetMenuCode()) ? oldOrder.getSetMenuCode() : newOrder.getSetMenuCode()));
            oldOrder.setNumberOfTables(newOrder.getNumberOfTables() > 0 ? newOrder.getNumberOfTables() : oldOrder.getNumberOfTables());
            oldOrder.setEventDate(newOrder.getEventDate() != null ? newOrder.getEventDate() : oldOrder.getEventDate());
            this.put(oldOrder.getOrderCode(), oldOrder);
            if (this.values().contains(oldOrder)) {
                System.out.println("Dulicate data!");
            } else {
                System.out.println("Order updated successfully!");
                printOrderDetails(oldOrder.getCustomerCode());
            }

            this.saved = false;
        }
    }

    //Prints order details.
    public void printOrderDetails(String orderId) {
        DecimalFormat df = new DecimalFormat("#,##0");
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Order order = this.get(orderId);
        if (order == null) {
            System.out.println("Order not found.\n");
            return;
        }

        Customer customer = customerManager.searchCusByCode(order.getCustomerCode());
        FeastMenu menu = feastMenuManager.getCode(order.getSetMenuCode());

        if (customer == null || menu == null) {
            System.out.println("Invalid customer or menu data.\n");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Customer order information [Order ID: %s] \n", order.getOrderCode());
        System.out.println("--------------------------------------------------");
        System.out.printf("Code          : %s%n", customer.getCustomerCode().toUpperCase());
        System.out.printf("Customer name : %s%n", customer.getName());
        System.out.printf("Phone number  : %s%n", customer.getPhone());
        System.out.printf("Email         : %s%n", customer.getEmail());
        System.out.println("--------------------------------------------------");
        System.out.printf("Code of Set Menu : %s%n", order.getSetMenuCode().toUpperCase());
        System.out.printf("Set menu name    : %s%n", menu.getName());
        System.out.printf("Event date       : %s%n", order.getEventDate().format(formatDate));
        System.out.printf("Number of tables : %d%n", order.getNumberOfTables());
        System.out.printf("Price            : %s Vnd%n", df.format(menu.getPrice()));

        System.out.println("Ingredients:");
        for (String ingredient : menu.getIngredients()) {
            System.out.println(ingredient.trim());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Total Cost       : %s Vnd%n", df.format(order.getTotalCost()));
        System.out.println("-------------------------------------------------- \n");
    }
    
    
    //Prints a formatted table of orders.
    // sortedOrders List of orders sorted by date.
    private void printOrderTable(List<Order> sortedOrders) {
        System.out.println(HEADER_TABLE);
        for (Order i : sortedOrders) {
            System.out.println(i);
        }
        System.out.println(FOOTER_TABLE);
    }
    
    public void readOrderFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.pathFile))) {
            String line;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String orderCode = parts[0];
                    String customerCode = parts[1];
                    String menuCode = parts[2];
                    int tables = Integer.parseInt(parts[3]);
                    LocalDate eventDate = LocalDate.parse(parts[4], dateFormatter);
                    double price = Double.parseDouble(parts[5]);
                    Order order = new Order(orderCode, customerCode, menuCode, tables, eventDate, price);
                    this.put(orderCode, order);
                }
            }
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, "Order file not found", ex);
        } catch (IOException | NumberFormatException | DateTimeParseException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, "Error reading order file", ex);
        }
    }
    
    public void saveOrderToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathFile))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            for (Order order : this.values()) {
                String line = String.format("%s,%s,%s,%d,%s,%.2f",
                        order.getOrderCode(),
                        order.getCustomerCode(),
                        order.getSetMenuCode(),
                        order.getNumberOfTables(),
                        order.getEventDate().format(dateFormatter),
                        order.getPrice());
                writer.write(line);
                writer.newLine();
            }
            this.saved = true;
        } catch (IOException ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, "Error saving order file", ex);
        }
    }

}
