package test

class CartItem {
    Product product;

    Integer quantity;
    BigDecimal totalPrice;

    Date creationDate;
    Date lastUpdatedDate;

    static constraints = {
        product unique: true
        creationDate nullable: true, blank: true
        lastUpdatedDate nullable: true, blank: true
    }

    static mapping = {
        quantity defaultValue: "0"
        totalPrice defaultValue: "0"
    }
}
