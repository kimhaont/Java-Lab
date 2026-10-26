
package model;

import java.io.Serializable;

public class CD implements Serializable {
    private String cdId;
    private String collectionName;
    private String type;
    private String title;
    private double price;
    private int year;
    

    public CD(String cdId, String collectionName, String type, String title, double price, int year) {
        this.cdId = cdId;
        this.collectionName = collectionName;
        this.type = type;
        this.title = title;
        this.price = price;
        this.year = year;
    }

    /**
     * @return the cdId
     */
    public String getCdId() {
        return cdId;
    }

    /**
     * @param cdId the cdId to set
     */
    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    /**
     * @return the collectionName
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * @param collectionName the collectionName to set
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    @Override
    public String toString() {
        return String.format(
        "%-10s %-15s %-10s %-20s %-10.2f %d",
        cdId,
        collectionName,
        type,
        title,
        price,
        year
    );
  }
}
