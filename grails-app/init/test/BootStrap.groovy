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
    }
    def destroy = {
    }
}
