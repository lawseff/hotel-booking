<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/reservations.css"/>" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="<c:url value="/assets/js/form.js"/>"></script>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:bundle basename="content" prefix="reservations.">
        <fmt:message key="title" var="title"/>
        <fmt:message key="list.id" var="list_id"/>
        <fmt:message key="list.check_in" var="list_check_in"/>
        <fmt:message key="list.check_out" var="list_check_out"/>
        <fmt:message key="list.status" var="list_status"/>

        <fmt:message key="details" var="details"/>
        <fmt:message key="details.id" var="details_id"/>
        <fmt:message key="details.first_name" var="details_first_name"/>
        <fmt:message key="details.second_name" var="details_second_name"/>
        <fmt:message key="details.room" var="details_room"/>
        <fmt:message key="details.room_number" var="details_room_number"/>
        <fmt:message key="details.no_rooms" var="details_no_rooms"/>
        <fmt:message key="details.class" var="details_class"/>
        <fmt:message key="details.persons" var="details_persons"/>
        <fmt:message key="details.check_in" var="details_check_in"/>
        <fmt:message key="details.check_out" var="details_check_out"/>
        <fmt:message key="details.status" var="details_status"/>
        <fmt:message key="details.total_price" var="details_total_price"/>

        <fmt:message key="button.cancel" var="button_cancel"/>
        <fmt:message key="button.approve" var="button_approve"/>
        <fmt:message key="button.pay" var="button_pay"/>
        <fmt:message key="button.check_in" var="button_check_in"/>
        <fmt:message key="button.check_out" var="button_check_out"/>

        <fmt:message key="payment.sum" var="payment_sum"/>
        <fmt:message key="payment.card_number" var="payment_card_number"/>
        <fmt:message key="payment.valid_thru" var="payment_valid_thru"/>
        <fmt:message key="payment.valid_thru_placeholder" var="valid_thru_placeholder"/>
        <fmt:message key="payment.cvv_number" var="payment_cvv_number"/>

        <fmt:message key="status.waiting" var="status_waiting"/>
        <fmt:message key="status.approved" var="status_approved"/>
        <fmt:message key="status.paid" var="status_paid"/>
        <fmt:message key="status.checked_in" var="status_checked_in"/>
        <fmt:message key="status.checked_out" var="status_checked_out"/>
        <fmt:message key="status.cancelled" var="status_cancelled"/>
    </fmt:bundle>

    <title>${title}</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container">
    <display:table name="sessionScope.reservations" uid="reservation" class="table" pagesize="8"
                   sort="list" defaultsort="1" defaultorder="descending">
        <display:column title="${list_id}" property="id" sortable="true"/>
        <display:column title="${list_check_in}" sortable="true" sortProperty="arrivalDate">
            <fmt:formatDate value="${reservation.arrivalDate}" type="date"/>
        </display:column>
        <display:column title="${list_check_out}" sortable="true" sortProperty="departureDate">
            <fmt:formatDate value="${reservation.departureDate}" type="date"/>
        </display:column>
        <display:column title="${list_status}" sortable="true" sortProperty="reservationStatus">
            <booking:formatStatus status="${reservation.reservationStatus}"/>
        </display:column>
        <display:column title="${details}">

            <a href="${pageContext.request.contextPath}/controller?command=show_reservations_page&id=${reservation.id}">
                ...
            </a>

        </display:column>
    </display:table>
</div>


    <c:set var="reservation_details" scope="session" value="${sessionScope.reservation_details}"/>
    <c:set var="user" scope="session" value="${sessionScope.user}"/>
    <c:set var="rooms" scope="session" value="${sessionScope.rooms}"/>

    <c:if test="${not empty reservation_details}">
