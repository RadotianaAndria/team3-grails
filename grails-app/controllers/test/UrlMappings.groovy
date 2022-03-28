package test

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/users" (resources: "user")
        "/api/products" (resources: "product")
        "/api/categories" (resources: "category")
        "/api/banners" (resources: "banner")


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
