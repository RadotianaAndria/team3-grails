package test

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(value=["hasRole('ROLE_ADMIN')"])
class UserController {

    UserService userService
    ProductService productService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }

    def cart(Long id) {
        [ id_user:id, cart:userService.get(id).cart, products:productService.list(params)]
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        User userToDelete = userService.get(id)
        UserRole.findAllByUser(userToDelete)*.delete()

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def addItemIntoCart(Long idUser, Long idProduct, Integer quantity) {
        try {
            userService.addItemToCart(idUser, idProduct, quantity)
            println("ID : "+idUser+"\nPRODUCT : "+idProduct+"\nQTY : "+quantity)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render(view:"user/cart", model:[id_user:idUser, cart:userService.get(idUser).cart, isAddingItemIntoCart: false,products:productService.list(params)]) }
        }

    }

    def removeItemFromCart(Long idUser, Long idProduct){
        try {
            userService.removeItemFromCart(idUser, idProduct)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render(view:"user/cart", model:[id_user:idUser, cart:userService.get(idUser).cart, isAddingItemIntoCart: false,products:productService.list(params)]) }
        }
    }

    def validateCart(Long idUser){
        try {
            userService.validateCart(idUser)
        } catch (ValidationException e) {
            println(e.getMessage())
            return
        }
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render(view:"user/cart", model:[id_user:idUser, cart:userService.get(idUser).cart, isAddingItemIntoCart: false,products:productService.list(params)]) }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
