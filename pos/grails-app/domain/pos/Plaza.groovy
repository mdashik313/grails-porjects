package pos

class Plaza {

    String name
    String mobile_num
    String email
    String address 
    Integer plaza_id

    static constraints = {
        mobile_num size: 11..11
        email email: true, blank: true, nullable: true
        address maxSize: 30, blank: false 
        plaza_id size: 5..5
    }
}
