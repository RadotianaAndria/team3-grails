package test

import grails.gorm.services.Service

import javax.servlet.http.HttpServletRequest

@Service(Banner)
interface BannerService {

    Banner get(Serializable id)

    List<Banner> list(Map args)

    Long count()

    void delete(Serializable id)

    Banner save(Banner banner)
}