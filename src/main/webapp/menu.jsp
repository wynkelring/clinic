<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<p><spring:message code="label.menu"/></p>
<sec:authorize access="isAnonymous()">
    <a href="<c:url value="/login"/>"><spring:message code="label.login"/></a>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="<c:url value="/users/addUser"/>"><spring:message code="label.title"/></a><br>
    <a href="<c:url value="/roles"/>"><spring:message code="label.role"/></a>
</sec:authorize>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<!-- csrf for log out-->
<form action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<br/>
<div>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <p>
            <spring:message code="label.welcome"/> : ${pageContext.request.userPrincipal.name} |
            <a href="javascript:formSubmit()"> Logout</a>
        </p>
    </c:if>
</div>
