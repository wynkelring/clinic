<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center">
        <form:form class="form-signin" name='searchVisitHours' method="POST" action="/visitHours" modelAttribute="searchVisitHours">
                <div class="form-label-group">
                    <form:select path="doctorId" id="doctorId" name="doctorId" class="form-control" placeholder="" required=""
                                 autofocus="" >
                        <c:forEach items="${doctorsList}" var="doctor">
                            <form:option value="${doctor.id}" >${doctor.firstName} ${doctor.lastName}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="doctorId"/>
                </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="visitHours.search"/></button>
        </form:form>
        <c:if test="${not empty list}">
            <div class="alert alert-danger container col-5" role="alert">
                <c:forEach items="${list}" var="list">
                    <a href="/visitHours/${list.id}">${list.startDate} - ${list.endDate}: ${list.description}</a><br>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty list && not empty searchVisitHours.doctorId}">
            <div class="alert alert-danger container col-5" role="alert">
                <spring:message code="visitHours.noVisitHours"/>
            </div>
        </c:if>
        <c:if test="${not empty notFound}">
            <div class="alert alert-danger container col-5" role="alert">
                <spring:message code="${notFound}"/>
            </div>
        </c:if>
    </section>
</main>
