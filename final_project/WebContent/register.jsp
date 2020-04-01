<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<p><a href="index.html">Back to Home Page</a></p>
	<h1>Register</h1>
	<form name="register-form" method="get" action="RegisterServlet">
		<div>
			<label for="username">Username:</label><br />
			<input type="text" name="username" /><br />
			<label for="email">Email:</label><br />
			<input type="text" name="email" /><br />
			<label for="password">Password:</label><br />
			<input type="text" name="password" /><br />
			<label for="confirm-password">Confirm Password:</label><br />
			<input type="text" name="confirm-password" /><br />
			<input type="submit" name="submit" value="submit" />
		</div>
	</form>
	<%if(request.getAttribute("error")!=null){
		String error_message = (String) request.getAttribute("error");%>
		<p style="color:red"><%=error_message%></p>
	<%} %>
</body>
</html>