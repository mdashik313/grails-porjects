package pos

class Customer {
    String mobileNo
    String name
    String nId
    String address

    Integer employeeId

    static hasMany = [sales: Sales]
    
    static constraints = {
        employeeId nullable: true
    }
}
