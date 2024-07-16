package de.gribovskij.supdupmarket;

/**
 * Represents a category of products governed by specific rules and constraints.
 * Each {@code ProductGroup} applies a set of rules from a
 * {@link ProductRuleSet} that dictate how products are managed within the
 * group.
 * 
 * @author Eugen Gribovskij
 */
public class ProductGroup {

    private final String name;

    // The name for the product, the user can better relate to
    private final String userFriendlyName;

    private ProductRuleSet productRuleSet;

    public ProductGroup(String name, String userFriendlyName, ProductRuleSet productRule) {
        this.name = name;
        this.userFriendlyName = userFriendlyName;
        this.productRuleSet = productRule;
    }

    // Getter / Setter
    public String getName() {
        return name;
    }

    public ProductRuleSet getProductRule() {
        return productRuleSet;
    }

    public void setProductRule(ProductRuleSet productRule) {
        this.productRuleSet = productRule;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }

}
