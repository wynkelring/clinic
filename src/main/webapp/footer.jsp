<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<footer class="footer py-3 fixed-bottom" style="background: #f7f7f7">
    <div class="container">
        <span class="text-muted">
            <div class="row">
                <div class="col"><spring:message code="label.footer"/> </div>
                <div class="col ml-auto text-right">${serverTime}</div>
            </div>
        </span>
    </div>
</footer>
