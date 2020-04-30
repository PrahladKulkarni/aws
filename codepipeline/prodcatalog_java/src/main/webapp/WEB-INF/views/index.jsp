<%@ page import="java.util.ArrayList, com.aws.vokunev.catalog.data.CatalogItem"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <style>
    body {
      background-color:coral;
    }
  </style>
  <title>Sample Application - prodcatalog001</title>
</head>

<body>
  <h1>Product Catalog</h1>
  <table cellspacing="10" borfder="0">
    <th>ID</th>
    <th>Year</th>
    <th>Title</th>
    <th>Category</th>
    <th>Price</th>
    <c:forEach items="${catalog}" var="item">
      <tr>
        <td>${item.id}</td>
        <td>${item.year}</td>
        <td>${item.title}</td>
        <td>${item.productCategory}</td>
        <td>$${item.price}</td>
      </tr>
    </c:forEach>
  </table>
</body>

</html>