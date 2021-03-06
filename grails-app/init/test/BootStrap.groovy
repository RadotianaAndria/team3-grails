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
                def productInstance = new Product(name: productname, price: 4000, inStock: 5, description: "Tee shirt de bonne qualité", photo: "teeshirt.jpg")
                productInstance.category = category;
                productInstance.save()
        }
        def sweat = new Category(name: "Sweat shirt").save()
        ["Jordan XXL", "Nike M", "Adidas L"].each {
            def productname ->
                def productInstance = new Product(name: productname, price: 20000, inStock: 5, description: "Sweat shirt de bonne qualité", photo: "sweatshirt.jpg")
                productInstance.category = sweat;
                productInstance.save()
        }
        def pantalon = new Category(name: "Pantalon").save()
        ["Cargo 38", "Slim jean 41", "Jogging 36", "Jean délavé 40"].each {
            def productname ->
                def productInstance = new Product(name: productname, price: 20000, inStock: 5, description: "Pantalon de bonne qualité", photo: "pantalon.jpg")
                productInstance.category = pantalon;
                productInstance.save()
        }

        ["Banner numero 1", "Banner numero 2", "Banner numero 3", "Banner numero 4"].each {
            def bannerName ->
                def bannerInstance = new Banner(name: bannerName, created: "28/03/2022")
                bannerInstance.save()
        }

    }
    def destroy = {
    }
}
