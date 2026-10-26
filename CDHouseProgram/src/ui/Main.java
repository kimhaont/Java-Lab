package ui;

import service.CDService;
import utils.Inputter;

public class Main {

    public static void main(String[] args) {

        CDService service = new CDService();

        Inputter inputter = new Inputter();

        while (true) {

            showMenu();

            int choice = inputter.inputInt(
                    "Enter your choice: "
            );

            switch (choice) {

                case 1:

                    service.addCD();

                    break;

                case 2:

                    service.searchCDByTitle();

                    break;

                case 3:

                    service.displayCatalog();

                    break;

                case 4:

                    showUpdateDeleteMenu(
                            service,
                            inputter
                    );

                    break;

                case 5:

                    service.saveToFile();

                    break;

                case 6:

                    service.loadFromFile();

                    break;

                case 0:

                    System.out.println(
                            "Thank you for using CD House Program."
                    );

                    return;

                default:

                    System.out.println(
                            "Invalid choice. Please choose from 0 to 6."
                    );
            }

            System.out.println();
        }
    }

    // =====================================================
    // MAIN MENU
    // =====================================================

    private static void showMenu() {

        System.out.println();
        System.out.println(
                "================ CD HOUSE PROGRAM ================"
        );

        System.out.println(
                "1. Add CD to the catalog"
        );

        System.out.println(
                "2. Search CD by CD title"
        );

        System.out.println(
                "3. Display the catalog"
        );

        System.out.println(
                "4. Update / Delete CD"
        );

        System.out.println(
                "5. Save CD catalog to file"
        );

        System.out.println(
                "6. Print CD list from file"
        );

        System.out.println(
                "0. Quit"
        );

        System.out.println(
                "==================================================="
        );
    }

    // =====================================================
    // UPDATE / DELETE MENU
    // =====================================================

    private static void showUpdateDeleteMenu(
            CDService service,
            Inputter inputter) {

        while (true) {

            System.out.println();

            System.out.println(
                    "============== UPDATE / DELETE =============="
            );

            System.out.println(
                    "1. Update CD"
            );

            System.out.println(
                    "2. Delete CD"
            );

            System.out.println(
                    "0. Back to main menu"
            );

            System.out.println(
                    "==============================================="
            );

            int choice = inputter.inputInt(
                    "Enter your choice: "
            );

            switch (choice) {

                case 1:

                    service.updateCD();

                    break;

                case 2:

                    service.deleteCD();

                    break;

                case 0:

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }
}