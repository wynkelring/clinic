<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<main role="main">
    <section class="container text-center">
        <c:if test="${not empty errorDate}">
            <div class="alert alert-primary container col-8 text-center" role="alert">
                <spring:message code="${errorDate}"/>
            </div>
        </c:if>
        <form:form class="form-signin" name='addVisitHours' method="POST" action="/visitHours/add"
                   modelAttribute="addVisitHours">
            <div class="form-label-group">
                <form:input path="startDate" type="datetime-local" id="startDate" name="startDate" class="form-control"
                            placeholder="" required=""
                            autofocus=""/>
                <form:label path="startDate" for="startDate"><spring:message code="visitHours.startDate"/></form:label>
                <form:errors path="startDate"/>
            </div>
            <div class="form-label-group">
                <form:select path="visitLength" id="visitLength" name="visitLength" class="form-control" placeholder=""
                             required=""
                             autofocus="">
                    <form:option value="15"></form:option>
                    <form:option value="30"></form:option>
                    <form:option value="45"></form:option>
                    <form:option value="60"></form:option>
                </form:select>
                <form:label path="visitLength" for="visitLength"><spring:message
                        code="visitHours.visitLength"/></form:label>
                <form:errors path="visitLength"/>
            </div>
            <div class="form-label-group">
                <form:input path="visitsCount" type="number" min="1" id="visitsCount" name="visitsCount"
                            class="form-control" placeholder="" required=""
                            autofocus=""/>
                <form:label path="visitsCount" for="visitsCount"><spring:message
                        code="visitHours.visitsCount"/></form:label>
                <form:errors path="visitsCount"/>
            </div>
            <div class="form-label-group">
                <form:input path="visitCost" type="number" min="1" step="0.01" id="visitCost" name="visitCost"
                            class="form-control" placeholder="" required=""
                            autofocus=""/>
                <form:label path="visitCost" for="visitCost"><spring:message code="visitHours.visitCost"/></form:label>
                <form:errors path="visitCost"/>
            </div>
            <div class="form-label-group">
                <form:input path="description" type="text" id="description" name="description" class="form-control"
                            placeholder="" required=""
                            autofocus=""/>
                <form:label path="description" for="description"><spring:message
                        code="visitHours.description"/></form:label>
                <form:errors path="description"/>
            </div>
            <sec:authorize access="!hasAnyRole('DOCTOR')">
                <div class="form-label-group">
                    <form:select path="doctorId" id="doctorId" name="doctorId" class="form-control" placeholder=""
                                 required=""
                                 autofocus="">
                        <c:forEach items="${doctorsList}" var="doctor">
                            <form:option value="${doctor.id}">${doctor.firstName} ${doctor.lastName}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="doctorId"/>
                </div>
            </sec:authorize>
            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message
                    code="visitHours.add"/></button>
        </form:form>
    </section>
</main>
