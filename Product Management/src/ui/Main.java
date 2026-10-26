package ui;

import service.ProductService;
import utils.Inputter;

public class Main {

    public static void main(String[] args) {

        ProductService service =
                new ProductService();

        Inputter inputter =
                new Inputter();

        while (true) {

            showMenu();

            int choice = inputter.inputInt(
                    "Enter your choice: ",
                    1,
                    9
            );

            switch (choice) {

                case 1:
                    service.createProduct();
                    break;

                case 2:
                    service.checkExistProduct();
                    break;

                case 3:
                    service.searchByName();
                    break;

                case 4:
                    service.updateProduct();
                    break;

                case 5:
                    service.deleteProduct();
                    break;

                case 6:
                    service.saveToFile();
                    break;

                case 7:
                    service.printAllFromFile();
                    break;
                
                case 8:
                    service.display();
                    break;

                case 9:

                    System.out.println(
                            "Goodbye!"
                    );

                    return;
            }

            System.out.println();
            inputter.inputString(
                    "Press Enter to return to main menu..."
            );
        }
    }

    private static void showMenu() {

        System.out.println(
                "\n========== PRODUCT MANAGEMENT =========="
        );

        System.out.println(
                "1. Create Product"
        );

        System.out.println(
                "2. Check Exist Product"
        );

        System.out.println(
                "3. Search Product by Name"
        );

        System.out.println(
                "4. Update Product"
        );

        System.out.println(
                "5. Delete Product"
        );

        System.out.println(
                "6. Save to File"
        );

        System.out.println(
                "7. Print All Products from File"
        );

        System.out.println(
                "8. display"
        );
        System.out.println(
                "9. Quit"
        );

        System.out.println(
                "========================================"
        );
    }
}