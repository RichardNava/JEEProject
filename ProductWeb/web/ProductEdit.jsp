<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="/pm/css/pm.css">        
        <title>Edit Product</title>       
    </head>
    <body>
        <header class="header">Edit Product</header>
        <nav class="nav"><a href="/pm/ProductSearch.html">Product Search</a></nav>
        <section class="content">
            <c:if test="${pm.status !=null}">
                <div class="${ (pm.errors)?'error':'info'}">${pm.status}</div>
            </c:if>
            <form action="ProductEdit.jsp" method="POST">
                <div class="field">
                    <label for="pid">ID:</label>
                    <input type="text" id="pid" name="p_id" value="${pm.product.id}" readonly>
                </div>
                <div class="field">
                    <label for="pid">Name:</label>
                    <input type="text" id="pname" name="p_name" value="${pm.product.name}" required>
                </div>
                <div class="field">
                    <label for="pid">Price:</label>
                    <input type="text" id="pprice" name="p_price" value="${pm.product.price}" required>
                </div>
                <div class="field">
                    <label for="pid">Best Before:</label>
                    <input type="text" id="pdate" name="p_date" value="${pm.product.bestBefore}">
                </div>
                <div class="field">
                    <input type="submit" id="submit" value="Update">
                </div> 
            </form>    
        </section>
        <footer class="footer">Invoker used method ${pageContext.request.method}</footer>
    </body>
</html>
