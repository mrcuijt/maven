<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<title>代理房屋租赁平台 - 信息修改</title>
<!--[if lt IE 9]> 
<script src="<%=request.getContextPath()%>/js/require/html5shiv/3.7.0/html5shiv.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/respond/1.4.2/respond.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/css3-mediaqueries/1.0.0/css3-mediaqueries.js"></script> 
<![endif]-->
<script src="<%=request.getContextPath()%>/js/require/picturefill/3.0.2/picturefill.min.js" async></script>
<!-- 引入 Bootstrap 样式 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="../comm/header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<form name="loginForm" class="login-form" method="post" action="#">
				<div class="col-sm-5">
						<table>
							<tr>
								<td>
									<label for="username">房屋类型：</label> 
									<input name="username" type="text" placeholder="请输入用户名"  value="zhangsan"/>
								</td>
							</tr>
							<tr>
								<td>
									<label for="username">所在地址：</label> 
									<input name="username" type="text" placeholder="请输入用户名"  value="zhangsan"/>
								</td>
							</tr>
							<tr>
								<td>
									<label for="username">联系人：</label> 
									<input name="username" type="text" placeholder="请输入用户名"  value="zhangsan"/>
								</td>
							</tr>
							<tr>
								<td>
									<label for="username">联系方式：</label> 
									<input name="username" type="text" placeholder="请输入用户名"  value="zhangsan"/>
								</td>
							</tr>
							<tr>
								<td>
									
								</td>
							</tr>
						</table>
				</div>
				<div class="col-sm-7">
					<table>
						<tr>
							<td>
								<label for="username">房屋预览图：</label> 
								<input name="username" type="file" placeholder="请选择图片" />
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-12">
					<a role="button" class="btn btn-default" href="#">保存</a>
					<button class="btn btn-default" type="submit">发布</button>
				</div>
			</form>
		</div>
	</div>
	
	<!-- 引入公共的页脚 -->
	<jsp:include page="../comm/footer.jsp"></jsp:include>

	<!-- 引入 jQuery -->
	<script src="<%=request.getContextPath()%>/js/require/jquery/jquery-1.12.4.min.js"></script>
	<!-- 引入 Bootstrap -->
	<script src="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>