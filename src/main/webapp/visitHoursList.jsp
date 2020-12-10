<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center">
        <a href="/visitHours/add" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">
            <spring:message code="visitHours.add"/>
        </a>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"><spring:message code="visitHours.doctor"/></th>
                <th scope="col"><spring:message code="visitHours.startDate"/></th>
                <th scope="col"><spring:message code="visitHours.endDate"/></th>
                <th scope="col"><spring:message code="visitHours.visitsCount"/></th>
                <th scope="col"><spring:message code="visitHours.visitLength"/></th>
                <th scope="col"><spring:message code="visitHours.visitCost"/></th>
                <th scope="col"><spring:message code="visitHours.manage"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${visitHoursList}" var="visitHours">
                <tr>
                    <td>${visitHours.doctor.firstName} ${visitHours.doctor.lastName}</td>
                    <td>${visitHours.startDate}</td>
                    <td>${visitHours.endDate}</td>
                    <td>${visitHours.visitsCount}</td>
                    <td>${visitHours.visitLength}</td>
                    <td>${visitHours.visitCost}</td>
                    <td>
                        <form:form class="form-signin" name='deleteUser' method="POST" action="/visitHours/cancel/${visitHours.id}">
                            <button class="btn btn-danger btn-block m-0" type="submit"><spring:message code="visitHours.cancel"/></button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${currentPage <= 1}">disabled</c:if>">
                    <c:if test="${currentPage > 1}">
                        <a class="page-link" href="/visitHours/list/${id - 1}"><spring:message code="pagination.previous"/></a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="page-link" href="/visitHours/list/1"><spring:message code="pagination.previous"/></a>
                    </c:if>
                </li>
                <li class="page-item"><a class="page-link" href="#">${currentPage}</a></li>
                <li class="page-item <c:if test="${totalPages <= currentPage}">disabled</c:if>">
                    <c:if test="${totalPages > currentPage}">
                        <a class="page-link" href="/visitHours/list/${id + 1}"><spring:message code="pagination.next"/></a>
                    </c:if>
                    <c:if test="${totalPages <= currentPage}">
                        <a class="page-link" href="/visitHours/list/${currentPage}"><spring:message code="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
    </section>
</main>
