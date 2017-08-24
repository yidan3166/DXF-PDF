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

</head>
<body>

<h1>This is test.jsp</h1>
<h1>Filter should has redirected your to index.jsp</h1>
<h2>Filter 'RedirectToLogin' is not working properly</h2>

<h3>User session is:</h3>
<h1 style="color: red;"> <%= session.getAttribute("user") %> </h1>

</body>
</html>