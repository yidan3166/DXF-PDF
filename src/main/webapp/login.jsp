<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>

<!--ovo je za bolji rendering i touch-zooming-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--ovo je za bosanska slova-->
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- bootstrap CDN link for CSS (brzi nego bootstrap file)-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!--moj CSS-->
<link rel="stylesheet" href="">
<!-- modernizr cheks if browser supports HTML5 -->
<script src="modernizr-custom.js"></script>


</head>
<body>

<%! String message; %>

<%
message = (String) session.getAttribute("message");

if(message == null){
	message = "";
}
%>

<a href="index.jsp">back to index.jsp</a>

<h1>Welcome to login</h1>

<h3 style="color:red;"> <%= message %> </h3>
<% session.setAttribute("message", null); %>



<form action="Login" method="post">
	<label>username or email</label>
	<input type="text" name="emailOrUsername" required>
	<label>password</label>
	<input type="password" name="password" required>
	<input type="submit" value="login">
</form>

</body>
</html>