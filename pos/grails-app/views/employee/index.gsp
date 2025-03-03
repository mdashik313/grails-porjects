
<!DOCTYPE html>
<html>
<head>
    

  <g:render template="/layouts/heading_pos"/>
  <meta charset="UTF-8">
  <title>Upload</title>
  
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
    <div class="header-title">Upload</div>

        <g:uploadForm controller="employee" action="add_image_reduce_resolution_and_bytes">
            <label> Name: </label> 
            <input type="text" name="name" /> <br>

            <label> Phone: </label> 
            <input type="text" name="phone" /> <br>
            
            <input type="file" name="uploadedImage" /> <br>
            <input type="submit" />
        </g:uploadForm>

        <table>
          <tr>
            <td> Name </td>
            <td> Phone </td>
            <td> Document </td>
          </tr>

          <g:each var="employee" in="${employees}" >
            <tr>
              <td> ${employee.name} </td>
              <td> ${employee.phone} </td>
              <td> <img src="${createLinkTo(dir: 'images/employee/', file: employee.document)}" /> </td>
          </tr>
          </g:each>

        </table>
    </div>
    
  </div>

</body>
</html>
