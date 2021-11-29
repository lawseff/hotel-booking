<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/header.css"/>" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title></title>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="header.">
        <fmt:message key="home" var="home"/>
        <fmt:message key="log_in" var="log_in"/>
        <fmt:message key="register" var="register"/>
        <fmt:message key="rooms" var="rooms"/>
        <fmt:message key="book" var="book"/>
        <fmt:message key="reservations" var="reservations"/>
        <fmt:message key="sign_out" var="sign_out"/>
    </fmt:bundle>

</head>
<body>
<div class="header">
    <div class="header__inner">
        <div class="logo">
            <h1>The<span>HOTEL</span><span id="stars">&#8902;&#8902;&#8902;&#8902;&#8902;</span></h1>
        </div>
        <c:set var="user" scope="session" value="${sessionScope.user}"/>
        <ul class="navigation">
            <a href="${pageContext.request.contextPath}/home"><li>${home}</li></a>
            <c:if test="${empty user}">
                <a href="${pageContext.request.contextPath}/login">
                    <li>${log_in}&nbsp;<i class="fa fa-sign-out"></i></li>
                </a>
                <a href="${pageContext.request.contextPath}/register">
                    <li>${register}</li>
                </a>
            </c:if>
            <c:if test="${not empty user}">
                <c:if test="${user.admin}">
                    <a href="${pageContext.request.contextPath}/rooms"><li>${rooms}</li></a>
                </c:if>
                <c:if test="${not user.admin}">
                    <a href="${pageContext.request.contextPath}/book"><li>${book}</li></a>
                </c:if>
                <a href="${pageContext.request.contextPath}/reservations">
                    <li>${reservations}</li>
                </a>
                <a href="${pageContext.request.contextPath}/sign_out">
                    <li>${sign_out}&nbsp;<i class="fa fa-sign-out"></i></li>
                </a>
            </c:if>
            <select name="locale" form="change_language" onchange="this.form.submit()">
                <li>
                    <option value="en-US"
                            <c:if test="${sessionScope.locale.language eq 'en'}">
                                selected="selected"
                            </c:if>
                    >EN</option>
                    <option value="ru-RU"
                            <c:if test="${sessionScope.locale.language eq 'ru'}">
                                selected="selected"
                            </c:if>
                    >RU</option>
                    <option value="be-BY"
                            <c:if test="${sessionScope.locale.language eq 'be'}">
                                selected="selected"
                            </c:if>
                    >BE</option>
                </li>
            </select>
        </ul>
    </div>
</div>

<form id="change_language" action="${pageContext.request.contextPath}/change_language">
</form>

</body>
</html>
