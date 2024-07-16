package de.gribovskij.supdupmarket.csv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create CSV products.
 *
 * @author Eugen Gribovskij
 */
public class CSVProductCreator {

    private final List<CSVProduct> csvProducts;
    private final LocalDate startDate;

    /**
     * Constructor to initialize the product list and start date.
     */
    public CSVProductCreator() {
        this.csvProducts = new ArrayList<>();
        this.startDate = LocalDate.now();
    }

    /**
     * Method to create CSV products and add them to the product list.
     */
    public void createCsvProducts() {
        csvProducts.add(new CSVProduct("Gouda", 75.0, startDate.plusDays(60), 40, startDate, "Cheese"));
        csvProducts.add(new CSVProduct("Cheddar", 65.0, startDate.plusDays(84), 140, startDate, "Cheese"));
        csvProducts.add(new CSVProduct("Tilsiter", 53.0, startDate.plusDays(51), 90, startDate, "Cheese"));
        csvProducts.add(new CSVProduct("Burgtrocken", 40.0, LocalDate.MAX, 20, startDate, "Wine"));
        csvProducts.add(new CSVProduct("Delheim", 70.0, LocalDate.MAX, 40, startDate, "Wine"));
        csvProducts.add(new CSVProduct("Bodegas", 90.0, LocalDate.MAX, 50, startDate, "Wine"));
        csvProducts.add(new CSVProduct("Gefluegelbrust", 7.0, startDate.plusDays(9), 2, startDate, "Meat"));
        csvProducts.add(new CSVProduct("Putenbrust", 6.0, startDate.plusDays(11), 2, startDate, "Meat"));
        csvProducts.add(new CSVProduct("Rinderfilet", 10.0, startDate.plusDays(12), 2, startDate, "Meat"));
    }

    /**
     * Returns the list of CSV products.
     *
     * @return the list of CSV products
     */
    public List<CSVProduct> getCsvProducts() {
        return csvProducts;
    }

}
