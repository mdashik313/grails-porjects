<html>
    <head> 
        <meta name="layout" content="main"/>
    </head>

    <body>
        <g:render template="/layouts/nav" />

        <div style="width: 50%; margin: 20px auto;">
            <h2 style="text-align: center;">Edit Product</h2>

            <g:form controller="product" action="update" id="${product.id}" method="PUT" style="display: flex; flex-direction: column; gap: 10px;">
                <label for="group">Group:</label>
                <g:select name="product_group" value="${product?.product_group}" from="['3D TV','AIR COOLER', 'AIR FRYER']" />

                <label for="title">Model:</label>
                <input type="text" name="model" value="${product.model}" required />

                <label for="description">Organization:</label>
                <input type="text" name="organization" value="${product.organization}" required />

                <label for="priority">Code:</label>
                <input type="number" name="code" value="${product.code}" required />

                <label for="category">POS Product ID:</label>
                <input type="number" name="pos_product_id" value="${product.pos_product_id}" required />
                
                <label for="category">Is Active?:</label>
                <g:select name="active" value="${product.active}" from="['Yes','No']" />

                <label for="description">Description:</label>
                <textarea name="description" required >${product.description}</textarea>

                <button type="submit" style="background: blue; color: white; padding: 8px; border: none; cursor: pointer;">
                    Update
                </button>
                <button type="button" onclick="window.location.href='${createLink(controller: 'product', action: 'index')}'" 
                    style="background: blue; color: white; padding: 8px; border: none; cursor: pointer;">
                    Cancel
                </button>
            </g:form>
        </div>

    </body>
</html>

