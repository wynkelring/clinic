<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<body onload='document.loginForm.username.focus();'>
	<div class="container">
		<c:if test="${not empty error}">
			<div class="alert alert-danger container col-5" role="alert">
				${error}
			</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="alert alert-primary container col-5" role="alert">
					${msg}
			</div>
		</c:if>
		<form class="form-signin" name='loginForm' action="<c:url value='/login'/>" method='POST'>
			<div class="text-center mb-4">
				<h1 class="h3 mb-3 font-weight-normal">Zaloguj się</h1>
			</div>

			<div class="form-label-group">
				<input type="text" id="login" name="login" class="form-control" placeholder="Login" required="" autofocus="">
				<label for="login">Email address</label>
			</div>

			<div class="form-label-group">
				<input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
				<label for="password">Password</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>

</body>
</html>
