package pos
import java.text.SimpleDateFormat
import java.util.TimeZone

import groovy.sql.Sql
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate


class ProductController {

    def index() {
        def products = Product.list()  // Fetch all products
        def groupedProducts = products.groupBy{it.product_group}
        [groupedProducts:groupedProducts]
    }

    def create(){

    }

    def save(){
        def product = new Product(params)

        def date = new Date()  // Current date and time

        product.created_date = date

        if(product.save(flush:true)){
            redirect(action:"index")
        }
    }

    def view_details(Long id){
        def product = Product.get(id)
        [product:product]
    }

    def edit(Long id) {
        def product = Product.get(id)
        if(!product){
            flash.message = "Product not found"
            redirect(action:"index")
        }
        
        [product:product]
    }

    def update(Long id){
        if(params.cancel) { 
            redirect(view:"index")
            return
        }

        def product = Product.get(id)

        if(product){
            product.properties = params

            def date = new Date()
            product.modified_date = date

            product.save(flush:true)
            flash.message = "Product update successful"
        }
        else{
            flash.message = "Product not found"
        }
        redirect(action:"index")
    }

    def delete(Long id) {
        def product = Product.get(id)
        if(product){
            product.delete(flush:true)
        }
        else{
            flash.message = "Product not found"
        }
        redirect(action:"index")
    }

    def search_product(String query){
       query = query?.trim()?.toLowerCase()
    
        def allProducts = Product.list() // Get all products from the database

        def products = allProducts.findAll { product ->
            // Convert all fields to string and check if they contain the query
            product.model?.toLowerCase()?.contains(query) ||
            product.organization?.toLowerCase()?.contains(query) ||
            product.description?.toLowerCase()?.contains(query) ||
            product.product_group?.toLowerCase()?.contains(query) ||
            product.code?.toString()?.contains(query) ||
            product.pos_product_id?.toString()?.contains(query) ||
            product.active?.toLowerCase()?.contains(query)
        }

        // Print search results to console
        println "Search Results:"
        products.each { row ->
            println row // Print each row of the result
        }

        def groupedProducts = products.groupBy{it.product_group}

        render(view:"index", model:[groupedProducts: groupedProducts] ) 
    }
}
