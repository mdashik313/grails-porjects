<html>
    <head> 
        <meta name="layout" content="main"/>
    </head>

    <body>
        <g:render template="/layouts/nav" /> <br>

        <div style="background: #f5f7f8;
            padding: 10px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 5px 5px 0 0;">  Product List
        </div>

        <div style="display: flex; justify-content: center; align-items: center; margin:10px">
        <g:form style="display: flex; align-items: center;" controller="product" action="search_product" method="GET">
            <input type="text" name="query" required 
                style="background: white; padding: 10px; border-radius: 20px 0 0 20px; outline: none;" 
                placeholder="Search product..." />

            <button type="submit" 
                style="background: blue; color: white; padding: 12px; border: none; border-radius: 0 20px 20px 0; cursor: pointer;">
                Search
            </button>
        </g:form>
    </div>

        <table border="1">
            <tr>
                <th> Model </th>
                <th> Organization </th>
                <th> Code </th>
                <th> POS Product ID </th>
                <th> Is Active? </th>
                <th> Description </th>
                <th> Action </th>
            </tr>

            <g:each var="entry" in="${groupedProducts}">
                <tr  >
                    <td colspan="100%"  style="background:#EEF; font-weight: bold; font-style: italic;"> ${entry.key} </td>
                </tr>

                <g:each var="product" in="${entry.value}">
                    <tr> 
                        <td> ${product.model} </td>
                        <td> ${product.organization} </td>
                        <td> ${product.code} </td>
                        <td> ${product.pos_product_id} </td>
                        <td> ${product.active} </td>
                        <td> ${product.description} </td>
                        <td>
                            <g:link action="edit" id="${product.id}" style="text-decoration:none"> Edit </g:link> | 
                            <g:link action="delete" id="${product.id}" style="text-decoration:none"> Delete </g:link> | 
                            <g:link controller="product" action="view_details" id="${product.id}" style="text-decoration:none" > View </g:link>
                        </td>
                    </tr>
                </g:each>
            </g:each>

            <tr>

            </tr>
        </table>

    </body>
</html>