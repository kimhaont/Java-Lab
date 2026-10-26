
package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FeastMenu;

/**
 *
 * @author Admin
 */
public class FeastMenuManager extends ArrayList<FeastMenu> {
    private final String pathFile;

    public FeastMenuManager() {
        super();
        this.pathFile = "./FeastMenu.csv";
        readFromFile();
    }

    public FeastMenu getCode(String code) {
        for (FeastMenu m : this) {
            if (m.getMenuCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }
    
    /**
     * Converts a CSV line into a FeastMenu object.
     * @param temp A line from the file.
     * @return FeastMenu object or null if parsing fails.
     */
    private FeastMenu dataToObject(String temp) {
        try {
            String[] parts = temp.split(",");
            String menuCode = parts[0].trim();
            String name = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());
            
            // Process ingredients
            String ingredients = parts[3].trim()
                    .replaceAll("^\"|\"$", "")
                    .replace("#", "\n");

            return new FeastMenu(menuCode, name, price, ingredients);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing line: " + temp);
            return null;
        }
    }
    
    /**
     * Gets the price of a menu by its code.
     * @param code The menu code.
     * @return Menu price, or 0 if not found.
     */
    public double getMenuPrice(String code) {
        for (FeastMenu m : this) {
            if (m.getMenuCode().equalsIgnoreCase(code)) {
                return m.getPrice();
            }
        }
        return 0;
    }
    
    //find menu by code
    public FeastMenu findFeastMenuByCode(String code) {
        for (FeastMenu i : this) {
            if (i.getMenuCode().compareToIgnoreCase(code) == 0) {
                return i;
            }
        }
        return null;
    }

    public boolean isValidCode(String code) {
        return findFeastMenuByCode(code) != null;
    }
    
     /**
     * Displays all feast menus sorted by price.
     */
    public void displayFeastMenus() {
        DecimalFormat df = new DecimalFormat("#,##0");
        if (this.isEmpty()) {
            System.out.println("Cannot read data from FeastMenu.csv. Please check it.");
            return;
        }

        // Sort menus by price (ascending)
           this.sort((FeastMenu f1, FeastMenu f2) -> {
               return Double.compare(f1.getPrice(), f2.getPrice());
           });

        System.out.println("List of Set Menus for ordering party:");
        System.out.println("--------------------------------------------------");

        for (FeastMenu menu : this) {
            System.out.printf("Code        : %s%n", menu.getMenuCode());
            System.out.printf("Name        : %s%n", menu.getName());
            System.out.printf("Price       : %s Vnd%n", df.format(menu.getPrice()));
            System.out.println("Ingredients :");
            System.out.println(String.join("\n", menu.getIngredients()));
            System.out.println("-------------------------------------------------- \n");
        }
    }
    
    //Read csv
    private void readFromFile() {
        FileReader fr = null;
        try {
            File file = new File(this.pathFile);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); // Skip header
            String temp;
            while ((temp = br.readLine()) != null) {
                FeastMenu m = dataToObject(temp);
                if (m != null) {
                    this.add(m);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FeastMenuManager.class.getName()).log(Level.SEVERE, "File not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(FeastMenuManager.class.getName()).log(Level.SEVERE, "Error reading file", ex);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FeastMenuManager.class.getName()).log(Level.SEVERE, "Error closing file", ex);
            }
        }
    }
    
    //Checks if a menu code exists
    public boolean isValidMenuCode(String code) {
        return getCode(code) != null;
    }
    
    
    
}
