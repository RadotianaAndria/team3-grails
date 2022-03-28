package test

class Cart {
    BigDecimal totalPrice;

    static hasMany = [items: CartItem];

    //static belongsTo = [user : User]

    static constraints = {
        items nullable: true, blank: true
        //user unique: true
    }
    static mapping = {
        totalPrice defaultValue: "0"
        items cascade: 'all'
    }
}
