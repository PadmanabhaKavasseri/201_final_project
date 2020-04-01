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
<title>Dashboard</title>
</head>
<body>
<%String user = (String) session.getAttribute("username"); %>
<p><a href="index.html" action=<%session.removeAttribute("username"); %>>Logout</a>
<h1>Hello <%=user%>! Welcome to the SChedget Dashboard!</h1>
<a href="schedule_input.jsp" action=<%session.setAttribute("username", user); %>>Find your schedule</a>
<a href="rooms_list.jsp" action=<%session.setAttribute("username", user); %>>Make a reservation</a>
</body>
</html>