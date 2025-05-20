<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8" />
<title>Login to your account</title>
<!-- Set contextPath variable for reuse -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/log.css" />
</head>
<body>
	<div class="login-box">
		<h2>Login</h2>

		<!-- Display error message if available -->

		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="row">
				<div class="col">
					<label for="username">Username:</label>
					<input type="text" id="username" name="username"/>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="password">Password:</label>
					<input type="password" id="password" name="password"/>
				</div>
			</div>
			<button type="submit" class="login-button">Login</button>
		</form>
		<c:if test="${not empty error}">
			<p class="error-message" style="margin-top: 10px;">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
	</div>
</body>
</html>
