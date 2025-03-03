<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link rel="shortcut icon" href="/pos/static/images/favicon1.ico" type="image/x-icon">
<div class="heading-walton"> Walton POS System </div>

<div class="pos-nav"> 
        <ul style=" width: 80%; margin: 20px auto; margin-top: 0;  padding-left: 0; padding-top: 7px; padding-bottom: 7px;">
            <li><a href="/pos/sales/home"> Home </a> </li>
            <li><a href="/pos/sales/index"> Sales List</a> </li>
            <li><a href="/pos/sales/index"> Plaza List</a></li> 
            <li><a href="/pos/employee/index"> Upload File </a> </li>
        </ul>
</div>

<style>
    
     body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f2f2f2; /* Light gray background */
    }

    .pos-nav {
        background-color:rgb(79, 76, 76);
        margin-top: 0;
        
    }
    .pos-nav ul {
        list-style-type: none;
        display: flex;
        flex-direction: row;
        gap: 10px;
    }
     .pos-nav ul li {
        
    }
    .pos-nav ul li a:hover {
        background:rgb(0, 0, 0);
       
    }
    .pos-nav ul li a {
        color: #ffffff;
        text-decoration: none;
        padding: 5px;
        padding-top: 7px; padding-bottom: 7px;
        font-size: 15px;
    }
    
    

    .heading-walton {
            background: linear-gradient(to bottom, #ffffff, #d9e4f1);
            padding: 25px;
            text-align: left;
            font-size: 24px;
            font-weight: bold;

    }

    .sales-nav-sub {
        margin-left: 5px;
       
    }
    .sales-nav-sub ul {
        list-style-type: none; /* Remove default list style */
        padding: 0;
        margin-bottom: 20px;
        display: flex; /* Align items in a row */
    }

    .sales-nav-sub ul li {
        margin-right: 20px; /* Space between the items */
    }

    .sales-nav-sub ul li a {
        text-decoration: none; /* Remove underline */
        color: black; /* Change text color to black */
        font-weight: normal;
        padding: 4px;
        font-size: 13px;
        
    }
    .sales-nav-sub ul li a:hover{
        background-color:rgb(156, 146, 146);
        border-radius: 5px;
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