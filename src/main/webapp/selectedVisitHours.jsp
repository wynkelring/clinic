<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center">
        <c:if test="${not empty notAvailable}">
            <div class="alert alert-primary container col-8 text-center" role="alert">
                <spring:message code="${notAvailable}"/>
            </div>
        </c:if>
        <spring:message code="visitHours.doctor"/>: ${visitHours.doctor.firstName} ${visitHours.doctor.lastName}<br>
        <spring:message code="visitHours.description"/>: ${visitHours.description}<br>
        <spring:message code="visitHours.visitCost"/>: ${visitHours.visitCost} zł
        <c:if test="${not empty hourList}">
            <div class="alert alert-danger container col-5" role="alert">
                <c:forEach items="${hourList}" var="hour" varStatus="loop">
                    ${hour[0]} - ${hour[1]}
                    <c:if test="${hour[3]}">
                        <sec:authorize access="hasRole('PATIENT')">
                            <a href="/visits/book/${visitHours.id}/queue/${hour[2]}"><spring:message code="visitHours.book"/></a>
                        </sec:authorize>
                    </c:if>
                    <br>
                </c:forEach>
            </div>
        </c:if>
        <sec:authorize access="isAnonymous()">
            <spring:message code="visitHours.signin"/>
        </sec:authorize>
    </section>
</main>
