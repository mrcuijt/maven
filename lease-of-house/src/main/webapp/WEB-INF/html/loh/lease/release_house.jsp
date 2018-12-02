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
<title>代理房屋租赁平台 - 房屋发布</title>
<!--[if lt IE 9]> 
<script src="<%=request.getContextPath()%>/js/require/html5shiv/3.7.0/html5shiv.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/respond/1.4.2/respond.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/css3-mediaqueries/1.0.0/css3-mediaqueries.js"></script> 
<![endif]-->
<script src="<%=request.getContextPath()%>/js/require/picturefill/3.0.2/picturefill.min.js" async></script>
<!-- 引入 Bootstrap 样式 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/css/bootstrap.min.css" />
<script type="text/javascript">
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	var basePath = "<%=basePath%>";
</script>
</head>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="../comm/header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<form name="loginForm" class="login-form" enctype="multipart/form-data"
				method="post" action="<%=request.getContextPath()%>/loh/lease/releaseHouse.do">
				<div class="col-sm-7">
					<table>
						<tr>
							<td>
								<label for="houseTitle">房屋标题：</label> 
								<input name="houseTitle" type="text" placeholder="请输入房屋标题"  value=""/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="houseType">房屋类型：</label> 
								<select name="houseType">
									<c:forEach items="${lohHouseTypeList }" var="lohHouseType" varStatus="">
										<option value="${lohHouseType.lohHouseTypeId}">${lohHouseType.houseType}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label for="housePrice">房屋价格：</label> 
								<input name="housePrice" type="text" placeholder="请输入出租金额"  value=""/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="houseAddress">所在地址：</label> 
								<select id="province" name="province">
									<c:forEach items="${regionInfoList }" var="regionInfo" varStatus="vs">
										<option value="${regionInfo.regionInfoId }">${regionInfo.regionName }</option>
									</c:forEach>
								</select>
								<select id="city" name="city"></select>
								<select id="county" name="county"></select>
								<input name="houseAddress" type="text" placeholder="请输入所在地址"  value=""/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="contacts">联系人：</label> 
								<input name="contacts" type="text" placeholder="请输入联系人"  value=""/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="cellPhone">联系方式：</label> 
								<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-5">
					<table>
						<tr>
							<td>
								<label for="image">房屋预览图：</label> 
								<input name="image" type="file" placeholder="请选择图片" />
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-12">
					<a role="button" class="btn btn-default" href="<%=request.getContextPath()%>/loh/lease/main.do">返回</a>
					<a role="button" class="btn btn-default" href="#">保存</a>
					<button class="btn btn-default" type="submit">发布</button>
					<p>${message }</p>
					<p>
						<%
							String message = request.getParameter("message");
							if (message != null) {
								message = new String(message.getBytes("iso-8859-1"), "UTF-8");
						%>
						<%=message%>
						<%
							}
						%>
					</p>
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
	<!-- 引入当前页面的 js -->
	<script src="<%=request.getContextPath()%>/js/loh/lease/release_house.js"></script>
</body>
</html>