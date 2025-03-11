
<!DOCTYPE html>
<html>
<head>
    

  <g:render template="/layouts/heading_pos"/>
  <meta charset="UTF-8">
  <title>Sales Details</title>
  
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
    <div class="header-title">Sales Details</div>
    <g:render template="/layouts/sales_nav" />

    

    <g:form controller="sales" action="save" method="POST" style="display: flex; flex-direction: column;" >
        <h3 style="background-color: #6097ab;
            color: white;
            padding: 6px 15px;
            font-weight: normal;
            margin: 0;
            width: max-content;
            font-size: 16px;">

            Customer Information 
        </h3> 

        <table style="background: #eaf4ff; padding: 5px;">
            <tr> 
                <td> Plaza Name: </td>
                <td> Walton Plaza-Chandra </td>
            </tr>
            <tr>
                <td>Customer ID:</td>
                <td> <input type="text" name="customersId" value="${sale.id}" disabled="disabled" /> </td>
                <td> <input type="text" name="customerType" value="Retail" disabled="disabled" /> </td>
                <td>Customer Code/AC:</td>
                <td> <input type="text" name="customerCode"  value="202502006" disabled="disabled" /> </td>
                <td> <input type="hidden" name="" /><input type="checkbox" name="exist" checked="checked" value="" disabled="disabled"  /> Exist? </td>
            </tr>
            <tr>
                <td>Customer Name:</td>
                <td><input type="text" name=""  value="${sale.customer.name}" disabled="disabled" /></td>
                <td>Invoice Number:</td>
                <td><input type="text" name=""  value="${sale.invoiceNumber}"  disabled="disabled" /></td>
            </tr>
            <tr>
                <td>Customer Photo:</td>
                <td>...</td>
                <td>Invoice Time:</td>
                <td><input  type="text" name="" value="${g.formatDate(format: 'MMMM d, yyyy h:mm a', date: sale.salesDate)}" disabled="disabled" /></td>
            </tr>
            <tr>
                <td>Emp ID[If Employee of Walton Group]:</td>
                <td><input type="text" name=""  value="${sale.customer.employeeId}" disabled="disabled" />
                </td>
                <td>National ID:</td>
                <td><input type="text" name=""   value="${sale.customer.nId}" disabled="disabled" /></td>

            </tr>
            <tr>
                <td>Present Address:</td>
                <td><input type="text" name="" value="${sale.customer.address}" disabled="disabled" /></td>
                <td>Mobile No:</td>
                <td><input type="text" name="" value="${sale.customer.mobileNo}" disabled="disabled" /></td>

            </tr>
            
            
            <tr>
                <td>Investigator ID:</td>
                <td><input type="text" name=""  value="" disabled="disabled" /></td>
                <td>Investigation Note:</td>
                <td><textarea name="" disabled="disabled" ></textarea></td>
            </tr>
            
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table> <br>

        <table class="table-style">
            <thead>
                <tr>
                    <th width="35" scope="col">Sl No.</th>
                    <th scope="col">Product Description</th>
                    <th scope="col">Barcode/Serial No.</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Unit Price</th>
                    <th scope="col">Amount</th>
                    <th scope="col">Discount(%)</th>
                    <th scope="col">Discount</th>
                    <th scope="col">Total Amount (Including VAT)</th>
                    <th width="50" scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr><td colspan="10" align="center">Please add some item to sale</td></tr>
            </tbody>
        </table>
        
        <br>

        <h3 style="background-color: #6097ab;
            color: white;
            padding: 6px 15px;
            font-weight: normal;
            margin: 0;
            width: max-content;
            font-size: 16px;"> Sales Information </h3>

        <table style="background: #eaf4ff; padding: 8px;">
            <tr>
                <td> <label>Total:</label> </td>
                <td> <input type="text" value="${sale.totalAmmount}" disabled="disabled"> </td>
                <td><label>Price Type:</label></td>
                <td><input type="text" value="${sale.priceType}" disabled="disabled"></td>
            </tr>
            <tr>
                <td><label>Gain on MRP:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>

                <td><label>Sales Type:</label></td>
                <td><input type="text" value="Cash" disabled="disabled"></td>
            </tr>
            <tr>
                <td><label>Rebate:</label></td>
                    <td><input type="text" value="0" disabled="disabled"></td>
                    <td><label>Cash Received (DP):</label></td>
                    <td><input type="text" value="${sale.cashReceived}" disabled="disabled"></td>
            </tr>

            <tr>
                <td><label>Other [+/-]:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>
                <td><label>Current Balance:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>
            </tr>

            <tr>
                <td><label>VAT:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>
                <td><label>Credit Limit:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>
            </tr>
           
            
            <tr>
                <td><label>Grand Total:</label></td>
                <td><input type="text" value="${sale.grandTotal}" disabled="disabled"></td>
                <td><label>Processing Fee:</label></td>
                <td><input type="text" value="0" disabled="disabled"></td>
            </tr>
            <tr>
                <td><label>Collected Amount:</label></td>
                <td><input type="text" value="${sale.collectionAmmount}" disabled="disabled"></td>
                <td><label>Next Installment Date:</label></td>
                <td><input type="text" disabled="disabled"></td>
                
            </tr>
             <tr>
                
                <td><label>Total Collection:</label></td>
                <td><input type="text" value="${sale.collectionAmmount}" disabled="disabled"></td>
                <td><label>Remarks:</label></td>
                <td><input type="text" disabled="disabled"></td>
            </tr>
                
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
        
        
    </g:form>
   
    
  </div>

</body>
</html>

