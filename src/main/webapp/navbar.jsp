<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark" style="background: #3b5998">
    <div class="container">
        <a class="navbar-brand" href="/"><spring:message code="websiteName"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
                aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>"><spring:message code="menu.home"/> <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/visitHours"/>"><spring:message code="menu.visitsHours"/></a>
                </li>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/login"/>"><spring:message code="menu.login"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/register"/>"><spring:message code="menu.register"/></a>
                    </li>
                </sec:authorize>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-uppercase" href="#" id="profileDropdown" data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">${pageContext.request.userPrincipal.name}</a>
                        <div class="dropdown-menu" aria-labelledby="profileDropdown">
                            <a class="dropdown-item" href="<c:url value="/users/editProfile"/>">My profile</a>
                            <a class="dropdown-item" href="<c:url value="/visits/myVisits/1"/>">My visits</a>
                            <a class="dropdown-item" href="javascript:formSubmit()"><spring:message code="menu.logout"/></a>
                        </div>
                    </li>
                </c:if>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="languageDropdown" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false"><spring:message code="menu.language"/></a>
                    <div class="dropdown-menu" aria-labelledby="languageDropdown">
                        <a class="dropdown-item" href="?lang=pl">PL</a>
                        <a class="dropdown-item" href="?lang=en">EN</a>
                        <a class="dropdown-item" href="?lang=de">DE</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
<form action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
