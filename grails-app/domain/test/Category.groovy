package test

import grails.rest.Resource

@Resource
class Category {

    String name

    static hasMany = [products: Product]

    static constraints = {
        name nullable: false, blank: false
    }
}
