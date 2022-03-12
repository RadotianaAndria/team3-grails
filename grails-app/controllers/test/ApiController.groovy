package test

import grails.converters.JSON
import grails.converters.XML

class ApiController {

    def user()
    {
        switch(request.getMethod())
        {
            case "GET":
                if(!params.id)
                    return response.status = 400
                def userInstance = User.get(params.id)
                if (!userInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render userInstance as XML }
                    json { render userInstance as JSON }
                }
                break;
            case "PUT":
                break;
            case "PATCH":
                break;
            case "DELETE":
                break;
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    def users()
    {
        switch(request.getMethod())
        {
            case "GET":
                def userList = User.getAll()
                response.withFormat {
                    xml { render userList as XML }
                    json { render userList as JSON }
                }
                break;
            case "POST":
//                def userInstance = new User(request.getJSON())
                def userInstance = new User()
                userInstance.username = request.getJSON().username
                if (!userInstance.save(flush:true))
                    return response.status = 400
                return response.status = 201
                break;
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    def saleAd()
    {

    }

    def saleAds()
    {

    }
}