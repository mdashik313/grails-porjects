<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>Product Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 0;
            justify-content: center;
            align-items: center;
        }
        .container {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            margin: auto;
        }
        .header {
            background: #f5f7f8;
            padding: 10px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 5px 5px 0 0;
        }

    </style>
</head>
<body>
<g:render template="/layouts/nav" />

<div class="container">
    <div style="background: #f5f7f8;
            padding: 10px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 5px 5px 0 0;">
            Product Details
    </div>
    <g:link style="text-decoration:none; margin: 8px;" action="index"> Product list </g:link>

    <table style="width: 100%; border:none;">
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Product Category :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">${product.product_group}</td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Model :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">${product.model}</td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Description :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">${product.description}</td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Created :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">
                <g:formatDate format="MMMM d, yyyy h:mm:ss a z" date="${product.created_date}" />
            </td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Created By :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">System</td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Modified :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">
                <g:formatDate format="MMMM d, yyyy h:mm:ss a z" date="${product.modified_date}" />
            </td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%; padding: 8px; ">Modified By :</td>
            <td style="text-align: left; width: 50%; padding: 8px; ">System</td>
        </tr>
    </table>

</body>
</html>
