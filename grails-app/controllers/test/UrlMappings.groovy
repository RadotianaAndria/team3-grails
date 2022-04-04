package test

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        /*
        // BACK OFFICE
        "/api/users" (resources: "user"){
            collection{
                "/addItemIntoCart"(controller: "user",action: "add_item_into_cart")
                "/removeItemFromCart"(controller: "user",action: "remove_item_from_cart")
            }
        }
        "/api/products" (resources: "product"){
            collection{
                "/top5"(controller: "product",action: "top5")
                "/search"(controller: "product",action: "search")
            }
        }
        "/api/categories" (resources: "category")
        "/api/banners" (resources: "banner")
        */

        //API
        "/api/users" (resources: "userRest"){
            collection{
                "/addItemIntoCart"(controller: "userRest",action: "addItemIntoCart")
                "/removeItemFromCart"(controller: "userRest",action: "removeItemFromCart")
                "/validateCart"(controller: "userRest",action: "validateCart")
            }
        }
        "/api/products" (resources: "productRest"){
            collection{
                "/show/$id?"(controller: "productRest",action: "show")
                "/top5"(controller: "productRest",action: "top5")
                "/search"(controller: "productRest",action: "search")
            }
        }
        "/api/categories" (resources: "categoryRest")
        "/api/banners" (resources: "bannerRest")


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
