package model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * name, price, and list of ingredients.
 * 
 * @author admin
 */
public class FeastMenu {

    private String MenuCode; // Unique code for the menu item
    private String name; // Name of the menu item
    private double price; // Price of the menu item
    private List<String> ingredients; // List of ingredients used in the menu item

   
    public FeastMenu() {
    }

    
    public FeastMenu(String MenuCode, String name, double price, String ingredients) {
        this.MenuCode = MenuCode;
        this.name = name;
        this.price = price;
        this.ingredients = Arrays.asList(ingredients.split("#")); // Splitting ingredients string into a list
    }

    
    // Getters and setters for menu attributes
    
    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String MenuCode) {
        this.MenuCode = MenuCode;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public double getPrice() {
        return price;
    }

    
    public void setPrice(double price) {
        this.price = price;
    }

    
    public List<String> getIngredients() {
        return ingredients;
    }

    
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
