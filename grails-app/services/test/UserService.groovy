package test

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional


interface IUserService {

    User get(Serializable id)

    List<User> list(Map args)

    Long count()

    void delete(Serializable id)

    User save(User user)



}
@Service(User)
@Transactional
abstract class UserService implements IUserService {
    def getIdUser(String username){
        return User.findByUsername(username);
    }

    def addItemToCart(Long idUser, Long idProduct, Integer quantity){
        //We get the user first
        User user = User.get(idUser)
        //Then we get his cart
        Cart cart = user.cart
        //Get the product
        Product product = Product.get(idProduct)
        //Is stock sold out ?
        if(product.inStock >= quantity) {
            //Let's initialize the values of the cart_item
            BigDecimal totalPrice = product.price*quantity
            def now = new Date()
            int validationByCustomer = 10
            CartItem cartItem = new CartItem(product: product, quantity: quantity, totalPrice: totalPrice, isValid: validationByCustomer)

            if(cart != null){
                def command = Cart.executeQuery("select item from Cart cart " +
                        "inner join cart.items item " +
                        "where cart.id = :idCart and item.product.id = :idProduct",
                        [idCart:cart.id, idProduct:idProduct])
                command.each { item ->
                    println("ITEM : {\n QTY : "+item.getAt("quantity")+"\n TP : "+item.getAt("totalPrice")+"\n}")
                }

                if(command){
                    Integer qty = quantity
                    BigDecimal cartItemTotalPrice = totalPrice
                    command.each { item ->
                        qty = qty+item.getAt("quantity")
                        cartItemTotalPrice = cartItemTotalPrice+item.getAt("totalPrice")
                    }
                    if(qty>0) {
                        CartItem.executeUpdate("UPDATE CartItem command SET command.quantity = :qty, command.totalPrice = :totalPrice, command.lastUpdatedDate = :lastUpdatedDate " +
                                "WHERE command.product.id = :idProduct",
                                [qty: qty, totalPrice: cartItemTotalPrice, lastUpdatedDate: new Date(), idProduct: product.id])
                        user.cart.totalPrice = user.cart.totalPrice + totalPrice
                        user.cart.isValid = validationByCustomer
                        user.cart.save(flush:true, failOnError: true)
                    } else {
                        println("Quantity must be larger than the one in the cart !!!\nOr just delete the item to remove it from the cart")
                    }
                } else {
                    if(quantity>0) {
                        cartItem.creationDate = now
                        cartItem.lastUpdatedDate = now
                        cartItem.save(failOnError: true)

                        user.cart.addToItems(cartItem)
                        user.cart.totalPrice = user.cart.totalPrice + totalPrice
                        user.cart.isValid = validationByCustomer
                        user.cart.save(flush:true, failOnError: true)
                    } else {
                        println("Quantity must be positive !!!")
                    }
                }
                //user.cart.save(flush:true)
            } else {
                if(quantity>0){
                    cartItem.creationDate = now
                    cartItem.lastUpdatedDate = now
                    cartItem.save(failOnError: true)


                    def myNewCart = new Cart(totalPrice: totalPrice, user:user, isValid: validationByCustomer)
                    myNewCart.addToItems(cartItem)
                    myNewCart.save(flush:true, failOnError: true)

                    user.cart = myNewCart
                    user.save(flush:true, failOnError: true)


                    println("USER CART : { \n   ITEMS : "+user.cart.items[0].product.name+"\n   TOTAL PRICE : "+user.cart.totalPrice+"\n}")
                } else {
                    println("Quantity must be positive !!!")
                }
            }
            //product.inStock = product.inStock - quantity
            //product.save(flush:true, failOnError: true)
        } else {
            println("Quantity in stock sold out !!!")
        }
    }

    def removeItemFromCart(Long idUser, Long idProduct){
        User user = User.get(idUser)
        Cart cart = user.cart
        def command = Cart.executeQuery("select item from Cart cart " +
                "inner join cart.items item " +
                "where cart.id = :idCart and item.product.id = :idProduct",
                [idCart: cart.id, idProduct: idProduct])
        if(command!=null){
            Long id = 0
            BigDecimal totalPrice = 0
            Integer isValid = 0
            int validationByBO = 20
            command.each { item ->
                id = item.getAt("id")
                totalPrice = item.getAt("totalPrice")
                isValid = item.getAt("isValid")
            }
            def commandToRemove = cart.items.find { it.id == id }

            int quantity = commandToRemove.quantity

            cart.removeFromItems(commandToRemove)
            user.cart.totalPrice = user.cart.totalPrice - totalPrice
            user.cart.save(flush:true, failOnError: true)
            CartItem.executeUpdate("DELETE FROM CartItem command WHERE command.id = :id", [id: id])

            //If the cart was already validate by the BO
            //Then let's update the stock in the product
            if(isValid < validationByBO){
                Product product = Product.get(idProduct)
                product.inStock = product.inStock + quantity
                product.save(flush:true, failOnError: true)
            }
        }
    }

    def validateCart(Long idUser){
        //We get the user first
        User user = User.get(idUser)
        //We get his corresponding cart
        Cart cart = user.cart
        //Loop the elements inside the cart
        Product product
        int validationByBO = 20
        //Valid all the command inside the cart
        for(CartItem command in cart.items){
            //If the command is not yet valid, then we proceed this following step
            if(command.isValid < validationByBO){
                product = Product.get(command.product.id)
                product.inStock -= command.quantity
                product.save(flush:true, failOnError: true)

                command.isValid = validationByBO
                command.save(flush:true, failOnError: true)
            }
        }
        //Valid the cart
        cart.isValid = validationByBO
        cart.save(flush:true, failOnError: true)
    }
}