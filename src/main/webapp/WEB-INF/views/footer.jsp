<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/footer.css"/>" type="text/css"/>
    <title></title>

</head>
<body>

    <div class="footer">
        <c:set var="user" scope="session" value="${sessionScope.user}"/>
        <c:if test="${not empty user and user.admin}">
            <span>Administrator</span>
        </c:if>
    </div>

</body>
</html>
