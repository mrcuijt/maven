<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	<h3>Index</h3>
	<a href="<%=request.getContextPath()%>/html/demo.jsp">dispatcherPostRequset</a>
	<h3>Dispatcher -- </h3>
	<a href="<%=request.getContextPath()%>/dispatcherPostRequset.do">dispatcherPostRequset</a>
	<br />
	<a href="<%=request.getContextPath()%>/dispatcherRequsetGet.do">ispatcherRequsetGet</a>
	<br />
	<a href="<%=request.getContextPath()%>/getRequest.do">getRequest</a>
	<br />
	<a href="<%=request.getContextPath()%>/postRequest.do">postRequest</a>
	<br />
	<h3>Redirect -- </h3>
	<a href="<%=request.getContextPath()%>/redirect/redirectPost.do">redirectPost</a>
	<br />
	<a href="<%=request.getContextPath()%>/redirect/redirectGet.do">redirectGet</a>
	<br />
	<a href="<%=request.getContextPath()%>/redirect/getRequest.do">getRequest</a>
	<br />
	<a href="<%=request.getContextPath()%>/redirect/postRequest.do">postRequest</a>
	<br />
</body>
</html>