<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-light" style="background: #8b9dc3">
    <div class="container">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
                aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav mx-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/users/list/1"/>"><spring:message code="menu.users"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/visitHours"/>"><spring:message code="menu.visitsHours"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>"><spring:message code="menu.visits"/></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
