package de.gribovskij.supdupmarket;

import java.util.Objects;

/**
 * This class defines rules and boundarys for one product.
 *
 * @author Eugen Gribovskij
 */
public class ProductRuleSet {

    private final boolean expiring;

    private final ProductQualityChange qualityChange;

    // Set to true if the product price changes daily
    private final boolean dailyPrice;

    // Quality gain/loss factor then quality can change
    private final int qualityChangeFactor;

    /**
     * Defines the minimum quality threshold. If reached, the product must be
     * thrown away
     */
    private final int lowestQualityBoundary;

    /**
     * Defines the maximum quality threshold. If reached, the quality cannot
     * increase anymore.
     */
    private final int highestQualityBoundary;

    /**
     * Indicates how much days must pass before the quality can increase or
     * decrease
     */
    private final int daysUntilQualityChange;

    /**
     * True if product get a discount for being clode to the expiry date
     */
    private final boolean expiryDiscount;

    /**
     * The expiry discount in percent on the current price
     */
    private final int expiryDiscountInPercent;

    public ProductRuleSet(boolean expiring, ProductQualityChange qualityChange, boolean dailyPrice, int qualityChangeFactor, int lowestQualityBoundary, int highestQualityBoundary, int daysUntilQualityChange, boolean expiryDiscount, int expiryDiscountInPercent) {
        Objects.requireNonNull(qualityChange, "Must be initialized");
        this.expiring = expiring;
        this.qualityChange = qualityChange;
        this.dailyPrice = dailyPrice;
        this.qualityChangeFactor = qualityChangeFactor;
        this.lowestQualityBoundary = lowestQualityBoundary;
        this.highestQualityBoundary = highestQualityBoundary;
        this.daysUntilQualityChange = daysUntilQualityChange;
        this.expiryDiscount = expiryDiscount;
        this.expiryDiscountInPercent = expiryDiscountInPercent;
    }

    // Getter / Setter
    public boolean isExpiring() {
        return expiring;
    }

    public ProductQualityChange getQualityChange() {
        return qualityChange;
    }

    public boolean isDailyPrice() {
        return dailyPrice;
    }

    public int getQualityChangeFactor() {
        return qualityChangeFactor;
    }

    public int getLowestQualityBoundary() {
        return lowestQualityBoundary;
    }

    public int getHighestQualityBoundary() {
        return highestQualityBoundary;
    }

    public int getDaysUntilQualityChange() {
        return daysUntilQualityChange;
    }

    public boolean isExpiryDiscount() {
        return expiryDiscount;
    }

    public int getExpiryDiscountInPercent() {
        return expiryDiscountInPercent;
    }

}
