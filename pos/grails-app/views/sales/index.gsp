
<!DOCTYPE html>
<html>
<head>
    

  <g:render template="/layouts/heading_pos"/>
  <meta charset="UTF-8">
  <title>Sales List</title>
  
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

    /* Table styling */
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }

    thead tr {
      background-color: #94c1e0; /* Header row color */
      color: #fff;              /* Header text color */
    }

    th, td {
      border: 1px solid #ccc;
      padding: 8px 12px;
      text-align: left;
    }

    /* Highlight first column */
    td:first-child {
    }

    /* Alternate row color */
    tbody tr:nth-child(even) {
      background-color:rgb(230, 236, 237);
    }

    /* Hover effect */
    tbody tr:hover {
      background-color: #FFFFED;
    }

    

  </style>
</head>
<body>

  <!-- Main Container -->
  <div class="page-container">
    
    <!-- Header Section -->
    <div class="header-title">Sales List</div>
    <g:render template="/layouts/sales_nav" />

    <div >
      <form action="search_sale" method="get" >
          <table border="0"  >
              <tr>
                  <td >Sale No:</td>
                  <td><input type="text" name="sale_no" value=""  id="code" /></td>
                  <td>Customer Name:</td>
                  <td><input type="text" name="customer_name" value="" /></td>
                  <td >Customer Mobile No:</td>
                  <td><input type="text" name="customer_mobile_no" value=""  /></td>
              </tr>
              <tr>
                  <td>Plaza:</td>
                  <td>
                      
                          <g:select name="plaza_id" from="['Dhaka Palaza','Barishal Plaza']" />

                      
                  </td>
                  <td >From date:</td>
                  <td><input type="date" name="fromDate"  value="" /></td>
                  <td class="label">To date:</td>
                  <td><input type="date" name="toDate"  value=""  /></td>
              </tr>
              <tr>
                  <td style="text-align:right;" colspan="6">
                    <input name="" style="padding: 10px;
                    color: white;
                    background-color:rgb(150, 148, 148);
                    border: 1px solid #ddd; cursor:pointer" type="submit" value="Search"/>
                  </td>
              </tr>
          </table>
      </form>
    </div>

    <!-- Sales Table -->
    <table>
      <thead>
        <tr>
          <th>Sale No</th>
          <th>Customer</th>
          <th>Sales Type</th>
          <th>Price Type</th>
          <th>Grand Total</th>
          <th>Cash Received</th>
          <th>Collected Amt</th>
          <th>Sales Date</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <!-- Example rows -->
        <g:each var="sale" in="${sales}" >
        
          <tr>
            <td>${sale.sale_no}</td>
            <td>${sale.customer_name}</td>
            <td>Cash</td>
            <td>${sale.price_type}</td>
            <td>${sale.grand_total}</td>
            <td>${sale.cash_received}</td>
            <td>${sale.collection_amt}</td>
            <td>${sale.sales_date}</td>
            <td>
              <g:link controller="sales" action="edit" id="${sale.id}" style="border: none; background: transparent; cursor: pointer;">
                <i class="fas fa-clipboard"></i>
              </g:link>
            </td>
          </tr>
        </g:each>
        <!-- More rows as needed... -->
      </tbody>
    </table>
    
  </div>

</body>
</html>
