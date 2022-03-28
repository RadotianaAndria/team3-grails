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

    }
    def destroy = {
    }
}
