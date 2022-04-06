package test

import grails.converters.JSON
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional


interface IProductService {

    Product get(Serializable id)

    List<Product> list(Map args)

    Long count()

    void delete(Serializable id)

    Product save(Product product)

}
@Service(Product)
@Transactional
abstract class ProductService implements IProductService {
    def getTop(def max) {
        return Product.executeQuery("from Product where inStock > 0 order by ranking DESC", [max: max])
    }

    def search(def keyword){
        def criteria = Product.createCriteria()
        def resultList = criteria.list(max: 4){
            or{
                like('name', '%'+keyword+'%')
                like('description','%'+keyword+'%')
                category{
                    like('name','%'+keyword+'%')
                }
            }
        }
        return resultList
    }

}