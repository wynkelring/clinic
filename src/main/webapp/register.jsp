<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
	<form:form class="form-signin" name='registerForm' method="POST" action="/register" modelAttribute="register">
        <div class="text-center mb-4">
            <h1 class="h3 mb-3 font-weight-normal"><spring:message code="register.title"/></h1>
        </div>

        <div class="form-label-group">
            <form:input path="firstName" type="text" id="firstName" name="firstName" class="form-control" placeholder="Firstname" required=""
                        autofocus="" />
            <form:label path="firstName" for="firstName"><spring:message code="register.firstName"/></form:label>
            <form:errors path="firstName"/>
        </div>
        <div class="form-label-group">
            <form:input path="lastName" type="text" id="lastName" name="lastName" class="form-control" placeholder="Lastname" required=""
                        autofocus="" />
            <form:label path="lastName" for="lastName"><spring:message code="register.lastName"/></form:label>
            <form:errors path="lastName"/>
        </div>
        <div class="form-label-group">
            <form:input path="pesel" type="number" min="10000000000" max="99999999999" size="11" maxlength="11" id="pesel" name="pesel" class="form-control" placeholder="Pesel" required=""
                        autofocus="" />
            <form:label path="pesel" for="pesel"><spring:message code="register.pesel"/></form:label>
            <form:errors path="pesel"/>
        </div>
        <div class="form-label-group">
            <form:input path="telephone" type="text" id="telephone" name="telephone" class="form-control" placeholder="Telephone" required=""
                        autofocus="" />
            <form:label path="telephone" for="telephone"><spring:message code="register.telephone"/></form:label>
            <form:errors path="telephone"/>
        </div>
        <div class="form-label-group">
            <form:input path="email" type="text" id="email" name="email" class="form-control" placeholder="Email" required=""
                        autofocus="" />
            <form:label path="email" for="email"><spring:message code="register.email"/></form:label>
            <form:errors path="email"/>
        </div>
        <div class="form-label-group">
            <form:input path="password" type="password" id="password" name="password" class="form-control" placeholder="Password" required=""
                        autofocus="" />
            <form:label path="password" for="password"><spring:message code="register.password"/></form:label>
            <form:errors path="password"/>
        </div>
        <div class="form-label-group">
            <form:input path="activationToken" type="hidden" id="activationToken" name="activationToken" class="form-control" value="true" />
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="register.title"/></button>
	</form:form>
</div>

