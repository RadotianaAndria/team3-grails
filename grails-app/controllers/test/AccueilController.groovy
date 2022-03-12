package test

import grails.plugin.springsecurity.annotation.Secured

@Secured(value=["hasRole('ROLE_CLIENT')"])
class AccueilController {

    def index() { }
}
