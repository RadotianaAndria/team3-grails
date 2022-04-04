package test

class Cart {
    BigDecimal totalPrice;

    static hasMany = [items: CartItem];

    Integer isValid;

    static constraints = {
        items nullable: true, blank: true
        isValid inList: [0, 10, 20, 30]
    }
    static mapping = {
        totalPrice defaultValue: "0"
        items cascade: 'all'
        isValid defaultValue: "0"
    }
}
