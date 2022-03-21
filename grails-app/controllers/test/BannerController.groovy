package test

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.joda.time.DateTime

import javax.servlet.http.HttpServletRequest

import static org.springframework.http.HttpStatus.*
@Secured(value=["hasRole('ROLE_ADMIN')"])
class BannerController {

    BannerService bannerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bannerService.list(params), model:[bannerCount: bannerService.count()]
    }

    def show(Long id) {
        respond bannerService.get(id)
    }

    def create() {
        respond new Banner(params)
    }

    def save(Banner banner) {
        if (banner == null) {
            notFound()
            return
        }

        try {
//            if(banner.created == null) banner.setCreated(new DateTime(2022,03,16,18,04))
            bannerService.save(banner)
//            uploadImage(banner, request)
        } catch (ValidationException e) {
            e.printStackTrace()
            respond banner.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'banner.label', default: 'Banner'), banner.id])
                redirect banner
            }
            '*' { respond banner, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond bannerService.get(id)
    }

    def update(Banner banner) {
        if (banner == null) {
            notFound()
            return
        }

        try {
            bannerService.save(banner)
        } catch (ValidationException e) {
            respond banner.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'banner.label', default: 'Banner'), banner.id])
                redirect banner
            }
            '*'{ respond banner, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bannerService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'banner.label', default: 'Banner'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'banner.label', default: 'Banner'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }


//    def uploadImage(Banner banner, HttpServletRequest request){
//        if(request.getFile("bannerImage") && !request.getFile("bannerImage").filename.equals("")){
//            String image = FileUtil.uploadBannerImage(request.getFile("bannerImage"))
//            if(!image.equals("")){
//                banner.name = image
//                banner.save(flush:true)
//            }
//        }
//    }
}
