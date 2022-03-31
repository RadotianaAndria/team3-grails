package test.rest

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import test.Product
import test.ProductService

@Secured(value=["hasRole('ROLE_ADMIN')"])
class ProductRestController extends RestfulController<Product> {
    static responseFormats = ['json', 'xml']
    ProductRestController() {
        super(Product)
    }

    ProductService productService

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    @Override
    def index(Integer max) {
        //return super.index(max)
        render productService.list(params) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def show(Long id) {
        render productService.get(id) as JSON
    }


    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def top5() {
        def max = params.max ?: 5
        render productService.getTop(max) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def search(){
        def jsonObject = request.JSON
        def keyword = jsonObject.keyword
        render productService.search(keyword) as JSON
    }


}
