<%@page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/error.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="error.">
        <fmt:message key="title" var="title"/>
        <fmt:message key="message" var="message"/>
        <fmt:message key="home" var="home"/>
    </fmt:bundle>

    <title>${title}</title>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
    <div class="form">
        ${message}
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="show_home_page">
            <input type="submit" value="${home}">
        </form>
    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
