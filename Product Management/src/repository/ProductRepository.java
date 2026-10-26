package repository;

import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private List<Product> productList;

    private final String FILE_NAME = "Product.dat";

    public ProductRepository() {

        productList = new ArrayList<>();

        loadFromFile();
    }

    public List<Product> getProductList() {

        return productList;
    }

    public void add(Product product) {

        productList.add(product);
    }

    public Product findByID(String productID) {

        for (Product product : productList) {

            if (product.getProductID()
                    .equalsIgnoreCase(productID)) {

                return product;
            }
        }

        return null;
    }

    public Product findByName(String productName) {

        for (Product product : productList) {

            if (product.getProductName()
                    .equalsIgnoreCase(productName)) {

                return product;
            }
        }

        return null;
    }

    public boolean delete(Product product) {

    if (product == null) {
        return false;
    }

    return productList.remove(product);
}

    public void saveToFile() {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {

            oos.writeObject(productList);

            System.out.println("Save to file successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error while saving file: " + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(FILE_NAME))) {

            Object object = ois.readObject();

            if (object instanceof List) {

                productList = (List<Product>) object;
            }

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Cannot load Product.dat: " + e.getMessage()
            );
        }
    }
}