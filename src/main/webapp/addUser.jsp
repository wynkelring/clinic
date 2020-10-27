<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>App user page</title>
</head>
<body>
<h1><spring:message code="label.appUser"/></h1>
<form:form method="post" action="/users/addUser" modelAttribute="addUser">

    <table>
        <tr>
            <td><form:label path="firstName"><spring:message code="label.firstName"/></form:label></td>
            <td><form:input path="firstName" /></td>
            <td><form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td><form:label path="lastName"><spring:message code="label.lastName"/></form:label></td>
            <td><form:input path="lastName" /></td>
            <td><form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
            <td><form:input path="email" /></td>
            <td><form:errors path="email"/></td>
        </tr>
        <tr>
            <td><form:label path="telephone"><spring:message code="label.telephone"/></form:label></td>
            <td><form:input path="telephone" /></td>
            <td><form:errors path="telephone"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.addUser"/>"/>
            </td>
        </tr>
    </table>

</form:form>
</body>
</html>

