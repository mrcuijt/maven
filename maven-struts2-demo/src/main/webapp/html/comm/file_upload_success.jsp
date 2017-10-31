<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传成功</title>
</head>
<body>

	<%
		String message = request.getParameter("message");
		if (message != null && !message.equals("")) {
			message = new String(message.getBytes("iso-8859-1"), "utf-8");
		}
	%>

	<h3>文件上传成功</h3>

	<p>
		信息：<%=message%></p>

	<button type="button">
		<a href="<%=request.getContextPath()%>/html/comm/file_upload.jsp">继续上传</a>
	</button>
	
	<button type="button">
		<a href="<%=request.getContextPath()%>/index.jsp">返回索引页</a>
	</button>
	


</body>
</html>