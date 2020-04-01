<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	session=request.getSession();
    if(session.getAttribute("username")==null)
    {
        response.sendRedirect("index.html");
    }
%>
<head>
<meta charset="UTF-8">
<title>Study Rooms</title>
</head>
<%session=request.getSession(); 
String user = (String) session.getAttribute("username");%>
<body>
<a href="index.html" action=<%session.removeAttribute("username"); %>>Logout</a>
<a href="dashboard.jsp" action=<%session.setAttribute("username", user); %>>Return to your Dashboard</a>
<h1>This is where users will see a list of study rooms!</h1>
<h2>User: <%= session.getAttribute("username") %></h2>
</body>
</html>