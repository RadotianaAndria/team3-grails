package test

import grails.rest.Resource

class Category {

    String name

    static hasMany = [products: Product]

    static constraints = {
        name nullable: false, blank: false
    }
}
