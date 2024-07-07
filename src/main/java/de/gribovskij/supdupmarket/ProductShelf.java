package de.gribovskij.supdupmarket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eugen Gribovskij
 */
public class ProductShelf {

    private String description;

    private final List<Product> storedProducts;

    public ProductShelf(List<Product> storedProducts) {
        this.storedProducts = new ArrayList<>(storedProducts);
    }

    /**
     * Adds a product to a shelf if the product is marketable.
     *
     * @param product to add to the shelf
     * @param currentDate
     */
    public void addProduct(Product product, LocalDate currentDate) {
        product.setStoreDate(currentDate);
        if (product.isMarketable(currentDate)) {
            storedProducts.add(product);
        }
    }

    public void removeProduct(Product product) {
        storedProducts.remove(product);
    }

    /**
     * Removes all expired products from the shelf based on the given date.
     *
     * @param currentDate the date used to check product marketability
     * @return a list of non marketable products
     */
    public List<Product> removeExpiredProducts(LocalDate currentDate) {
        List<Product> nonMarketableProducts = storedProducts.stream().
                filter(product -> !product.isMarketable(currentDate)).toList();
        storedProducts.removeAll(nonMarketableProducts);

        return nonMarketableProducts;
    }

    // Getter / Setter
    public List<Product> getStoredProducts() {
        return storedProducts;
    }
 
    public String getDescription() {
        return description;
    }

}
