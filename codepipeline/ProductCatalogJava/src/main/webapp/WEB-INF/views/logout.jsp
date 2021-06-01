<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty token}">
    <div style="border-style: solid; border-width: medium; border-color: red; padding: 10px;">
        <a href="logout/"><strong>Log Out</strong></a>
        </br>
        <a href="/logout/"><strong>Log Out</strong></a>
        </br>
        <a href="/prodcatalog/logout/"><strong>Log Out</strong></a>
    </div>
</c:if>

