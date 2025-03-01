package pos

class Sales {
    
    String price_type
    String sale_by

    Integer sale_no
    Integer total_ammount
    Integer discount
    Integer grand_total
    Integer cash_received
    Integer collection_amt
    Date sales_date

    String customer_mobile_no
    String customer_name
    String customer_nid
    String customer_address
    
    


    static constraints = {
        // customer_name nullable : true
        // sale_type nullable : true
        // price_type nullable : true
        // grand_total nullable : true
        // cash_received nullable : true
        // collection_amt nullable : true
        // sales_date nullable : true
        
        
    }
}
