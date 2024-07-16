package de.gribovskij.supdupmarket;

import de.gribovskij.supdupmarket.csv.CSVExporter;
import de.gribovskij.supdupmarket.csv.CSVImporter;
import de.gribovskij.supdupmarket.csv.CSVProduct;
import de.gribovskij.supdupmarket.csv.CSVProductCreator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The MarketService class represents a simulation of a market system. It
 * manages the lifecycle of products and product groups, performs initialization
 * from CSV files, and runs a simulation over a specified number of days to
 * simulate daily market activities.
 *
 * This class initializes product groups such as Cheese, Wine, and Meat, each
 * with specific rules for quality management, expiry handling, and pricing
 * strategies. It also reads products from a CSV file, associates them with
 * their respective groups, and manages their lifecycle through daily
 * simulations.
 *
 * The simulation starts from a specified start date and runs for a specified
 * number of days, during which it tracks and manages the expiration and removal
 * of products based on their expiry dates.
 *
 * @author Eugen Gribovskij
 */
public class MarketService {

    private final ProductShelf shelf;
    private final List<ProductGroup> productGroups;
    private List<CSVProduct> csvProducts;
    private final LocalDate startDate;

    public MarketService() {
        this.shelf = new ProductShelf(new ArrayList<>());
        this.productGroups = new ArrayList<>();
        this.csvProducts = new ArrayList<>();
        this.startDate = LocalDate.now();
        initializeProductGroups();
        initializeProducts();
    }

    private void initializeProducts() {

        CSVProductCreator csvProductCreator = new CSVProductCreator();
        csvProductCreator.createCsvProducts();
        csvProducts = csvProductCreator.getCsvProducts();
        CSVExporter.writeProductsToCSV("src/main/resources/products.csv", csvProducts);

        List<CSVProduct> importedCSVProducts = CSVImporter.importProductsFromCSV("src/main/resources/products.csv");

        for (CSVProduct csvProduct : importedCSVProducts) {
            shelf.addProduct(new Product(
                    csvProduct.getName(),
                    csvProduct.getBasePrice(),
                    csvProduct.getExpiryDate(),
                    csvProduct.getStartQuality(),
                    csvProduct.getProductGroupName(),
                    findProductGroup(csvProduct.getProductGroupName())), startDate);
        }

    }

    private void initializeProductGroups() {
        ProductRuleSet cheeseRuleSet = new ProductRuleSet(
                true, // Expiring
                ProductQualityChange.DECREASE, // Quality decreases
                true, // Daily price change
                1, // Quality change factor
                30, // Lowest quality boundary
                100, // Highest quality boundary
                1, // Days until quality change
                false, // Expiry discount
                0 // Expire discount in percent
        );
        productGroups.add(new ProductGroup("Cheese", "Kaese", cheeseRuleSet));

        ProductRuleSet wineRuleSet = new ProductRuleSet(
                false, // Not expiring
                ProductQualityChange.INCREASE, // Quality increases
                false, // No daily price change
                1, // Quality change factor
                1, // Lowest quality boundary
                50, // Highest quality boundary
                10, // Days until quality change
                false, // Expiry discount
                0 // Expire discount in percent
        );
        productGroups.add(new ProductGroup("Wine", "Wein", wineRuleSet));

        ProductRuleSet meatRuleSet = new ProductRuleSet(
                true, // Expiring
                ProductQualityChange.UNCHANGING, // Quality increases
                false, // No daily price change
                0, // Quality change factor
                1, // Lowest quality boundary
                1, // Highest quality boundary
                0, // Days until quality change
                true, // Expiry discount
                25 // Expire discount in percent
        );
        productGroups.add(new ProductGroup("Meat", "Fleisch", meatRuleSet));

    }

    public void startMarketSimulation(LocalDate currentDate, int numberOfDays) {
        String removedProductsHeadline = "#".repeat(10) + " Zu entfernende Produkte " + "#".repeat(10);
        currentDate = startDate;

        System.out.println("#".repeat(10) + " Willkommen im SuperDuperMarkt! " + "#".repeat(10));

        for (int day = 1; day < numberOfDays; day++) {
            List<Product> removedProducts = shelf.removeExpiredProducts(currentDate);

            System.out.println("Tag " + day + " (" + currentDate + "):");

            for (Product product : shelf.getStoredProducts()) {
                System.out.println(product.toUserInfo(currentDate) + "\n");
            }

            for (Product removedProduct : removedProducts) {
                System.out.println(removedProductsHeadline);
                System.out.println(removedProduct.toUserInfo(currentDate));
                System.out.println("#".repeat(removedProductsHeadline.length()) + "\n");
            }

            System.out.println("-".repeat(removedProductsHeadline.length()) + "\n");

            currentDate = currentDate.plusDays(1);
        }
    }

    public ProductGroup findProductGroup(String name) {
        for (ProductGroup group : productGroups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Product group with name '" + name + "' not found.");
    }
    
    // Getter

    public ProductShelf getShelf() {
        return shelf;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public List<CSVProduct> getCsvProducts() {
        return csvProducts;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

}
