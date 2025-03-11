package pos
import java.text.SimpleDateFormat

class SalesController {

    static defaultAction = "list"

    def list() {
        def sales = Sales.getAll()
        [sales:sales]
    }

    def show(Long id) {
        def sale = Sales.get(id)
        [sale: sale]
    }

    def home() {
        
    }

    def new_retail_sale() {
        
    }

    def save(){

        Random randomNumber = new Random()
        Date date_today = new Date()
        def salesService

        def customer = new Customer(
            mobileNo: params.customer_mobile_no,
            name: params.customer_name,
            nId: params.customer_nid,
            address: params.customer_address,
            employeeId: params?.employee_id
        )

        if(!customer.save(flush: true)){
            flash.message = "Error saving customer!"
            redirect(action: "new_retail_sale")
            return
        }

        def sale = new Sales()
        
        sale.priceType = "WT Cash Price"
        sale.sellerId = params.sale_by
        // Generate a 3-digit random number (between 100 and 999)
        sale.saleNo = 100 + randomNumber.nextInt(900)
        sale.totalAmmount = params.total_ammount?.toInteger()
        sale.discount = params?.discount?.toInteger()
        sale.grandTotal = params.total_ammount?.toInteger() - params.discount?.toInteger()
        sale.cashReceived = params.cash_received?.toInteger()
        sale.collectionAmmount = params.cash_received?.toInteger() - params.grand_total?.toInteger()
        sale.salesDate = date_today
        // sale.invoiceNumber = salesService.getUniqueNumber().toString()
        sale.invoiceNumber = "14234343"

        sale.customer = customer

        if(!sale.save(flush: true)){
            flash.message = "Error saving sale!"
            redirect(action: "new_retail_sale")
            return
        }
            
        redirect(action:"list")
    }

    def edit(Long id){
        redirect(action:"new_retail_sale")
    }

    def cancel() {
        redirect(action: "index")
    }

    def search_sale(){
        def s = Sales.createCriteria()

        def results = s.list {
            if(params.sale_no){
                ilike("saleNo", "%${params.sale_no}%")  // Case-insensitive partial search
            }
            
            if(params.customer_name){
                customer {
                    ilike("name", "%${params.customer_name}%")
                }
            }
            if(params.customer_mobile_no){
                customer {
                    ilike("mobileNo", "%${params.customer_mobile_no}%")
                }
            }
            if(params.fromDate){
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
                Date fromDate = date_format.parse(params.fromDate) // Convert form input to Date object
                ge("salesDate", fromDate)
            }

            if(params.toDate){
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
                Date toDate = date_format.parse(params.toDate) // Convert form input to Date object
                le("salesDate", toDate)
            }
            println search_result
        }


        // def searchResult = Sales.getAll()

        // if(params.sale_no){
        //     search_result = search_result.findAll{ result ->
        //     result.sale_no?.toString()?.contains(params.sale_no.trim())
            
        //     }
        // }
        // if(params.customer_name){
        //     search_result = search_result.findAll{ result ->
        //     result.customer_name?.toString()?.contains(params.customer_name.trim())
            
        //     }
        // }
        // if(params.customer_mobile_no){
        //     search_result = search_result.findAll{ result ->
        //     result.customer_mobile_no?.toString()?.contains(params.customer_mobile_no.trim())
            
        //     }
        // }
        // if(params.fromDate){
        //     SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
            
        //     search_result = search_result.findAll{ result ->

        //         Date fromDate = date_format.parse(params.fromDate) // Convert form input to Date

        //         date_format.parse(result.sales_date.toString()) >= fromDate
        //         //here converted yyy-mm-dd hh-mm-ss to yyyy-mm-dd because input form sending in yyyy-mm-dd fomat date             
        //     }
        // }

        // if(params.toDate){
        //     SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
            
        //     search_result = search_result.findAll{ result ->

        //         Date toDate = date_format.parse(params.toDate) // Convert form input to Date

        //          date_format.parse(result.sales_date.toString()) <= toDate
                                
        //     }
        // }
       
        
        // println search_result
        render(view:"list", model:[sales:results])
    }
}
