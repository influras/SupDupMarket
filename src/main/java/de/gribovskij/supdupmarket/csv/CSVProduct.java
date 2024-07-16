package de.gribovskij.supdupmarket.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

/**
 * Represents product data from a CSV file using OpenCSV annotations.
 * 
 * @author Eugen Gribovskij
 */
public class CSVProduct {

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Base Price")
    private double basePrice;

    @CsvBindByName(column = "Expiry Date")
    @CsvDate("dd.MM.yyyy")
    private LocalDate expiryDate;

    @CsvBindByName(column = "Start Quality")
    private int startQuality;

    @CsvBindByName(column = "Store Date")
    @CsvDate("dd.MM.yyyy")
    private LocalDate storeDate;

    @CsvBindByName(column = "Product Group Name")
    private String productGroupName;

    public CSVProduct() {
        // Required no-argument constructor for OpenCSV
    }
    
    /**
     * Constructs a new CSVProduct with specified attributes.
     *
     * @param name the product's name
     * @param basePrice the product's base price
     * @param expiryDate the product's expiry date
     * @param startQuality the product's initial quality
     * @param storeDate the date the product was added to the shelf
     * @param productGroupName the name of the associated product group
     */
    public CSVProduct(String name, double basePrice, LocalDate expiryDate, int startQuality, LocalDate storeDate, String productGroupName) {
        this.name = name;
        this.basePrice = basePrice;
        this.expiryDate = expiryDate;
        this.startQuality = startQuality;
        this.storeDate = storeDate;
        this.productGroupName = productGroupName;
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getStartQuality() {
        return startQuality;
    }

    public void setStartQuality(int startQuality) {
        this.startQuality = startQuality;
    }

    public LocalDate getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(LocalDate storeDate) {
        this.storeDate = storeDate;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    @Override
    public String toString() {
        return "CSVProduct{" +
                "name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", expiryDate=" + expiryDate +
                ", startQuality=" + startQuality +
                ", storeDate=" + storeDate +
                ", productGroupName='" + productGroupName + '\'' +
                '}';
    }
}
