
<!DOCTYPE html>
<html>
<head>
    

  <g:render template="/layouts/heading_pos"/>
  <meta charset="UTF-8">
  <title>Retail Sale</title>
  
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f2f2f2; /* Light gray background */
    }

    .page-container {
      width: 80%;
      margin: 20px auto;
      background-color: #fff;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 20px;
    }

    /* Header styling */
    .header-title {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 10px;
      border-bottom: 1px solid #ddd;
      padding: 10px;
      color: #333;
      background-color: #f2f2f2;
      border: 1px solid #ddd;
      text-align:left;
    }

    

  </style>
</head>
<body>

  <!-- Main Container -->
  <div class="page-container">
    
    <!-- Header Section -->
    <div class="header-title">Retail Sale</div>
    <g:render template="/layouts/sales_nav" />

    <h3 style="background-color: #6097ab;
      color: white;
      padding: 6px 15px;
      font-weight: normal;
      margin: 0;
      width: max-content;
      font-size: 16px;"> Customer Information </h3> <br>

    <g:form controller="sales" action="save" method="POST" style="display: flex; flex-direction: column; gap: 5px;" >

        <label for="mobile_no"> Mobile No: </label>
        <input type="text" name="customer_mobile_no"  />
        
        <label> Employee ID:(if customer is walton employee) </label>
        <input type="text" name="employee_id"  /> 

        <label for=""> Customer Name: </label>
        <input type="text" name="customer_name" />

        <label for=""> Customer Address: </label>
        <input type="text" name="customer_address"  /> 
        
        <label for=""> Customer National ID: </label>
        <input type="text" name="customer_nid"  /> 
        
        <label for=""> Sale By: </label>
        <input type="text" name="sale_by"  /> 
        
        <label for=""> Offer: </label>
        <g:select name="offer" from="['Eid offer', 'New year offer']" noSelection="['':'Not applicable']"  /> <br>
        
        <h3 style="background-color: #6097ab;
            color: white;
            padding: 6px 15px;
            font-weight: normal;
            margin: 0;
            width: max-content;
            font-size: 16px;"> Cash Calculation </h3> <br>

        <label for=""> Total: </label>
        <input type="number" name="total_ammount" required /> <br>

        <label for=""> Trade Discount: </label>
        <input type="number" name="discount" value="0"  /> <br>

        <label for=""> Grand Total: </label>
        <input type="number" name="grand_total" required  /> <br>

        <label for=""> Cash Received: </label>
        <input type="number" name="cash_received" required  /> <br>

        
        <button type="submit"> 
            Save
        </button>
    </g:form>
   
    
  </div>

</body>
</html>





































<%-- <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retail Sale</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            border: 1px solid #ddd;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }
        
        .header {
            background-color: #f0f0f0;
            padding: 10px 20px;
            border-bottom: 1px solid #ddd;
            font-size: 20px;
            font-weight: bold;
        }
        
        .section-header {
            background-color: #6097ab;
            color: white;
            padding: 8px 20px;
            margin-top: 0;
            font-weight: normal;
        }
        
        .form-section {
            background-color: #e6f2f7;
            padding: 15px 20px;
            border-bottom: 1px solid #ddd;
        }
        
        .form-row {
            display: flex;
            margin-bottom: 15px;
            align-items: center;
        }
        
        .form-label {
            width: 200px;
            font-weight: bold;
        }
        
        .form-input input, .form-input select {
            padding: 6px;
            width: 350px;
            border: 1px solid #ccc;
        }
        
        .form-input input[type="radio"] {
            width: auto;
            margin-right: 5px;
        }
        
        .required {
            color: red;
            margin-left: 5px;
        }
        
        .note {
            font-size: 12px;
            color: #666;
            margin-top: 2px;
        }
        
        .search-container {
            text-align: center;
            padding: 20px;
        }
        
        .search-box {
            padding: 10px;
            width: 70%;
            border: 1px solid #ddd;
            border-radius: 20px 0 0 20px;
            outline: none;
            background-color: #fff8e1;
        }
        
        .search-button {
            padding: 10px 20px;
            background-color: #2a6b8a;
            color: white;
            border: none;
            border-radius: 0 20px 20px 0;
            cursor: pointer;
        }
        
        .barcode-link {
            margin-left: 10px;
            color: blue;
            text-decoration: underline;
        }
        
        .radio-group {
            display: flex;
            justify-content: center;
            margin-top: 10px;
            gap: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">Retail Sale</div>
        
        <h3 class="section-header">Customer Information</h3>
        
        <div class="form-section">
            <div class="form-row">
                <div class="form-label">Is Group Customer?</div>
                <div class="form-input">
                    <input type="radio" name="groupCustomer" id="yes" value="yes">
                    <label for="yes">Yes</label>
                    <input type="radio" name="groupCustomer" id="no" value="no" checked>
                    <label for="no">No</label>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-row">
                <div class="form-label">
                    Mobile No: <span class="required">*</span>
                    <div class="note">(Customer's 11 digit valid mobile number)</div>
                </div>
                <div class="form-input">
                    <input type="text" required>
                </div>
                
                <div class="form-label" style="margin-left: 20px;">
                    Employee ID:
                    <div class="note">(If the customer is Walton Group Employee)</div>
                </div>
                <div class="form-input">
                    <input type="text" style="background-color: #fff8e1;">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    Customer Name: <span class="required">*</span>
                </div>
                <div class="form-input">
                    <input type="text" required>
                </div>
                
                <div class="form-label" style="margin-left: 20px;">
                    Customer Address: <span class="required">*</span>
                </div>
                <div class="form-input">
                    <input type="text" required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    Customer National ID:
                </div>
                <div class="form-input">
                    <input type="text" style="background-color: #fff8e1;">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    Sale By: <span class="required">*</span>
                    <div class="note">(Mention Sale Man ID)</div>
                </div>
                <div class="form-input">
                    <input type="text" required>
                </div>
                
                <div class="form-label" style="margin-left: 20px;">
                    Purchaser Bin:
                </div>
                <div class="form-input">
                    <input type="text" style="background-color: #fff8e1;">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">Offer</div>
                <div class="form-input">
                    <select style="background-color: #fff8e1;">
                        <option>-Select One-</option>
                    </select>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">Vendor</div>
                <div class="form-input">
                    <select style="background-color: #fff8e1;">
                        <option>Please Select Vendor</option>
                    </select>
                </div>
            </div>
        </div>
        
        <div class="form-section">
            <div class="form-row">
                <div class="form-label">Student/Teacher</div>
                <div class="form-input">
                    <select>
                        <option>Non-Student</option>
                        <option>Student</option>
                        <option>Teacher</option>
                    </select>
                </div>
            </div>
        </div>
        
        <div class="search-container">
            <div>Scan Barcode:</div>
            <input type="text" class="search-box">
            <button class="search-button">Search</button>
            <a href="#" class="barcode-link">Check Old Barcode</a>
            
            <div class="radio-group">
                <div>
                    <input type="radio" name="packageType" id="individual" checked>
                    <label for="individual">Individual</label>
                </div>
                <div>
                    <input type="radio" name="packageType" id="package">
                    <label for="package">Package</label>
                </div>
            </div>
        </div>
    </div>
</body>
</html> --%>

