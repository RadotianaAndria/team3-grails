package test

import java.time.LocalDate

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: "ROLE_ADMIN").save()
        def clientRole = new Role(authority: "ROLE_CLIENT").save()

        def admin = new User(username: "admin", password: "admin").save()

        UserRole.create(admin, adminRole)

        ["Rado", "Sambatra", "Natefy", "Loic"].each {
            def username ->
                def userInstance = new User(username: username, password: "password")
                userInstance.save()

                UserRole.create(userInstance, clientRole)

                UserRole.withSession {
                    it.flush()
                    it.clear()
                }
        }
        def category = new Category(name: "Tee shirt").save()
        ["Marvel M", "DC XS", "LoL M", "Star wars XL", "Disney L", "Code S"].each {
            def productname ->
                def productInstance = new Product(name: productname, price: 4000, inStock: 12, description: "Tee shirt de bonne qualité", photo: "teeshirt.jpg")
                productInstance.category = category;
                productInstance.save()
        }
        def sweat = new Category(name: "Sweat shirt").save()
        ["Air Jordan XXL", "Nike M", "Adidas L"].each {
            def productname ->
                def productInstance = new Product(name: productname, price: 20000, inStock: 5, description: "Sweat shirt de bonne qualité", photo: "sweatshirt.jpg")
                productInstance.category = sweat;
                productInstance.save()
        }
        def pantalon = new Category(name: "Pantalon").save()
        ["Pantalon Carotte 38", "Slim jean 41", "Pantalon velour 36", "Jean délavé 40"].each {
            def productname ->
                def productInstance = new Product(name: productname, price: 20000, inStock: 5, description: "Sweat shirt de bonne qualité", photo: "sweatshirt.jpg")
                productInstance.category = pantalon;
                productInstance.save()
        }

    }
    def destroy = {
    }
}
