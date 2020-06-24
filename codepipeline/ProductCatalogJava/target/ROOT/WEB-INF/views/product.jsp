<%@ page import="java.util.ArrayList, com.aws.vokunev.catalog.data.CatalogItem"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <style>
    body {
      background-color:white;
    }
    table table tbody tr:nth-child(odd){
      background-color:lightblue;
      color:black;
    }
    table td {
      padding: 10px;
      font-size: 120%;
    }
    tr {
      vertical-align: top;
    }
  </style>
  <title>Sample Application - Product Details</title>
</head>

<body>
  <h1>Product Details</h1>
  <table width="100%">
    <tr>
      <td width="40%">
        <table>
          <tr>
            <td><b>Id:</b></td><td>${product.id}</td>
          </tr>
          <tr>
            <td><b>Title:</b></td><td>${product.title}</td>
          </tr>
          <tr>
            <td ><b>Description:</b></td><td>${product.description}</td>
          </tr>
          <tr>
            <td><b>Category:</b></td><td>${product.productCategory}</td>
          </tr>
          <%-- Insert the additional properties here --%>
          <c:forEach items="${product.properties}" var="property">
            <tr>
              <td><b>${property.key}:</b></td><td>${property.value}</td>
            </tr>
          </c:forEach>
          <%-- These core properties always stay at the bottom --%>
          <tr>
            <td><b>Year:</b></td><td>${product.year}</td>
          </tr>
          <tr>
            <td><b>Price:</b></td><td>${product.price}</td>
          </tr>
        </table>
      </td>
      <td style="text-align: center; vertical-align: middle;">
        <img width="500" src="${product.image}" alt="product image"/>
      </td>
    </tr>
  </table>
</body>

</html>