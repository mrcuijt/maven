<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>post.jsp</title>
</head>
<body>
	<span>Form GET</span>
	<form action="<%=request.getContextPath()%>/getRequest.do" method="get">
		<input name="userName" type="text" placeholder="用户名" /> 
		<br /> 
		<input name="password" type="password" placeholder="密码" />
		<button type="submit">提交</button>
	</form>
	
	<br />
	
	<span>Form POST</span>
	<form action="<%=request.getContextPath()%>/getRequest.do" method="post">
		<input name="userName" type="text" placeholder="用户名" /> 
		<br /> 
		<input name="password" type="password" placeholder="密码" />
		<button type="submit">提交</button>
	</form>
</body>
</html>