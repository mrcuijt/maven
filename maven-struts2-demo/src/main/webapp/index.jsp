<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调试主页</title>
</head>
<body>

	<h3>Hello World</h3>
	<br />

	<a href="<%=request.getContextPath()%>/hello.action">Hello World</a>
	<br />

	<h3>文件上传</h3>
	<a href="<%=request.getContextPath()%>/html/comm/file_upload.jsp">文件上传</a>
	<br />

</body>
</html>