<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <form:form class="form-signin" name='editProfile' method="POST" action="/users/editProfile"
               modelAttribute="editProfile">
        <c:if test="${not empty success}">
            <div class="alert alert-primary container col-8 text-center" role="alert">
                <spring:message code="${success}"/>
            </div>
        </c:if>
        <div class="text-center mb-4">
            <h1 class="h3 mb-3 font-weight-normal"><spring:message code="editProfile.title"/></h1>
        </div>

        <div class="form-label-group">
            <form:input path="firstName" type="text" id="firstName" name="firstName" class="form-control"
                        placeholder="Firstname" required=""
                        autofocus=""/>
            <form:label path="firstName" for="firstName"><spring:message code="editProfile.firstName"/></form:label>
            <form:errors path="firstName"/>
        </div>
        <div class="form-label-group">
            <form:input path="lastName" type="text" id="lastName" name="lastName" class="form-control"
                        placeholder="Lastname" required=""
                        autofocus=""/>
            <form:label path="lastName" for="lastName"><spring:message code="editProfile.lastName"/></form:label>
            <form:errors path="lastName"/>
        </div>
        <div class="form-label-group">
            <form:input path="pesel" type="number" min="10000000000" max="99999999999" size="11" maxlength="11"
                        id="pesel" name="pesel" class="form-control" placeholder="Pesel" required=""
                        autofocus=""/>
            <form:label path="pesel" for="pesel"><spring:message code="editProfile.pesel"/></form:label>
            <form:errors path="pesel"/>
        </div>
        <div class="form-label-group">
            <form:input path="telephone" type="text" id="telephone" name="telephone" class="form-control"
                        placeholder="Telephone" required=""
                        autofocus=""/>
            <form:label path="telephone" for="telephone"><spring:message code="editProfile.telephone"/></form:label>
            <form:errors path="telephone"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message
                code="editProfile.title"/></button>
    </form:form>
</div>

