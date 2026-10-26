
package model;

public class Customer  {
    private String customerCode; // Unique customer ID
    private String name; // Customer's full name
    private String phone; // Contact number
    private String email; // Email address

    public Customer() {
    }

    public Customer(String customerCode, String name, String phone, String email) {
        this.customerCode = customerCode;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFormattedName() {
        String[] parts = name.split("\\s+"); // Split by spaces
        if (parts.length < 2) return name;   // If only one word, return as is

        String firstName = parts[parts.length - 1]; // Last word (first name)
        String lastName = String.join(" ", java.util.Arrays.copyOf(parts, parts.length - 1)); // Join remaining words

        return firstName + ", " + lastName;
    }
    
    public String getFirstName() {
        String[] parts = name.split("\\s+");
        if (parts.length < 2) return name;   // If only one word, return as is
        return parts[parts.length - 1];
          
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-26s | %-10s | %-25s ",
                customerCode.toUpperCase(), getFormattedName(), phone, email);
    }
    
     
     
}
