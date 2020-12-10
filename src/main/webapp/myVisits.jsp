<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"><spring:message code="visit.doctor"/></th>
                <th scope="col"><spring:message code="visit.visitDate"/></th>
                <th scope="col"><spring:message code="visit.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${visitList}" var="visit">
                <tr>
                    <td>${visit.visitHours.doctor.firstName} ${visit.visitHours.doctor.lastName}</td>
                    <td>${visit.visitHours.startDate} - ${visit.numberInQueue}</td>
                    <td>
                        <c:if test="${visit.cancelled}">
                            <spring:message code="visit.cancelled"/>
                        </c:if>
                        <c:if test="${visit.approved}">
                            <spring:message code="visit.approved"/>
                        </c:if>
                        <c:if test="${!visit.cancelled && !visit.approved}">
                            <c:if test="${ldtNow < visit.getVisitHours().getStartDate()}">
                                <form:form class="form-signin" name='cancel' method="POST" action="/visits/cancel/${visit.id}">
                                    <button class="btn btn-danger btn-block m-0" type="submit"><spring:message code="visit.cancel"/></button>
                                </form:form>
                            </c:if>
                            <c:if test="${ldtNow >= visit.getVisitHours().getStartDate()}">
                                <spring:message code="visit.pending"/>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${currentPage <= 1}">disabled</c:if>">
                    <c:if test="${currentPage > 1}">
                        <a class="page-link" href="/visits/list/${id - 1}"><spring:message code="pagination.previous"/></a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="page-link" href="/visits/list/1"><spring:message code="pagination.previous"/></a>
                    </c:if>
                </li>
                <li class="page-item"><a class="page-link" href="#">${currentPage}</a></li>
                <li class="page-item <c:if test="${totalPages <= currentPage}">disabled</c:if>">
                    <c:if test="${totalPages > currentPage}">
                        <a class="page-link" href="/visits/list/${id + 1}"><spring:message code="pagination.next"/></a>
                    </c:if>
                    <c:if test="${totalPages <= currentPage}">
                        <a class="page-link" href="/visits/list/${currentPage}"><spring:message code="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
    </section>
</main>
