package pos

class Product {

    String model 
    String organization
    Integer pos_product_id
    Integer code
    String active = false 
    String description
    String product_group
    Date created_date
    Date modified_date

    static constraints = {
        modified_date nullable : true
    }
}

