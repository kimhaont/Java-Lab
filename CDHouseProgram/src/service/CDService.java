package service;

import java.util.ArrayList;
import java.util.Comparator;

import model.CD;
import repository.CDRepository;
import utils.Inputter;
import utils.Validation;

public class CDService {

    private CDRepository repository;
    private Inputter inputter;

    private static final String FILE_NAME = "CD.dat";

    public CDService() {

        repository = new CDRepository();
        inputter = new Inputter();
    }

  

    private boolean isIdExist(String id) {

        for (CD cd : repository.getCdList()) {

            if (cd.getCdId().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

 

    public void addCD() {

        String id = inputter.inputNonBlank(
                "Enter CD ID: "
        );

        if (isIdExist(id)) {

            System.out.println(
                    "CD ID already exists."
            );

            return;
        }

        String collection;

        while (true) {

            collection = inputter.inputNonBlank(
                    "Enter collection (Game/Movie/Music): "
            );

            if (Validation.isValidCollection(collection)) {
                break;
            }

            System.out.println(
                    "Collection must be Game, Movie or Music."
            );
        }

        String type;

        while (true) {

            type = inputter.inputNonBlank(
                    "Enter type (Audio/Video): "
            );

            if (Validation.isValidType(type)) {
                break;
            }

            System.out.println(
                    "Type must be Audio or Video."
            );
        }

        String title = inputter.inputNonBlank(
                "Enter CD title: "
        );

        double price;

        while (true) {

            price = inputter.inputDouble(
                    "Enter unit price: "
            );

            if (Validation.isValidPrice(price)) {
                break;
            }

            System.out.println(
                    "Price must be greater than or equal to 0."
            );
        }

        int year;

        while (true) {

            year = inputter.inputInt(
                    "Enter publishing year: "
            );

            if (Validation.isValidYear(year)) {
                break;
            }

            System.out.println(
                    "Year must be between 1900 and 2100."
            );
        }

        CD cd = new CD(
                id,
                collection,
                type,
                title,
                price,
                year
        );

        repository.add(cd);

        System.out.println(
                "CD added successfully."
        );
    }

   
    public void searchCDByTitle() {

        if (repository.getCdList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        String keyword = inputter.inputNonBlank(
                "Enter CD title to search: "
        );

        boolean found = false;

        System.out.println();

        System.out.println(
                "------------------------------------------------------------"
        );

        for (CD cd : repository.getCdList()) {

            if (cd.getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                System.out.println(cd);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No CD found with title: " + keyword
            );
        }

        System.out.println(
                "------------------------------------------------------------"
        );
    }

    public void displayCatalog() {

        if (repository.getCdList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "====================== CD CATALOG ======================"
        );

        System.out.printf(
                "%-10s %-15s %-10s %-20s %-10s %s%n",
                "CD ID",
                "Collection",
                "Type",
                "Title",
                "Price",
                "Year"
        );

        System.out.println(
                "----------------------------------------------------------"
        );

        for (CD cd : repository.getCdList()) {

            System.out.println(cd);
        }

        System.out.println(
                "=========================================================="
        );
    }

   
    public void updateCD() {

        if (repository.getCdList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        String id = inputter.inputNonBlank(
                "Enter CD ID to update: "
        );

        CD cd = repository.findById(id);

        if (cd == null) {

            System.out.println(
                    "CD not found."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Current CD information:"
        );
        System.out.println(cd);

        System.out.println();
        System.out.println(
                "Press Enter to keep the old value."
        );

        while (true) {

            String collection = inputter.inputString(
                    "New collection: "
            );

            if (collection.isEmpty()) {
                break;
            }

            if (Validation.isValidCollection(collection)) {

                cd.setCollectionName(collection);

                break;

            } else {

                System.out.println(
                        "Collection must be Game, Movie or Music."
                );
            }
        }


        while (true) {

            String type = inputter.inputString(
                    "New type: "
            );

            if (type.isEmpty()) {
                break;
            }

            if (Validation.isValidType(type)) {

                cd.setType(type);

                break;

            } else {

                System.out.println(
                        "Type must be Audio or Video."
                );
            }
        }

        String title = inputter.inputString(
                "New title: "
        );

        if (!title.isEmpty()) {

            cd.setTitle(title);
        }

        while (true) {

            String priceInput = inputter.inputString(
                    "New price: "
            );

            if (priceInput.isEmpty()) {
                break;
            }

            try {

                double price =
                        Double.parseDouble(priceInput);

                if (Validation.isValidPrice(price)) {

                    cd.setPrice(price);

                    break;

                } else {

                    System.out.println(
                            "Price must be greater than or equal to 0."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid number."
                );
            }
        }

        while (true) {

            String yearInput = inputter.inputString(
                    "New publishing year: "
            );

            if (yearInput.isEmpty()) {
                break;
            }

            try {

                int year =
                        Integer.parseInt(yearInput);

                if (Validation.isValidYear(year)) {

                    cd.setYear(year);

                    break;

                } else {

                    System.out.println(
                            "Year must be between 1900 and 2100."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid integer."
                );
            }
        }

        System.out.println();
        System.out.println(
                "CD updated successfully."
        );
    }

    public void deleteCD() {

        if (repository.getCdList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        String id = inputter.inputNonBlank(
                "Enter CD ID to delete: "
        );

        CD cd = repository.findById(id);

        if (cd == null) {

            System.out.println(
                    "CD not found."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "CD information:"
        );

        System.out.println(cd);

        String confirm = inputter.inputString(
                "Are you sure you want to delete this CD? (Y/N): "
        );

        if (confirm.equalsIgnoreCase("Y")) {

            if (repository.delete(id)) {

                System.out.println(
                        "CD deleted successfully."
                );

            } else {

                System.out.println(
                        "Delete failed."
                );
            }

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }

    public void saveToFile() {

        if (repository.getCdList().isEmpty()) {

            System.out.println(
                    "The CD catalog is empty."
            );

            return;
        }

        boolean result =
                repository.saveToFile(FILE_NAME);

        if (result) {

            System.out.println(
                    "CD catalog saved successfully to "
                    + FILE_NAME
            );

        } else {

            System.out.println(
                    "Failed to save CD catalog."
            );
        }
    }

    public void loadFromFile() {

        boolean result =
                repository.loadFromFile(FILE_NAME);

        if (result) {

            System.out.println(
                    "CD catalog loaded successfully from "
                    + FILE_NAME
            );

            sortByTitle();

            displayCatalog();

        } else {

            System.out.println(
                    "Failed to load CD catalog."
            );
        }
    }

    private void sortByTitle() {

        ArrayList<CD> list =
                repository.getCdList();

        list.sort(
                Comparator.comparing(
                        CD::getTitle,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }
}