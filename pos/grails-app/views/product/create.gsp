<html>
    <head> 
        <meta name="layout" content="main"/>
    </head>

    <body>
        <g:render template="/layouts/nav" />
        <div style="background: #f5f7f8;
            padding: 10px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 5px 5px 0 0;">  Add New Product
        </div>
        <g:link style="text-decoration:none; margin: 8px;" action="index"> Product list </g:link>

        <div style="width: 50%; margin: 20px auto;">
            

            <g:form controller="product" action="save" method="POST" style="display: flex; flex-direction: column; gap: 10px;">
                <label for="group">Group:</label>
                <g:select name="product_group" from="['3D TV','AIR COOLER', 'AIR FRYER']" />

                <label for="title">Model:</label>
                <input type="text" name="model" required />

                <label for="description">Organization:</label>
                <input type="text" name="organization" required />

                <label for="priority">Code:</label>
                <input type="number" name="code" required />

                <label for="category">POS Product ID:</label>
                <input type="number" name="pos_product_id" required />
                
                <label for="category">Is Active?:</label>
                <g:select name="active" from="['Yes','No']" />

                <label for="description">Description:</label>
                <textarea name="description" required ></textarea>

                <button type="submit" style="background: blue; color: white; padding: 8px; border: none; cursor: pointer;">
                    Save
                </button>
            </g:form>
        </div>

    </body>
</html>

