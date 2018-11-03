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
<title>代理房屋租赁平台 - 主页</title>
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
			<div class="col-sm-12">
				<form name="loh-search-form"  action="#" method="get">
					<div class="form-group">
						<label for="loh_region">地区：</label>
						<input name="loh_region" type="text" class="form-control" placeholder="地区"/>
						<label for="loh_type">房屋类型：</label>
						<input name="loh_type" type="text" class="form-control" placeholder="房屋类型"/>
						<label for="loh_price">房屋价格</label>
						<input name="loh_price" type="text" class="form-control" placeholder="房屋价格"/>
						<button type="submit" class="form-control btn btn-default">搜索</button>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<table class="loh-house-info" border="2">
					<tr>
						<th width="100px;">房屋类型</th>
						<th width="100px;">房屋价格</th>
						<th width="100px;">发布日期</th>
						<th width="100px;">联系人</th>
						<th width="100px;">联系方式</th>
						<th width="150px;">预览图</th>
						<th width="150px;">查看</th>
						<th width="150px;">收藏</th>
					</tr>
					<%
						request.setAttribute("items", new String[] { "1", "2" });
					%>
					<c:forEach items="${items }" var="option" varStatus="vs">
						<tr>
							<td>${option }</td>
							<td>${option }</td>
							<td>${option }</td>
							<td>${option }</td>
							<td>${option }</td>
							<td>${option }</td>
							<td>查看</td>
							<td>收藏</td>
						</tr>
					</c:forEach>
				</table>
			</div>
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