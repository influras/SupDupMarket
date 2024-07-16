
import de.gribovskij.supdupmarket.MarketService;
import de.gribovskij.supdupmarket.Product;
import de.gribovskij.supdupmarket.ProductGroup;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eugen Gribovskij
 */
public class MarketServiceTest {

    private MarketService marketService;

    @BeforeEach
    public void setUp() {
        marketService = new MarketService();
    }

    @Test
    public void testProductInitialization() {
        // Check whether the product groups have been initialized correctly
        List<ProductGroup> productGroups = marketService.getProductGroups();
        assertNotNull(productGroups);
        assertEquals(3, productGroups.size()); // Assumption: Three product groups were initialized

        // Check whether products have been added correctly to the shelves
        List<Product> storedProducts = marketService.getShelf().getStoredProducts();
        assertNotNull(storedProducts);
        assertFalse(storedProducts.isEmpty());
    }

    @Test
    public void testStartMarketSimulation() {

        LocalDate currentDate = LocalDate.now();
        // Start market simulation for 120 days
        marketService.startMarketSimulation(currentDate, 120);

        // Check if all products were handled correctly after simulation
        List<Product> storedProducts = marketService.getShelf().getStoredProducts();
        assertNotNull(storedProducts);

        storedProducts = marketService.getShelf().getStoredProducts();
        // Test the number of products in the shelf after 120 days
        assertEquals(3, storedProducts.size()); // Assuming 3 products after simulation

        // Test if specific products with expected attributes are present after simulation
        boolean cheeseFound = false;
        boolean wineFound = false;
        boolean meatFound = false;

        for (Product product : storedProducts) {
            switch (product.getProductGroupName()) {
                case "Cheese" -> {
                    cheeseFound = true;
                }
                case "Wine" -> {
                    wineFound = true;
                    assertTrue(product.getCurrentQuality(currentDate) >= 1); // Assuming minimum quality for wine
                    assertTrue(product.getCurrentQuality(currentDate) <= 50); // Assuming maximum quality for wine
                }
                case "Meat" -> {
                    meatFound = true;
                }
                default -> {
                }
            }
        }

        assertTrue(wineFound);
        assertFalse(cheeseFound);
        assertFalse(meatFound);

    }

    @Test
    public void testFindProductGroup() {
        // Test whether the findProductGroup method works correctly
        ProductGroup cheeseGroup = marketService.findProductGroup("Cheese");
        assertNotNull(cheeseGroup);
        assertEquals("Cheese", cheeseGroup.getName());
    }

}
