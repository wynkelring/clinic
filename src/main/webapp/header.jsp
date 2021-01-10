<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header class="jumbotron">
    <div class="col-sm-8 mx-auto">
        <h1><spring:message code="home.welcome"/></h1>
        <p>
            <spring:message code="home.p1"/>
        </p>
        <p>
            <spring:message code="home.p2"/>
        </p>
    </div>
</header>