<div class="container" id="details">
        ${details}
        <hr>
        <table>
            <tr>
                <td>${details_id}</td>
                <td>${reservation_details.id}</td>
            </tr>
            <tr>
                <td>${details_first_name}</td>
                <td>${reservation_details.user.firstName}</td>
            </tr>
            <tr>
                <td>${details_second_name}</td>
                <td>${reservation_details.user.secondName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${reservation_details.user.email}</td>
            </tr>
            <tr>
                <td>${details_room}</td>
                <c:if test="${not empty reservation_details.room}">
                    <td>${reservation_details.room.id}</td>
                </c:if>
                <c:if test="${user.admin and reservation_details.reservationStatus eq 'WAITING'}">
                    <td>
                        <c:if test="${not empty rooms}">
                        <select name="room_id" form="approve" required>
                            <c:forEach items="${rooms}" var="room">
                                <option value="${room.id}">
                                    ${details_room_number}${room.id}
                                </option>
                            </c:forEach>
                        </c:if>
                            <c:if test="${empty rooms}">
                                ${details_no_rooms}
                            </c:if>
                        </select>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>${details_class}</td>
                <td>${reservation_details.roomClass.name}</td>
            </tr>
            <tr>
                <td>${details_persons}</td>
                <td>${reservation_details.personsAmount}</td>
            </tr>
            <tr>
                <td>${details_check_in}</td>
                <td>
                    <fmt:formatDate value="${reservation_details.arrivalDate}" type="date"/>
                </td>
            </tr>
            <tr>
                <td>${details_check_out}</td>
                <td>
                    <fmt:formatDate value="${reservation_details.departureDate}" type="date"/>
                </td>
            </tr>
            <tr>
                <td>${details_status}</td>
                <td>
                    <booking:formatStatus status="${reservation_details.reservationStatus}"/>
                </td>

            </tr>
            <tr>
                <td>${details_total_price}</td>
                <c:if test="${not empty reservation_details.totalPrice}">
                    <td>${reservation_details.totalPrice} BYN</td>
                </c:if>
            </tr>
            <tr>

                <c:if test="${reservation_details.reservationStatus ne 'CANCELLED'
                and reservation_details.reservationStatus ne 'CHECKED_OUT'}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="cancel_reservation">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="${button_cancel}">
                        </form>
                    </td>
                </c:if>

                <c:if test="${reservation_details.reservationStatus eq 'WAITING' and user.admin}">
                    <td>
                        <c:if test="${not empty rooms}">
                            <form id="approve" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="approve">
                                <input type="hidden" name="price" value="${reservation_details.totalPrice}">
                                <input type="hidden" name="id" value="${reservation_details.id}">
                                <input type="submit" value="${button_approve}">
                            </form>
                        </c:if>
                        <c:if test="${empty rooms}">
                            <input type="button" value="${button_approve}" disabled>
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'APPROVED' and not user.admin}">
                    <td>
                        <input type="button" value="${button_pay}" onclick="toggle('show')">
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'PAID' and user.admin}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="check_in">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="${button_check_in}">
                        </form>
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'CHECKED_IN' and user.admin}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="check_out">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="${button_check_out}">
                        </form>
                    </td>
                </c:if>
            </tr>
        </table>
    </c:if>
</div>

<div class="form" id="payment">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="pay">
        <input type="hidden" name="id" value="${reservation_details.id}">
        <p>${payment_sum} ${reservation_details.totalPrice} BYN<p>
        <label>
            ${payment_card_number}
            <input type="text" name="card_number" id="card_number" pattern="\d{16}" maxlength="16" required>
        </label>
        <label>
            ${payment_valid_thru}
            <input type="text" name="valid_thru" id="valid_thru" placeholder="${valid_thru_placeholder}" pattern="(0[1-9]|1[0-2])/\d{2}" maxlength="5" required>
        </label>
        <label>
            ${payment_cvv_number}
            <input type="password" name="cvv_number" id="cvv_number" pattern="\d{3}" maxlength="3" required>
        </label>
        <input type="submit" value="${button_pay}">
        <input type="button" value="${button_cancel}" onclick="toggle('hide')">
        <i class="fa fa-cc-visa"></i>
        <i class="fa fa-cc-mastercard"></i>
    </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>