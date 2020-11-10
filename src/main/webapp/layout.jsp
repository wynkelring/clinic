<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title" ignore="true"/> - <spring:message code="websiteName"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="<c:url value="/resources/styles/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/floating-labels.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/bootstrap.min.css"/>">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="<c:url value="/resources/js/bootstrap.bundle.min.js"/>"></script>
</head>
<body>
<tiles:insertAttribute name="navbar"/>
<div class="container mb-5 mt-2">
    <div class="row">
        <tiles:insertAttribute name="header"/>
    </div>
    <div class="row">
        <div class="col-12">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
</div>
<tiles:insertAttribute name="footer"/>
</body>
</html>

