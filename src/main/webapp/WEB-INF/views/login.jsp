<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/login.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="login.">
        <fmt:message key="password" var="password"/>
        <fmt:message key="log_in" var="log_in"/>
        <fmt:message key="fail_message" var="fail_message"/>
    </fmt:bundle>
    <title>${log_in}</title>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp"/>

    <c:set var="user" scope="session" value="${sessionScope.user}"/>
    <c:if test="${not empty user}">
        <jsp:forward page="/home"/>
    </c:if>

    <div class="login">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="login">
            <label >
                Email
                <input type="email"
                       name="email"
                       required
                       minlength="5"
                       maxlength="30"
                >
            </label>
            <label>
                ${password}
                <input type="password"
                       name="password"
                       required
                       minlength="5"
                       maxlength="30"
                >
            </label>
                <input class="button" type="submit" value="${log_in}">
        </form>
        <c:set var="is_login_failed" scope="request" value="${requestScope.is_login_failed}"/>
        <c:if test="${is_login_failed}">
            <div id="fail_message">
                ${fail_message}
            </div>
            <c:set var="is_login_failed" scope="session" value=""/>
        </c:if>

    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
