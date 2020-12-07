<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/login.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="register.">
        <fmt:message key="password" var="password"/>
        <fmt:message key="first_name" var="first_name"/>
        <fmt:message key="last_name" var="last_name"/>
        <fmt:message key="register" var="register"/>
        <fmt:message key="fail_message" var="fail_message"/>
    </fmt:bundle>
    <title>${register}</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<c:set var="user" scope="session" value="${sessionScope.user}"/>
<c:if test="${not empty user}">
    <jsp:forward page="/home"/>
</c:if>

<div class="login">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="register">
        <label >
            Email
            <input type="text" name="email" required>
        </label>
        <label>
            ${password}
            <input type="password" name="password" required>
        </label>
        <label >
            ${first_name}
            <input type="text" name="first_name" required>
        </label>
        <label >
            ${last_name}
            <input type="text" name="last_name" required>
        </label>
        <input class="button" type="submit" value="${register}">
    </form>
    <c:set var="is_register_failed" scope="request" value="${requestScope.is_login_failed}"/>
    <c:if test="${is_register_failed}">
        <div id="fail_message">
                ${fail_message}
        </div>
        <c:set var="is_register_failed" scope="session" value=""/>
    </c:if>

</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
