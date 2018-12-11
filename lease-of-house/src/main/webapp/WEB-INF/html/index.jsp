<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<title>代理房屋租赁平台 - 首页</title>
<!--[if lt IE 9]> 
<script src="<%=request.getContextPath()%>/js/require/html5shiv/3.7.0/html5shiv.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/respond/1.4.2/respond.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/css3-mediaqueries/1.0.0/css3-mediaqueries.js"></script> 
<![endif]-->
<script src="<%=request.getContextPath()%>/js/require/picturefill/3.0.2/picturefill.min.js"
	async></script>
<!-- 引入 Bootstrap 样式 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/loh/index/index.css" />
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="./loh/comm/header.jsp"></jsp:include>

	<!-- Banner begin -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1" class=""></li>
			<li data-target="#myCarousel" data-slide-to="2" class=""></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img class="first-slide"
					src="<%=request.getContextPath()%>/images/loh/index/001.jpg"
					alt="First slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Example headline.</h1>
						<p>
							Note: If you're viewing this page via a
							<code>file://</code>
							URL, the "next" and "previous" Glyphicon buttons on the left and
							right might not load/display properly due to web browser security
							rules.
						</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Sign
								up today</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item">
				<img class="second-slide"
					src="<%=request.getContextPath()%>/images/loh/index/002.jpg"
					alt="Second slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Another example headline.</h1>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Learn
								more</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item">
				<img class="third-slide"
					src="<%=request.getContextPath()%>/images/loh/index/003.jpg"
					alt="Third slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>One more for good measure.</h1>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Browse
								gallery</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<!-- Banner end -->

	<div class="container">
		<div class="row">
			<div class="col-sm-8">
				<div class="row">
					<div class="col-sm-12 form-group">
						<label for="lohRegion">地区：</label>
						<select id="province" name="province">
							<c:forEach items="${provinceList }" var="regionInfo" varStatus="vs">
								<c:choose>
									<c:when test="${regionInfo.regionInfoId == province }">
										<option value="${regionInfo.regionInfoId }" selected="selected">${regionInfo.regionName }</option>
									</c:when>
									<c:otherwise>
										<option value="${regionInfo.regionInfoId }">${regionInfo.regionName }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<select id="city" name="city">
							<option value="-1" selected="selected">请选择</option>
							<c:forEach items="${cityList }" var="regionInfo" varStatus="vs">
								<c:choose>
									<c:when test="${regionInfo.regionInfoId == city }">
										<option value="${regionInfo.regionInfoId }" selected="selected">${regionInfo.regionName }</option>
									</c:when>
									<c:otherwise>
										<option value="${regionInfo.regionInfoId }">${regionInfo.regionName }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<select id="county" name="county">
							<option value="-1" selected="selected">请选择</option>
							<c:forEach items="${countyList }" var="regionInfo" varStatus="vs">
								<c:choose>
									<c:when test="${regionInfo.regionInfoId == county }">
										<option value="${regionInfo.regionInfoId }" selected="selected">${regionInfo.regionName }</option>
									</c:when>
									<c:otherwise>
										<option value="${regionInfo.regionInfoId }">${regionInfo.regionName }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<br />
						<label for="lohHouseType">房屋类型：</label>
						<select name="houseType">
							<option value="-1" selected="selected">请选择</option>
							<c:forEach items="${lohHouseTypeList }" var="lohHouseType" varStatus="">
								<c:choose>
									<c:when test="${lohHouseType.lohHouseTypeId == lohHouseTypeId}">
										<option selected="selected" value="${lohHouseType.lohHouseTypeId}">${lohHouseType.houseType}</option>
									</c:when>
									<c:otherwise>
										<option value="${lohHouseType.lohHouseTypeId}">${lohHouseType.houseType}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<br />
						<label for="lohPrice">房屋价格</label>
						<input name="lohPrice" type="text" value="${lohPrice }" class="form-control" placeholder="请输入房屋价格"/>
						<label for="houseAddress">房屋地址</label>
						<input name="houseAddress" type="text" value="${fn:escapeXml(houseAddress) }" class="form-control" placeholder="请输入房屋地址"/>
						<button class="loh-house-search-btn" type="button" class="form-control btn btn-default">搜索</button>
					</div>
				</div>
				<div class="col-sm-12">
					<table class="loh-house-info" border="2">
						<tr>
							<th>序号</th>
							<th>房屋类型</th>
							<th>房屋价格</th>
							<th>房屋地址</th>
							<th>发布日期</th>
							<th>联系人</th>
							<th>联系方式</th>
							<th>预览图</th>
							<th>查看</th>
							<th>收藏</th>
						</tr>
						<c:forEach items="${pagination }" var="houseInfo" varStatus="vs">
							<tr>
								<td>${vs.count }</td>
								<td>${houseInfo.lohHouseTypeId }</td>
								<td>${houseInfo.price }</td>
								<td>${houseInfo.houseAddress }</td>
								<td>
									<fmt:formatDate value="${houseInfo.pushDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${houseInfo.contacts }</td>
								<td>${houseInfo.cellPhone }</td>
								<td>${houseInfo.cellPhone }</td>
								<td>
									<a href="<%=request.getContextPath() %>/loh/showReleaseHouse.do?id=${houseInfo.lohHouseInfoId}">查看</a>
								</td>
								<td>收藏</td>
							</tr>
						</c:forEach>
						<c:if test="${pagination == null || fn:length(pagination) == 0}">
							<tr>
								<td colspan="10">未查询到数据。</td>
							</tr>
						</c:if>
						<tr>
							<td colspan="5">总共有 <span class="totalRecord"></span> 条记录，当前是第 <span class="pageIndex"></span> 页，共有 <span class="totalPage"></span> 页</td>
							<td colspan="2">
								每页
								<select name="pageSize">
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
								</select>条记录
							</td>
							<td>
								去第<input name="pageIndex" type="text" value="" style="width: 50px"/>页
								<button class="loh-house-search-btn" type="button">GO</button>
							</td>
							<td>
								<a href="javascript:void(0);" page="first">首页</a>
								<a href="javascript:void(0);" page="perv">上一页</a>
							</td>
							<td>
								<a href="javascript:void(0);" page="next">下一页</a>
								<a href="javascript:void(0);" page="last">最后一页</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="login-div">
					<form name="loginForm" class="login-form" method="post"
						action="<%=request.getContextPath()%>/login.do">
						<table>
							<tr>
								<td>
									<label for="username">用户名：</label> 
									<input name="userName" type="text" placeholder="请输入用户名"  value="${userName }"/>
								</td>
							</tr>
							<tr>
								<td>
									<label for="password">密码：</label> 
									<input name="password" type="password" placeholder="请输入密码" value=""/>
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
									<a role="button" class="btn btn-default" href="<%=request.getContextPath()%>/register.do">
										<!-- <button class="btn btn-default" type="button">注册</button> -->
										注册
									</a>
									<button class="btn btn-default" type="submit">登录</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="row"></div>
	</div>

	<!-- 引入公共的页脚 -->
	<jsp:include page="./loh/comm/footer.jsp"></jsp:include>
	<!-- 引入 jQuery -->
	<script src="<%=request.getContextPath()%>/js/require/jquery/jquery-1.12.4.min.js"></script>
	<!-- 引入修复 jQuery.conle 的修复脚本  -->
	<script src="<%=request.getContextPath()%>/js/require/jquery/fix/jquery.fix.clone.js"></script>
	<!-- 引入 Bootstrap -->
	<script src="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- 引入当前页面的 js -->
	<script src="<%=request.getContextPath()%>/js/loh/main.js?dfsddffsffddff"></script>
</body>
</html>