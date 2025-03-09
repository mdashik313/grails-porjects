package pos

class Sales {
    
    String priceType
    String saleBy

    Integer saleNo
    Integer totalAmmount
    Integer discount
    Integer grandTotal
    Integer cashReceived
    Integer collectionAmmount
    Date salesDate

    static constraints = {
        // customer_mobile_no size: 11..11
    }
}
