<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${token.manager}">
    <c:if test="${not empty metadata}">
        <div style="border-style: solid; border-width: medium; border-color: red; padding: 10px;">
            <strong>Instance ID:</strong> ${metadata.instance_id}
            <br>
            <strong>Instance AZ:</strong> ${metadata.availability_zone}
        </div>
    </c:if>
</c:if>