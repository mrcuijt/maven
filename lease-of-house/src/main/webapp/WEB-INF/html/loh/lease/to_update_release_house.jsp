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
			<form name="loginForm" class="login-form" enctype="multipart/form-data"
				method="post" action="<%=request.getContextPath()%>/loh/lease/updateReleaseHouse.do">
				<input name="lohHouseInfoId" value="${lohHouseInfo.lohHouseInfoId }" style="display:none"/>
				<div class="col-sm-7">
					<table>
						<tr>
							<td>
								<label for="houseTitle">房屋标题：</label> 
								<input name="houseTitle" type="text" placeholder="请输入房屋标题"  value="房屋出租"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="houseType">房屋类型：</label> 
								<select name="houseType">
									<c:forEach items="${LohHouseTypeList }" var="lohHouseType" varStatus="">
										<c:choose>
											<c:when test="${lohHouseType.lohHouseTypeId == lohHouseInfo.lohHouseTypeId}">
												<option selected="selected" value="${lohHouseType.lohHouseTypeId}">${lohHouseType.houseType}</option>
											</c:when>
											<c:otherwise>
												<option value="${lohHouseType.lohHouseTypeId}">${lohHouseType.houseType}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label for="housePrice">房屋价格：</label> 
								<input name="housePrice" type="text" placeholder="请输入出租金额"  value="${lohHouseInfo.price }"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="houseAddress">所在地址：</label> 
								<select id="province" name="regionInfo">
									<c:forEach items="${regionInfoList }" var="regionInfo" varStatus="vs">
										<option value="${regionInfo.regionInfoId }">${regionInfo.regionName }</option>
									</c:forEach>
								</select>
								<select id="city" name="regionInfo"></select>
								<select id="county" name="regionInfo"></select>
								<input name="houseAddress" type="text" placeholder="请输入所在地址"  value="${lohHouseInfo.houseAddress }"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="contacts">联系人：</label> 
								<input name="contacts" type="text" placeholder="请输入联系人"  value="${lohHouseInfo.contacts }"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="cellPhone">联系方式：</label> 
								<input name="cellPhone" type="text" placeholder="请输入联系方式"  value="${lohHouseInfo.cellPhone }"/>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-5">
					<table>
						<tr>
							<td>
								<label for="image">房屋预览图：</label> <button id="addFile" type="button">添加</button>
								<input name="image" type="file" placeholder="请选择图片" />
							</td>
						</tr>
						<tr>
							<td>
								<c:forEach items="${lohFileInfos}" var="lohFileInfo"  varStatus="vs">
									<img src="<%=request.getContextPath()%>/${lohFileInfo.fileLink}" />
									<input name="imageId" value="${lohFileInfo.lohFileInfoId }" style="display:none"/>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-12">
					<a role="button" class="btn btn-default" href="#">保存</a>
					<button class="btn btn-default" type="submit">更新</button>
					<p>${message }</p>
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
	<script type="text/javascript">
		$(function(){
			// 绑定添加图片按钮的单击事件
			$("#addFile").bind("click",addUploadFile);
		});
		
		function addUploadFile(e){
			
			var $input = $("<input/>");
			$input.attr("name","image");
			$input.attr("type","file");
			$input.attr("placeholder","请选择图片");
			
			$(e.target).parent().append($input);
		}
	</script>
</body>
</html>