<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<title>代理房屋租赁平台 - 注册</title>
<!--[if lt IE 9]> 
<script src="<%=request.getContextPath()%>/js/require/html5shiv/3.7.0/html5shiv.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/respond/1.4.2/respond.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/css3-mediaqueries/1.0.0/css3-mediaqueries.js"></script> 
<![endif]-->
<script
	src="<%=request.getContextPath()%>/js/require/picturefill/3.0.2/picturefill.min.js"
	async></script>
<!-- 引入 Bootstrap 样式 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="../loh/comm/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div class="login-div">
				<form name="loginForm" class="login-form" method="post"
					action="<%=request.getContextPath()%>/register.do">
					<table>
						<tr>
							<td>
								<label for="username">用户名：</label> 
								<input name="username" type="text" placeholder="请输入用户名" value="${userName}" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="password">密码：</label> 
								<input name="password" type="password" placeholder="请输入密码" value="" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="repassword">再次密码：</label> 
								<input name="repassword" type="password" placeholder="请再次输入密码" value="" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="verifyCode">验证码：</label> 
								<img src="<%=request.getContextPath()%>/verifyCode.do?t=<%=System.currentTimeMillis()%>" />
								<input name="verifyCode" type="text" placeholder="请输入验证码" value="" />
							</td>
						</tr>
						<tr>
							<td>
								<button type="reset">重置</button>
								<button type="submit">注册</button>
								<span>${requestScope.message }</span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 引入公共的页脚 -->
	<jsp:include page="../loh/comm/footer.jsp"></jsp:include>

	<!-- 引入 jQuery -->
	<script
		src="<%=request.getContextPath()%>/js/require/jquery/jquery-1.12.4.min.js"></script>
	<!-- 引入 Bootstrap -->
	<script
		src="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>