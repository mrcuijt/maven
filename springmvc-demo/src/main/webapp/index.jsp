<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引页-大屏展示数据支持</title>
</head>
<body>
	<h1>大屏展示数据支持V1.0</h1>
	<br />

	<h3>Hello World</h3>
	<a href="<%=request.getContextPath()%>/hello.action">Hello World</a>

	<h3>Image Main</h3>
	<a href="<%=request.getContextPath()%>/json/helloJsonSupport.action"> Hello JsonSupport </a>

	<br />
	<a href="javascript:(0)" onclick="requestController();">查询总的联网医院数</a>


	<script type="text/javascript" charset="UTF-8">
		function requestController() {

			var baseUrl = "http://localhost:8080<%=request.getContextPath()%>";
			var xmlhttp;

			if (window.XMLHttpRequest) {
				// IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
				xmlhttp = new XMLHttpRequest();
			} else {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}

			xmlhttp.onreadystatechange = function() {
				console.info(xmlhttp);
				console.info(xmlhttp.state);
				if (xmlhttp.readystate == 4 && xmlhttp.state == 200) {
					try {
						document.getElementById("myDiv").innerHtml = xmlhttp.responseText;
					} catch (e) {
						console.info(e.message);
						console.info(xmlhttp.responseText);
					}
				} else if (xmlhttp.readystate == 4 && xmlhttp.state == 404) {
					alert("请求的页面找不到了！");
				}
			};

			xmlhttp.open("POST", baseUrl + "/json/helloJsonSupport.action?t="
					+ Math.random(), true);
			xmlhttp.send();

		}
	</script>

</body>
</html>