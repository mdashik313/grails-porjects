package pos
import java.text.SimpleDateFormat

class SalesController {

    def index() {
        def sales = Sales.list()
        [sales:sales]
    }

    def home() {
        
    }

    def new_retail_sale() {
        
    }

    def save(){
        def sale = new Sales()
        
        sale.price_type = "WT Cash Price"
        sale.sale_by = params.sale_by

        Random sale_rand = new Random()
        // Generate a 3-digit random number (between 100 and 999)
        sale.sale_no = 100 + sale_rand.nextInt(900)
        sale.total_ammount = params.total_ammount?.toInteger()
        sale.discount = params?.discount?.toInteger()
        sale.grand_total = params.total_ammount?.toInteger() - params.discount?.toInteger()
        sale.cash_received = params.cash_received?.toInteger()
        sale.collection_amt = params.cash_received?.toInteger() - params.grand_total?.toInteger()

        Date date_today = new Date()
        sale.sales_date = date_today
        

        sale.customer_mobile_no = params.customer_mobile_no
        sale.customer_name = params.customer_name
        sale.customer_nid = params.customer_nid
        sale.customer_address = params.customer_address

        if(sale.save(flush:true)){ }
            
        redirect(action:"index")
    }

    def edit(Long id){
        redirect(action:"new_retail_sale")
    }

    def cancel() {
        redirect(action: "index")
    }

    def search_sale(){
        def search_result = Sales.list()

        if(params.sale_no){
            search_result = search_result.findAll{ result ->
            result.sale_no?.toString()?.contains(params.sale_no.trim())
            
            }
        }
        if(params.customer_name){
            search_result = search_result.findAll{ result ->
            result.customer_name?.toString()?.contains(params.customer_name.trim())
            
            }
        }
        if(params.customer_mobile_no){
            search_result = search_result.findAll{ result ->
            result.customer_mobile_no?.toString()?.contains(params.customer_mobile_no.trim())
            
            }
        }
        if(params.fromDate){
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
            
            search_result = search_result.findAll{ result ->

                Date fromDate = date_format.parse(params.fromDate) // Convert form input to Date

                date_format.parse(result.sales_date.toString()) >= fromDate
                //here converted yyy-mm-dd hh-mm-ss to yyyy-mm-dd because input form sending in yyyy-mm-dd fomat date             
            }
        }

        if(params.toDate){
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd")
            
            search_result = search_result.findAll{ result ->

                Date toDate = date_format.parse(params.toDate) // Convert form input to Date

                 date_format.parse(result.sales_date.toString()) <= toDate
                                
            }
        }
       
        
        println search_result
        render(view:"index", model:[sales:search_result])
    }
}
