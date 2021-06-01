<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty token}">
    <div style="text-align: right; font-size: 120%; padding: 10px;">
        <a href="logout">Sign Out</a>
    </div>
</c:if>

