<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>Hotel Booking</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/index.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="home.">
        <fmt:message key="welcome" var="welcome"/>
        <fmt:message key="about" var="about"/>
    </fmt:bundle>

</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
    <div class="about">
        <h2>${welcome}</h2>
        <hr>
        <p>${about}</p>
    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>