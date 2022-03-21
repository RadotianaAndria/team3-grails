package test

class Category {

    String name

    static hasMany = [products: Product]

    static constraints = {
        name nullable: false, blank: false
    }
}
