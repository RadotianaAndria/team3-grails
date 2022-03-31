package test.rest

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import test.Banner
import test.BannerService

@Secured(value=["hasRole('ROLE_ADMIN')"])
class BannerRestController extends RestfulController<Banner> {
    static responseFormats = ['json', 'xml']
    BannerRestController() {
        super(Banner)
    }

    BannerService bannerService

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    @Override
    def index(Integer max) {
        //return super.index(max)
        render bannerService.list(params) as JSON
    }

    @Secured(value=["hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')"])
    def show(Long id) {
        render bannerService.get(id) as JSON
    }

}
