/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class Order {
    private String orderCode; // Unique identifier for the order
    private String customerCode; // Unique identifier for the customer
    private String setMenuCode; // Code representing the selected menu set
    private int numberOfTables; // Number of tables booked for the event
    private LocalDate eventDate; // Date of the event
    private double price; // Price per table

    public Order() {
    }

    public Order(String orderCode, String customerCode, String setMenuCode, int numberOfTables, LocalDate eventDate, double price) {
        this.orderCode = orderCode;
        this.customerCode = customerCode;
        this.setMenuCode = setMenuCode;
        this.numberOfTables = numberOfTables;
        this.eventDate = eventDate;
        this.price = price;
    }

     public String getOrderCode() {
        return orderCode;
    }

    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    
    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    
    public String getSetMenuCode() {
        return setMenuCode;
    }

    
    public void setSetMenuCode(String setMenuCode) {
        this.setMenuCode = setMenuCode;
    }

    
    public int getNumberOfTables() {
        return numberOfTables;
    }

    
    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    
    public LocalDate getEventDate() {
        return eventDate;
    }

    
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    
    public double getPrice() {
        return price;
    }

   
    public void setPrice(double price) {
        this.price = price;
    }

    
    public double getTotalCost() {
        return price * numberOfTables;
    }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0"); // Formatting price with commas
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting date
        return String.format("%-9s| %-11s| %-12s| %-9s| %-10s| %7d| %12s",
                this.orderCode,
                this.eventDate.format(formatDate),
                this.customerCode.toUpperCase(),
                this.getSetMenuCode().toUpperCase(),
                df.format(this.price),
                this.numberOfTables,
                df.format(this.getTotalCost()));
    }
    ///*Phương thức này đảm bảo rằng hai đối tượng Order có cùng customerCode, setMenuCode, và eventDate sẽ có cùng mã băm.
//Điều này giúp cải thiện hiệu suất khi sử dụng Order trong các cấu trúc dữ liệu như HashSet hoặc HashMap, vì hệ thống có thể nhanh chóng xác định vị trí lưu trữ của đối tượng mà không cần so sánh toàn bộ dữ liệu.*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.customerCode);
        hash = 47 * hash + Objects.hashCode(this.setMenuCode);
        hash = 47 * hash + Objects.hashCode(this.eventDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerCode, other.customerCode)) {
            return false;
        }
        if (!Objects.equals(this.setMenuCode, other.setMenuCode)) {
            return false;
        }
        return Objects.equals(this.eventDate, other.eventDate);
    }
    
}
