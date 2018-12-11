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
<title>代理房屋租赁平台 - 房屋发布详细信息</title>
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
			<div class="col-sm-5">
				<table>
					<tr>
						<td>
							<span>房屋标题：${lohHouseInfo.houseTitle }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>房屋类型：${lohHouseType.houseType }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>房屋价格：${lohHouseInfo.price }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>房屋所在地区：${provience.regionName }</span>
							<span>${city.regionName }</span>
							<span>${country.regionName }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>房屋所在地址：${lohHouseInfo.houseAddress }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>联系人：${lohHouseInfo.contacts }</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>联系方式：${lohHouseInfo.cellPhone }</span>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-sm-7">
				<table>
					<tr>
						<td>
							<label for="username">房屋预览图：</label> 
							<c:forEach items="${lohFileInfoList}" var="lohFileInfo"  varStatus="vs">
								<img src="<%=request.getContextPath()%>/${lohFileInfo.fileLink}" />
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			<p>${message }</p>
			<a class="btn btn-default" href="<%=request.getContextPath()%>/loh/main.do">返回</a>
		</div>
	</div>
	
	<!-- 引入公共的页脚 -->
	<jsp:include page="../comm/footer.jsp"></jsp:include>

	<!-- 引入 jQuery -->
	<script src="<%=request.getContextPath()%>/js/require/jquery/jquery-1.12.4.min.js"></script>
	<!-- 引入 Bootstrap -->
	<script src="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			if (window.history && window.history.pushState) {
				$(window).on('popstate', function() {
					window.history.pushState('forward', null, '#');
					window.history.forward(1);
				});
			}
			window.history.pushState('forward', null, '#'); // 在IE中必须得有这两行
			window.history.forward(1);
		});
	</script>
</body>
</html>