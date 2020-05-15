<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<c:url value="/login" var="loginProcessingUrl" />
	<form action="" method="post">
		<fieldset>
			<legend>Please Login</legend>
			<!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
			<div th:if="${param.error != null}">

					Failed to login.
					<div th:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                  Reason: <span th:text="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</div>
				</div>
			<!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
			<div th:if test="${param.logout != null}">
				You have been logged out.
			<div>
			<p>
				<label for="username">Username</label> <input type="text"
					id="username" name="username" />
			</p>
			<p>
				<label for="password">Password</label> <input type="password"
					id="password" name="password" />
			</p>
		
			<div>
				<button type="submit" class="btn">Log in</button>
			</div>
		</fieldset>
	</form>

</body>
</html>
