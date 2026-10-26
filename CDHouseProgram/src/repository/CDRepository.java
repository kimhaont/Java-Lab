package repository;

import java.io.*;
import java.util.ArrayList;
import model.CD;

public class CDRepository {

    private ArrayList<CD> cdList;

    public CDRepository() {
        cdList = new ArrayList<>();
    }

    public void add(CD cd) {
        cdList.add(cd);
    }

    public ArrayList<CD> getCdList() {
        return cdList;
    }

    public void setCdList(ArrayList<CD> cdList) {
        this.cdList = cdList;
    }

    // Find CD by ID
    public CD findById(String id) {

        for (CD cd : cdList) {

            if (cd.getCdId().equalsIgnoreCase(id)) {
                return cd;
            }
        }

        return null;
    }

    // Delete CD
    public boolean delete(String id) {

        CD cd = findById(id);

        if (cd != null) {

            cdList.remove(cd);

            return true;
        }

        return false;
    }

    // Save to file
    public boolean saveToFile(String fileName) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(fileName))) {

            oos.writeObject(cdList);

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error saving file: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // Load from file
    @SuppressWarnings("unchecked")
    public boolean loadFromFile(String fileName) {

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(fileName))) {

            cdList = (ArrayList<CD>) ois.readObject();

            return true;

        } catch (FileNotFoundException e) {

            System.out.println(
                    "File does not exist."
            );

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Error loading file: "
                    + e.getMessage()
            );
        }

        return false;
    }
}