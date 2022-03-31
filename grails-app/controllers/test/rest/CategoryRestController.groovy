package test.rest

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import test.Category
import test.CategoryService

@Secured(value=["hasRole('ROLE_ADMIN')"])
class CategoryRestController extends RestfulController<Category>{
    static responseFormats = ['json', 'xml']
    CategoryRestController(){
        super(Category)
    }

    CategoryService categoryService

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    @Override
    def index(Integer max) {
        //return super.index(max)
        render categoryService.list(params) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def show(Long id) {
        render categoryService.get(id) as JSON
    }
}
