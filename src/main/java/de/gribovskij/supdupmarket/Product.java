package de.gribovskij.supdupmarket;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a product in the supermarket with attributes such as name, base
 * price, expiry date, quality, and its associated product group.
 *
 * <p>
 * This class provides methods to check the marketability of a product and
 * standard overrides for equality and hashing.</p>
 *
 * @author Eugen Gribovskij
 */
public class Product {

    private String name;

    private final double basePrice;

    // The date then a product is not suitable for sale anymore
    private LocalDate expiryDate;

    private final int startQuality;

    // The date, when the product got added to a shelf
    private LocalDate storeDate;

    private String productGroupName;

    private ProductGroup productGroup;

    private int currentQuality;

    /**
     * Constructs a new {@code Product} with the specified attributes.
     *
     * <p>
     * Initializes the product with a name, base price, expiry date, starting
     * quality, and associated product group. If the product's rules indicate it
     * is not perishable, the expiry date is set to the maximum possible
     * date.</p>
     *
     * @param name the product's name; must not be {@code null}
     * @param basePrice the initial price; must be non-negative
     * @param expiryDate the expiry date if perishable; otherwise must be set to
     * LocalDate.MAX
     * @param startQuality the initial quality; must be non-negative
     * @param productGroupName the name associated to the product group
     * @param productGroup the associated product group
     *
     * @throws IllegalArgumentException if {@code basePrice} or
     * {@code startQuality} is negative
     * @throws NullPointerException if {@code name} or {@code productGroup} is
     * {@code null}
     */
    public Product(String name, double basePrice, LocalDate expiryDate, int startQuality, String productGroupName, ProductGroup productGroup) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        if (startQuality < 0) {
            throw new IllegalArgumentException("Quality cannot be negative");
        }
        this.name = name;
        this.basePrice = basePrice;
        if (!productGroup.getProductRule().isExpiring() && !expiryDate.equals(LocalDate.MAX)) {
            throw new IllegalArgumentException("expiryDate must be initialized with LocalDate.MAX, if product cannot expire");
        }
        this.expiryDate = productGroup.getProductRule().isExpiring() ? expiryDate : LocalDate.MAX;
        this.startQuality = startQuality;
        this.productGroupName = productGroupName;
        this.productGroup = productGroup;
    }

    /**
     * Checks if a product is suitable for sale. A product is considered not
     * marketable if it meets any of the following conditions: - It is expired
     * according to the product rules. - Its quality falls below the acceptable
     * boundary defined by the product rules. - Its expiry date is not today's
     * date or after.
     *
     * @param checkDate the date when the check for marketability was done
     * @return true if the product is marketable, false otherwise(breaches a
     * product rule)
     */
    public boolean isMarketable(LocalDate checkDate) {
        ProductRuleSet productRuleSet = this.productGroup.getProductRule();

        if (productRuleSet.isExpiring()) {
            // Check if the product is expired (expiry date is today or earlier) or quality is below the threshold
            return !expiryDate.isBefore(checkDate.plusDays(1)) && getCurrentQuality(checkDate) >= productRuleSet.getLowestQualityBoundary();
        }

        // For non-expiring products, only check quality
        return getCurrentQuality(checkDate) > productRuleSet.getLowestQualityBoundary();
    }

    public String marketableInfo(LocalDate currentDate) {
        ProductRuleSet productRuleSet = productGroup.getProductRule();

        if (getCurrentQuality(currentDate) < productRuleSet.getLowestQualityBoundary()) {
            return "Nein - Qualitaetsanforderung unterschritten - bitte aus dem Regal raeumen!";
        }
        if (expiryDate.isBefore(currentDate.plusDays(1))) {
            return "Nein - das Produkt ist abgelaufen - bitte aus dem Regal raeumen!";
        } else {
            return "Ja";
        }

    }

    /**
     * Provides a user friendly string representation of the product's current
     * status.
     *
     * @param currentDate the current date for calculating quality and price
     * @return a formatted string with the product's details
     */
    public String toUserInfo(LocalDate currentDate) {
        String expiryDateString = expiryDate.equals(LocalDate.MAX) ? "kein Ablaufdatum" : expiryDate.toString();

        return String.format("""
                             Produkt: %s
                             Artikelgruppe: %s
                             Grundpreis: %.2f Euro
                             Ablaufdatum: %s
                             Minimal zugelassene Qualitaet: %d
                             Aktuelle Qualitaetswertung: %d
                             Aktueller Tagespreis: %.2f Euro
                             Marktfaehig: %s""",
                name,
                productGroup.getUserFriendlyName(),
                basePrice,
                expiryDateString,
                productGroup.getProductRule().getLowestQualityBoundary(),
                getCurrentQuality(currentDate),
                getCurrentPrice(currentDate),
                marketableInfo(currentDate)
        );
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", basePrice=" + basePrice + ", expiryDate=" + expiryDate + ", startQuality=" + startQuality + ", storeDate=" + storeDate + ", productGroup=" + productGroup + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (Double.doubleToLongBits(this.basePrice) != Double.doubleToLongBits(other.basePrice)) {
            return false;
        }
        if (this.startQuality != other.startQuality) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.expiryDate, other.expiryDate);
    }

    // Getter / Setter
    /**
     * Calculates the current quality of the product based on the rules defined
     * in the associated ProductRuleSet. The calculation considers the elapsed
     * time since the product was placed in the shelf.
     *
     * - If the quality is unchanging, the initial quality is returned. - If the
     * quality increases, the quality is incremented based on the intervals and
     * a defined increase factor, up to a maximum limit. - If the quality
     * decreases, the quality is decremented based on the intervals and a
     * defined decrease factor.
     *
     * @param currentDate the current date to compare against the storage date
     * @return the calculated current quality
     */
    public int getCurrentQuality(LocalDate currentDate) {
        ProductRuleSet productRuleSet = productGroup.getProductRule();

        long daysBetweenStoreAndToday = ChronoUnit.DAYS.between(storeDate, currentDate);

        // Calculates the number of intervals since shelving during which the quality changes
        int qualityChangeIntervalls = 0;

        // Calculates the number of intervals since shelving during which the quality changes
        if (productRuleSet.getQualityChange() != ProductQualityChange.UNCHANGING) {
            qualityChangeIntervalls = (int) (daysBetweenStoreAndToday / productRuleSet.getDaysUntilQualityChange());
        }

        if (productRuleSet.getQualityChange() == ProductQualityChange.UNCHANGING) {
            return startQuality;
        }

        if (productRuleSet.getQualityChange() == ProductQualityChange.INCREASE) {
            currentQuality = startQuality + (qualityChangeIntervalls * productRuleSet.getQualityChangeFactor());

            if (currentQuality > productRuleSet.getHighestQualityBoundary()) {
                return productRuleSet.getHighestQualityBoundary();
            }

        }

        if (productRuleSet.getQualityChange() == ProductQualityChange.DECREASE) {
            currentQuality = startQuality - (qualityChangeIntervalls * productRuleSet.getQualityChangeFactor());
        }

        return currentQuality;

    }

    /**
     * Calculates the current price of the product based on its base price and
     * current quality. If the product's price changes daily, the price is
     * adjusted by adding a factor based on the quality. Additionally, if an
     * expiry discount is applicable and today is exactly one day before the
     * product's expiry date, the price is adjusted by subtracting a discount
     * factor from the base price with quality adjustments.
     *
     * @param currentDate the current date used to calculate the current quality
     * @return the current price of the product
     */
    public double getCurrentPrice(LocalDate currentDate) {
        ProductRuleSet productRuleSet = productGroup.getProductRule();

        double qualityPriceFactor = 0.10 * getCurrentQuality(currentDate);
        double basePriceWithQualityFactor = basePrice + qualityPriceFactor;

        int discountPercent = productRuleSet.getExpiryDiscountInPercent();
        double discountFactor = discountPercent / 100.0;
        double discountPriceFactor = basePriceWithQualityFactor * discountFactor;

        LocalDate oneDayBeforeExpiry = expiryDate.minusDays(1);
        boolean isOneDayBeforeExpiry = currentDate.isEqual(oneDayBeforeExpiry);

        if (productRuleSet.isDailyPrice() && productRuleSet.isExpiryDiscount() && isOneDayBeforeExpiry) {
            return basePriceWithQualityFactor - discountPriceFactor;
        } else if (productRuleSet.isExpiryDiscount() && isOneDayBeforeExpiry || expiryDate.isEqual(currentDate)) {
            return basePrice - discountPriceFactor;
        } else if (productRuleSet.isDailyPrice()) {
            return basePriceWithQualityFactor;
        } else {
            return basePrice;
        }
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getStartQuality() {
        return startQuality;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setStoreDate(LocalDate storeDate) {
        this.storeDate = storeDate;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public LocalDate getStoreDate() {
        return storeDate;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

}
