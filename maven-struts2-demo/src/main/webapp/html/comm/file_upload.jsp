<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>

	<form name="frmFileUpload"
		action="<%=request.getContextPath()%>/comm/fileUpload.action"
		method="post" enctype="multipart/form-data">


		<label for="inputStream">请选择上传的文件</label> 
		<input name="file" type="file" />
		<br />
		<button type="submit">提交</button>
	</form>

</body>
</html>