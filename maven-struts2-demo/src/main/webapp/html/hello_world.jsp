<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${message}</title>
</head>
<body>
	<h3>${message}</h3>

	<button type="button">
		<a href="<%=request.getContextPath()%>/index.jsp">返回索引页</a>
	</button>
</body>
</html>