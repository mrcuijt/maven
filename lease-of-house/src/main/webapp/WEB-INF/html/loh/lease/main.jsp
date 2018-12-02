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
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<title>代理房屋租赁平台 - 注册</title>
<!--[if lt IE 9]> 
<script src="<%=request.getContextPath()%>/js/require/html5shiv/3.7.0/html5shiv.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/respond/1.4.2/respond.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/require/css3-mediaqueries/1.0.0/css3-mediaqueries.js"></script> 
<![endif]-->
<script src="<%=request.getContextPath()%>/js/require/picturefill/3.0.2/picturefill.min.js" async></script>
<!-- 引入 Bootstrap 样式 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/css/bootstrap.min.css" />
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
</head>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="../comm/header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<form name="loh-search-form" action="<%=request.getContextPath()%>/loh/lease/main.do" method="get">
				<div class="col-sm-12">
					<div class="form-group">
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
						<button type="submit" class="form-control btn btn-default">搜索</button>
					</div>
				</div>
				<div class="col-sm-12">
					<table class="loh-house-info" border="2">
						<tr>
							<th width="100px;">序号</th>
							<th width="100px;">房屋类型</th>
							<th width="100px;">房屋价格</th>
							<th width="100px;">房屋地址</th>
							<th width="100px;">发布日期</th>
							<th width="100px;">联系人</th>
							<th width="100px;">联系方式</th>
							<th width="150px;">预览图</th>
							<th width="150px;">查看</th>
							<th width="150px;">收藏</th>
						</tr>
						<c:forEach items="${pagination }" var="houseInfo" varStatus="vs">
							<tr>
								<td>${vs.count }</td>
								<td>${houseInfo.lohHouseTypeId }</td>
								<td>${houseInfo.price }</td>
								<td>${houseInfo.houseAddress }</td>
								<td>${houseInfo.pushDate }</td>
								<td>${houseInfo.contacts }</td>
								<td>${houseInfo.cellPhone }</td>
								<td>${houseInfo.cellPhone }</td>
								<td>
									<a href="<%=request.getContextPath() %>/loh/lease/showReleaseHouse.do?id=${houseInfo.lohHouseInfoId}">查看</a>
									<a href="<%=request.getContextPath() %>/loh/lease/toUpdateReleaseHouse.do?id=${houseInfo.lohHouseInfoId}">更新</a>
									<a href="<%=request.getContextPath() %>/loh/lease/toDeleteReleaseHouse.do?id=${houseInfo.lohHouseInfoId}">删除</a>
								</td>
								<td>收藏</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="5">总共有 ${totalRecord } 条记录，当前是第 ${pageIndex } 页，共有 ${totalPage } 页</td>
							<td colspan="2">
								每页
								<select name="pageSize">
									<c:forEach items="3" var="count" varStatus="vs">
										<c:choose>
											<c:when test="${pageSize == 10 }">
												<option selected="selected" value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
											</c:when>
											<c:when test="${pageSize == 20 }">
												<option value="10">10</option>
												<option selected="selected" value="20">20</option>
												<option value="30">30</option>
											</c:when>
											<c:when test="${pageSize == 30 }">
												<option value="10">10</option>
												<option value="20">20</option>
												<option selected="selected" value="30">30</option>
											</c:when>
										</c:choose>
									</c:forEach>
								</select>条记录
							</td>
							<td>
								去第<input name="pageIndex" type="text" value="${pageIndex }" style="width: 50px"/>页
								<button type="submit">GO</button>
							</td>
							<td>
								<a href="<%=request.getContextPath()%>/loh/lease/main.do?pageIndex=1&pageSize=${pageSize}${fn:escapeXml(queryParam)}">首页</a>
								<c:if test="${pageIndex > 1 }">
									<a href="<%=request.getContextPath()%>/loh/lease/main.do?pageIndex=${pageIndex - 1}&pageSize=${pageSize}${fn:escapeXml(queryParam)}">上一页</a></td>
								</c:if>
							<td>
								<c:if test="${pageIndex < totalPage}">
									<a href="<%=request.getContextPath()%>/loh/lease/main.do?pageIndex=${pageIndex + 1}&pageSize=${pageSize}${fn:escapeXml(queryParam)}">下一页</a>
								</c:if>
								<a href="<%=request.getContextPath()%>/loh/lease/main.do?pageIndex=${totalPage}&pageSize=${pageSize}${fn:escapeXml(queryParam)}">最后一页</a>
							</td>
						</tr>
					</table>
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
	<script src="<%=request.getContextPath()%>/js/loh/lease/main.js"></script>
</body>
</html>