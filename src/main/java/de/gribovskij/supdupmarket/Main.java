package de.gribovskij.supdupmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductShelf shelf = new ProductShelf(new ArrayList<>());

        String removedProductsHeadline = "#".repeat(10) + " Zu entfernende Produkte " + "#".repeat(10);

        LocalDate startDate = LocalDate.now();

        // Define product groups and rule sets
        ProductRuleSet cheeseRuleSet = new ProductRuleSet(
                true, // Expiring
                ProductQualityChange.DECREASE, // Quality decreases
                true, // Daily price change
                1, // Quality change factor
                30, // Lowest quality boundary
                100, // Highest quality boundary
                1 // Days until quality change
        );
        ProductGroup cheeseGroup = new ProductGroup("Cheese", "Kaese", cheeseRuleSet);

        ProductRuleSet wineRuleSet = new ProductRuleSet(
                false, // Not expiring
                ProductQualityChange.INCREASE, // Quality increases
                false, // No daily price change
                1, // Quality change factor
                1, // Lowest quality boundary
                50, // Highest quality boundary
                10 // Days until quality change
        );
        ProductGroup wineGroup = new ProductGroup("Wine", "Weine", wineRuleSet);

        // Create and add products to the list
        shelf.addProduct(new Product("Gouda", 75.0, startDate.plusDays(60), 40, cheeseGroup), startDate);
        shelf.addProduct(new Product("Cheddar", 65.0, startDate.plusDays(84), 140, cheeseGroup), startDate);
        shelf.addProduct(new Product("Tilsiter", 53.0, startDate.plusDays(51), 90, cheeseGroup), startDate);
        shelf.addProduct(new Product("Burgtrocken", 40.0, LocalDate.MAX, 20, wineGroup), startDate);
        shelf.addProduct(new Product("Delheim", 70.0, LocalDate.MAX, 40, wineGroup), startDate);
        shelf.addProduct(new Product("Bodegas", 90.0, LocalDate.MAX, 50, wineGroup), startDate);

        // Define the number of days to simulate
        int numberOfDays = 120;
        LocalDate currentDate = startDate;

        // Simulate and print product details for each day
        for (int day = 1; day < numberOfDays; day++) {
            List<Product> removedProducts = shelf.removeExpiredProducts(currentDate);

            System.out.println("Tag " + day + " (" + currentDate + "):");

            // Update and print each product's details
            for (Product product : shelf.getStoredProducts()) {
                System.out.println(product.toUserInfo(currentDate) + "\n");
            }

            for (Product removedProduct : removedProducts) {
                System.out.println(removedProductsHeadline);
                System.out.println(removedProduct.toUserInfo(currentDate));
                System.out.println("#".repeat(removedProductsHeadline.length()) + "\n");
            }

            System.out.println("-".repeat(removedProductsHeadline.length()) + "\n");

            // Advance to the next day
            currentDate = currentDate.plusDays(1);

        }
    }
}
