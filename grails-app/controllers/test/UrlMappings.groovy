package test

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/users" (resources: "user"){
            collection{
                "/addItemIntoCart"(controller: "user",action: "addItemIntoCart")
                "/removeItemFromCart"(controller: "user",action: "removeItemFromCart")
            }
        }
        "/api/products" (resources: "product"){
            collection{
                "/search"(controller: "product",action: "search")
            }
        }
        "/api/categories" (resources: "category")
        "/api/banners" (resources: "banner")


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
