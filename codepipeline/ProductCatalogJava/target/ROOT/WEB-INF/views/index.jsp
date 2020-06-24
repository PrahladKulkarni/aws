<%@ page import="java.util.ArrayList, com.aws.vokunev.catalog.data.CatalogItem"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <style>
    body {
      background-color:white;
    }
    table tbody tr:nth-child(even){
      background-color:azure;
      color:black;
    }    
    table td {
      padding: 7px;
      font-size: 120%;
    }
    th {
      text-align:left;
    }
    .priceUpdate {
      color: red;
    }
  </style>
  <title>Sample Application - Product Catalog</title>
</head>

<body>
  <h1>Product Catalog</h1>
  <table>
    <th>ID</th>
    <th>Year</th>
    <th>Title</th>
    <th>Category</th>
    <th>Price</th>
    <th>Recent Updates</th>
    <c:forEach items="${catalog}" var="item">
      <tr>
        <td><a href="product?id=${item.id}" target="_blank">${item.id}</a></td>
        <td>${item.year}</td>
        <td>${item.title}</td>
        <td>${item.productCategory}</td>
        <td>$${item.price}</td>
        <td class="priceUpdate">
          <c:if test="${item.oldPrice > 0}">
            <c:choose>
              <c:when test="${item.price > item.oldPrice}">Up from $${item.oldPrice}</c:when>
              <c:otherwise>Down from $${item.oldPrice}</c:otherwise>
           </c:choose>
          </c:if>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>

</html>