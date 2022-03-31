package test

import grails.rest.Resource

class Product {
    String name
    Float price
    Integer inStock
    String description
    String photo

    Integer ranking = Math.abs(new Random().nextInt() % 100) + 1

    static belongsTo = [category: Category]

    static constraints = {
        name nullable: false, blank: false
        price min: 0F, scale: 2
        inStock nullable: false, blank: false
        ranking nullable: false
    }
}
