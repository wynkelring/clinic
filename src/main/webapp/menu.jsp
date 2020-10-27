<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p><spring:message code="label.menu"/></p>
<a href="<c:url value="/users/addUser"/>"><spring:message code="label.title"/></a>

