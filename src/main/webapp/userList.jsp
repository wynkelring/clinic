<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
                <th scope="col">#</th>
                <th scope="col"><spring:message code="userList.firstName"/></th>
                <th scope="col"><spring:message code="userList.lastName"/></th>
                <th scope="col"><spring:message code="userList.email"/></th>
                <th scope="col"><spring:message code="userList.active"/></th>
                <th scope="col"><spring:message code="userList.roles"/></th>
                <th scope="col"><spring:message code="userList.manage"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <th scope="row">${user.id}</th>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <c:if test="${user.enabled && !user.deleted}">
                        <td><spring:message code="userList.active.true"/></td>
                    </c:if>
                    <c:if test="${user.enabled == false}">
                        <td><spring:message code="userList.active.not"/></td>
                    </c:if>
                    <td>
                        <c:forEach items="${user.roles}" var="role">
                            <spring:message code="userList.roles.${role.role}"/>
                        </c:forEach>
                    </td>
                    <td>
                        <a class="page-link" href="/users/get/${user.id}">
                            <spring:message code="userList.manage"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${currentPage <= 1}">disabled</c:if>">
                    <c:if test="${currentPage > 1}">
                        <a class="page-link" href="/users/list/${id - 1}"><spring:message
                                code="pagination.previous"/></a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="page-link" href="/users/list/1"><spring:message code="pagination.previous"/></a>
                    </c:if>
                </li>
                <li class="page-item"><a class="page-link" href="#">${currentPage}</a></li>
                <li class="page-item <c:if test="${totalPages <= currentPage}">disabled</c:if>">
                    <c:if test="${totalPages > currentPage}">
                        <a class="page-link" href="/users/list/${id + 1}"><spring:message code="pagination.next"/></a>
                    </c:if>
                    <c:if test="${totalPages <= currentPage}">
                        <a class="page-link" href="/users/list/1"><spring:message code="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
    </section>
</main>
