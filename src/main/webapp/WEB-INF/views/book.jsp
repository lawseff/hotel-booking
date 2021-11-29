<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/book.css"/>" type="text/css"/>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:bundle basename="content" prefix="book.">
        <fmt:message key="check_in" var="check_in"/>
        <fmt:message key="check_out" var="check_out"/>
        <fmt:message key="class_label" var="class_label"/>
        <fmt:message key="persons" var="persons"/>
        <fmt:message key="book_room" var="book_room"/>
    </fmt:bundle>

    <title>${book_room}</title>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp"/>

    <div class="reservation_request">
        <form action="${pageContext.request.contextPath}/book" method="post">
            <label>
                ${check_in} <br>
                <booking:dateInput name="arrivalDate" minimumDate="today"/> <br>
            </label>
            <label>
                ${check_out} <br>
                <booking:dateInput name="departureDate" minimumDate="tomorrow"/> <br>
            </label>
            <label>
                ${class_label} <br>
                <select name="roomClass">
                    <c:forEach var="room_class" items="${requestScope.room_classes}">
                        <option value="${room_class.name}">${room_class.name}</option>
                    </c:forEach>
                </select> <br>
            </label>
            <label>
                ${persons} <br>
                <select name="personsAmount">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                </select> <br>
            </label>
            <input type="submit" value="${book_room}"/>
        </form>
    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
