package test

class CartItem {
    Product product;

    Integer quantity;
    BigDecimal totalPrice;

    Date creationDate;
    Date lastUpdatedDate;

    Integer isValid;

    static constraints = {
        product unique: false
        creationDate nullable: true, blank: true
        lastUpdatedDate nullable: true, blank: true
        isValid inList: [0, 10, 20, 30]
    }

    static mapping = {
        quantity defaultValue: "0"
        totalPrice defaultValue: "0"
        isValid defaultValue: "0"
    }
}
