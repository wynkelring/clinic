<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center col-6">
        <h1><spring:message code="home.book.title"/></h1>
        <p>
            <spring:message code="home.book.p"/>
        </p>
        <p class="lead text-muted">
            <a href="/visitHours" class="btn btn-primary my-2"><spring:message code="home.book.b"/></a>
        </p>
    </section>
</main>
