package pos

class Sales {
    
    String priceType
    String sellerId
    String invoiceNumber

    Integer saleNo
    Integer totalAmmount
    Integer discount
    Integer grandTotal
    Integer cashReceived
    Integer collectionAmmount
    Date salesDate

    static belongsTo = [customer: Customer]

    static constraints = {
        discount nullable: true
    }
}
