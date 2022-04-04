package test.rest

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.validation.ValidationException
import test.User
import test.UserService

@Secured(value=["hasRole('ROLE_ADMIN')"])
class UserRestController extends RestfulController<User>{
    static responseFormats = ['json', 'xml']
    UserRestController() {
        super(User)
    }

    UserService userService

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    @Override
    def index(Integer max) {
        //return super.index(max)
        render userService.list(params) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def show(Long id) {
        render userService.get(id) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def addItemIntoCart() {
        def jsonObject = request.JSON
        Long idUser = jsonObject.idUser
        Long idProduct = jsonObject.idProduct
        Integer quantity = jsonObject.quantity
        try {
            userService.addItemToCart(idUser, idProduct, quantity)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        //render userService.get(idUser) as JSON
        def result = [user_info:userService.get(idUser), cart:userService.get(idUser).cart, items_in_cart:userService.get(idUser).cart.items]
        render result as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def removeItemFromCart() {
        def jsonObject = request.JSON
        Long idUser = jsonObject.idUser
        Long idProduct = jsonObject.idProduct
        try {
            userService.removeItemFromCart(idUser, idProduct)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        //render userService.get(idUser) as JSON
        def result = [user_info:userService.get(idUser), cart:userService.get(idUser).cart, items_in_cart:userService.get(idUser).cart.items]
        render result as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def validateCart() {
        def jsonObject = request.JSON
        Long idUser = jsonObject.idUser
        try {
            userService.validateCart(idUser)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        //render userService.get(idUser) as JSON
        def result = [user_info:userService.get(idUser), cart:userService.get(idUser).cart, items_in_cart:userService.get(idUser).cart.items]
        render result as JSON
    }

}
