package service;

import model.Product;
import repository.ProductRepository;
import utils.Inputter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductService {

    private ProductRepository repository;
    private Inputter inputter;

    public ProductService() {

        repository = new ProductRepository();
        inputter = new Inputter();
    }

    // =====================================================
    // FUNCTION 1: CREATE PRODUCT
    // =====================================================

    public void createProduct() {

        System.out.println("\n========== CREATE PRODUCT ==========");

        String productID;

        while (true) {

            productID = inputter.inputNonBlank(
                    "Enter Product ID: "
            );

            if (repository.findByID(productID) == null) {
                break;
            }

            System.out.println(
                    "Product ID already exists."
            );
        }

        String productName;

        while (true) {

            productName = inputter.inputNonBlank(
                    "Enter Product Name: "
            );

            if (productName.length() < 5) {

                System.out.println(
                        "Product Name must have at least 5 characters."
                );

                continue;
            }

            if (productName.contains(" ")) {

                System.out.println(
                        "Product Name must not contain spaces."
                );

                continue;
            }

            if (repository.findByName(productName) != null) {

                System.out.println(
                        "Product Name already exists."
                );

                continue;
            }

            break;
        }

        double unitPrice = inputter.inputDouble(
                "Enter Unit Price: ",
                0,
                10000
        );

        int quantity = inputter.inputInt(
                "Enter Quantity: ",
                0,
                1000
        );

        String status;

        while (true) {

            status = inputter.inputNonBlank(
                    "Enter Status (Available/Not Available): "
            );

            if (status.equalsIgnoreCase("Available")) {

                status = "Available";
                break;

            } else if (status.equalsIgnoreCase("Not Available")) {

                status = "Not Available";
                break;

            } else {

                System.out.println(
                        "Status must be Available or Not Available."
                );
            }
        }

        Product product = new Product(
                productID,
                productName,
                unitPrice,
                quantity,
                status
        );

        repository.add(product);

        System.out.println(
                "Create Product successfully."
        );
    }
    
    public void display() {

        if (repository.getProductList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "====================== PRODUCT LIST ======================"
        );

        System.out.printf(
                "%-10s %-20s %-12s %-10s %-15s%n",
                "ID",
                "Product Name",
                "Unit Price",
                "Quantity",
                "Status"
        );

        System.out.println(
                "----------------------------------------------------------"
        );

        for (Product product : repository.getProductList()) {

            System.out.println(product);
        }

        System.out.println(
                "=========================================================="
        );
    }

    // =====================================================
    // FUNCTION 2: CHECK EXIST PRODUCT
    // =====================================================

    public void checkExistProduct() {

        System.out.println("\n========== CHECK EXIST PRODUCT ==========");

        String productName = inputter.inputNonBlank(
                "Enter Product Name: "
        );

        Product product =
                repository.findByName(productName);

        if (product != null) {

            System.out.println("Exist Product.");

        } else {

            System.out.println("No Product Found!");
        }
    }

    // =====================================================
    // FUNCTION 3: SEARCH PRODUCT BY NAME
    // =====================================================

    public void searchByName() {

        System.out.println("\n========== SEARCH PRODUCT ==========");

        String searchString = inputter.inputNonBlank(
                "Enter product name: "
        );

        List<Product> result = new ArrayList<>();

        for (Product product : repository.getProductList()) {

            if (product.getProductName()
                    .toLowerCase()
                    .contains(searchString.toLowerCase())) {

                result.add(product);
            }
        }

        if (result.isEmpty()) {

            System.out.println("Have no any Product");

            return;
        }

        result.sort(
                Comparator.comparing(
                        Product::getProductName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        printHeader();

        for (Product product : result) {

            System.out.println(product);
        }
    }

    // =====================================================
    // FUNCTION 4.1: UPDATE PRODUCT
    // =====================================================

    public void updateProduct() {

    System.out.println("\n========== UPDATE PRODUCT ==========");

    String productID = inputter.inputNonBlank(
            "Enter Product ID: "
    );

    Product product = repository.findByID(productID);

    if (product == null) {

        System.out.println(
                "Product does not exist."
        );

        return;
    }

    System.out.println(
            "Press Enter to keep old information."
    );

    // =========================================
    // UPDATE PRODUCT NAME
    // =========================================

    while (true) {

        String newName = inputter.inputString(
                "Enter new Product Name: "
        );

        // Blank -> keep old value
        if (newName.isEmpty()) {
            break;
        }

        // At least 5 characters
        if (newName.length() < 5) {

            System.out.println(
                    "Product Name must have at least 5 characters."
            );

            continue;
        }

        // No spaces
        if (newName.contains(" ")) {

            System.out.println(
                    "Product Name must not contain spaces."
            );

            continue;
        }

        // Duplicate name
        Product existing =
                repository.findByName(newName);

        if (existing != null && existing != product) {

            System.out.println(
                    "Product Name already exists."
            );

            continue;
        }

        // Valid -> update
        product.setProductName(newName);

        break;
    }

    // =========================================
    // UPDATE UNIT PRICE
    // =========================================

    while (true) {

        String priceInput = inputter.inputString(
                "Enter new Unit Price: "
        );

        // Blank -> keep old value
        if (priceInput.isEmpty()) {
            break;
        }

        try {

            double newPrice =
                    Double.parseDouble(priceInput);

            if (newPrice < 0 || newPrice > 10000) {

                System.out.println(
                        "Unit Price must be from 0 to 10000."
                );

                continue;
            }

            product.setUnitPrice(newPrice);

            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Unit Price. Please enter a real number."
            );
        }
    }

    // =========================================
    // UPDATE QUANTITY
    // =========================================

    while (true) {

        String quantityInput = inputter.inputString(
                "Enter new Quantity: "
        );

        // Blank -> keep old value
        if (quantityInput.isEmpty()) {
            break;
        }

        try {

            int newQuantity =
                    Integer.parseInt(quantityInput);

            if (newQuantity < 0 || newQuantity > 1000) {

                System.out.println(
                        "Quantity must be from 0 to 1000."
                );

                continue;
            }

            product.setQuantity(newQuantity);

            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Quantity. Please enter an integer."
            );
        }
    }

    // =========================================
    // UPDATE STATUS
    // =========================================

    while (true) {

        String newStatus = inputter.inputString(
                "Enter new Status (Available/Not Available): "
        );

        // Blank -> keep old value
        if (newStatus.isEmpty()) {
            break;
        }

        if (newStatus.equalsIgnoreCase("Available")) {

            product.setStatus("Available");

            break;

        } else if (
                newStatus.equalsIgnoreCase("Not Available")) {

            product.setStatus("Not Available");

            break;

        } else {

            System.out.println(
                    "Status must be Available or Not Available."
            );
        }
    }

    System.out.println(
            "Update Product successfully."
    );
}

    // =====================================================
    // FUNCTION 4.2: DELETE PRODUCT
    // =====================================================

    public void deleteProduct() {

        System.out.println("\n========== DELETE PRODUCT ==========");

        String productID = inputter.inputNonBlank(
                "Enter Product ID: "
        );

        Product product =
                repository.findByID(productID);

        if (product == null) {

            System.out.println(
                    "Productname does not exist"
            );

            return;
        }

        boolean deleted =
                repository.delete(product);

        if (deleted) {

            System.out.println(
                    "Delete Product successfully."
            );

        } else {

            System.out.println(
                    "Delete Product failed."
            );
        }
    }

    // =====================================================
    // FUNCTION 5: SAVE TO FILE
    // =====================================================

    public void saveToFile() {

        System.out.println("\n========== SAVE TO FILE ==========");

        repository.saveToFile();
    }

    // =====================================================
    // FUNCTION 6: PRINT ALL FROM FILE
    // =====================================================

    public void printAllFromFile() {

        System.out.println(
                "\n========== PRINT PRODUCTS FROM FILE =========="
        );

        repository.loadFromFile();

        List<Product> list =
                repository.getProductList();

        if (list.isEmpty()) {

            System.out.println(
                    "Have no any Product"
            );

            return;
        }

        list.sort(
                Comparator.comparing(
                        Product::getQuantity
                ).reversed()
                .thenComparing(
                        Product::getUnitPrice
                )
        );

        printHeader();

        for (Product product : list) {

            System.out.println(product);
        }
    }

    // =====================================================
    // PRINT HEADER
    // =====================================================

    private void printHeader() {

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-10s %-20s %-12s %-10s %-15s%n",
                "ID",
                "Product Name",
                "Unit Price",
                "Quantity",
                "Status"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );
    }
}