
package business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

/**
 *
 * @author Admin
 */
public class CustomerManager extends ArrayList<Customer> {
    
    private final String pathFile; // File path for storing customer data
    private boolean saved; // Flag to track if changes are saved
    
    private final String HEADER_TABLE = "----------------------------------------------------------------------------\n"
        + "Code  | Customer Name                | Phone      | Email        \n"
        + "----------------------------------------------------------------------------";
    private final String FOOTER_TABLE = "----------------------------------------------------------------------------\n";

    public CustomerManager() {
        super();
        this.pathFile = "./customers.txt";
        this.saved = true;
        readCusFromFile();
    }
    
    public boolean isSaved(){
        return saved;
    }
    
    //Adds a new customer to the list.
    public void addNew(Customer x){
        if(x != null && this.add(x)){
            this.saved = false;
        }
    }
    
     //Updates customer information if the customer exists.
     //newInfo The new customer data.
    public void update(Customer newInfo) {
        if (newInfo == null){
            return;
        }
        
       Customer oldInfo = searchCusByCode(newInfo.getCustomerCode());
        if (oldInfo != null) {
            oldInfo.setName(newInfo.getName().isEmpty() ? oldInfo.getName() : newInfo.getName());
            oldInfo.setPhone(newInfo.getPhone().isEmpty() ? oldInfo.getPhone() : newInfo.getPhone());
            oldInfo.setEmail(newInfo.getEmail().isEmpty() ? oldInfo.getEmail() : newInfo.getEmail());
            this.saved = false;
        }
        
        
    }
    
    //Searches for a customer by code.
    public Customer searchCusByCode(String code) {
        for (Customer i : this) {
            if (i.getCustomerCode().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return null;
    }
    
    public boolean isExistCode(String code) {
        return searchCusByCode(code) != null;
    }
    
    //Comparator for sorting customers by name in Vietnamese order.
    Comparator<Customer> nameComparator = new Comparator<Customer>() {
        @Override
        public int compare(Customer c1, Customer c2) {
            Collator collator = Collator.getInstance(new Locale("vi", "VN"));
            return collator.compare(c1.getFirstName(), c2.getFirstName());
        }
    };

    /**
     * Displays all customers sorted by name.
     */
    public void showAll() {
        List<Customer> sortedList = new ArrayList<>(this);
        sortedList.sort(nameComparator);
        printCustomerTable(sortedList);
    }

    /**
     * Searches customers by name and displays matching results.
     *
     * @param name The name to search for
     */
    public void searchCusByName(String name) {
        List<Customer> list = new ArrayList<>();
        for (Customer i : this) {

            if (i.getFirstName().contains(name)) {
                list.add(i);
            }
        }

        if (!list.isEmpty()) {
            list.sort(nameComparator);
            System.out.printf("Matching Customers: %s\n", name);
            printCustomerTable(list);
        } else {
            System.out.println("No customers match the search criteria!\n");
        }
    }

     
    //Prints customer data in a formatted table.list The list of customers to display
    private void printCustomerTable(List<Customer> list) {
        System.out.println(HEADER_TABLE);
        for (Customer i : list) {
            System.out.println(i); // Print each customer
        }
        System.out.println(FOOTER_TABLE);
    }
    
    //Reads customer data from a file.
    public void readCusFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    Customer customer = new Customer(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
                    this.add(customer);
                }
            }
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, "File not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, "Error reading file", ex);
        }
    }
    
    //Saves customer data to a file
    public void saveCusToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile))) {
            for (Customer c : this) {
                bw.write(String.format("%s|%s|%s|%s\n", c.getCustomerCode(), c.getName(), c.getPhone(), c.getEmail()));
            }
            this.saved = true;
        } catch (IOException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, "Error writing file", ex);
        }
    }

    

}
