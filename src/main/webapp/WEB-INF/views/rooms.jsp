<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/book.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:bundle basename="content" prefix="rooms.">
        <fmt:message key="title" var="title"/>
        <fmt:message key="class_label" var="class_label"/>
        <fmt:message key="basic_rate" var="basic_rate"/>
        <fmt:message key="rate_per_person" var="rate_per_person"/>
        <fmt:message key="cancel" var="cancel"/>
        <fmt:message key="save" var="save"/>
        <fmt:message key="number" var="number"/>
        <fmt:message key="beds" var="beds"/>
        <fmt:message key="active" var="active"/>
    </fmt:bundle>
    <title>${title}</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container">
    <form action="${pageContext.request.contextPath}/rooms/prices" method="post">
        <display:table name="requestScope.room_classes" uid="room_class" class="table">
        <display:column title="${class_label}" property="name"/>
        <display:column title="${basic_rate}">
            <input type="hidden" name="name" value="${room_class.name}">
            <input type="text" required value="${room_class.basicRate}" name="basicRate" pattern="^\d+\.\d{2}$" min="5" max="1000">
        </display:column>
        <display:column title="${rate_per_person}">
            <input type="text" required value="${room_class.ratePerPerson}" name="ratePerPerson" pattern="^\d+\.\d{2}$" min="5" max="1000">
        </display:column>
        </display:table>
        <input type="submit" value="${save}" id="save">
    </form>
    <form action="${pageContext.request.contextPath}/update_page" method="get">
        <input type="submit" value="${cancel}" id="cancel">
    </form>
</div>
<div class="container">

    <display:table name="requestScope.rooms" uid="room" pagesize="5" class="table" requestURI=""
                   sort="list" defaultsort="1" defaultorder="ascending">
        <display:column title="${number}" property="id" sortable="true"/>
        <display:column title="${class_label}" property="roomClass.name" sortable="true"/>
        <display:column title="${beds}" property="bedsAmount" sortable="true"/>
        <display:column title="${active}">
            <form action="${pageContext.request.contextPath}/rooms/${room.id}/status" method="post">
                <input type="checkbox" name="checkbox" value="checked"
                       <c:if test="${room.active}">checked="checked"</c:if>
                       onchange="this.form.submit()"
                />
            </form>
        </display:column>
    </display:table>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
