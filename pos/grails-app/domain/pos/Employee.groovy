package pos

class Employee {
    String name
    String phone
    String document
    
    static constraints = {
        document unique: true
    }
}
