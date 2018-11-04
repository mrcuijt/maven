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
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	var basePath = <%=basePath%>
</script>
</head>
<body>

	<!-- 引入公共的页头 -->
	<jsp:include page="../comm/header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<form method="post" action="<%=request.getContextPath()%>/loh/admin/data/addData.do">
				
					<lable for="houseType">房屋类型</lable>
					<input name="houseType" type="text" value="" placeholder="请输入房屋类型"/>
					
					<button class="btn btn-default" type="submit">添加</button>
				</form>
			</div>
			<div class="col-sm-12">
				<label>房屋类型</label>
				<select>
					<c:forEach items="${lohHouseTypeList }" var="lohHouseType" varStatus="vs">
						<option value="${lohHouseType.lohHouseTypeId }">${lohHouseType.houseType }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-12">
				<c:forEach items="${lohHouseTypeList }" var="lohHouseType" varStatus="vs">
					${lohHouseType.houseType }<input name="lohHouseTypeId" type="checkbox" value="${lohHouseType.lohHouseTypeId }"/><button type="button" class="btn btn-default">删除</button><br/>
				</c:forEach>
			</div>
		</div> 
	</div>
	
	<!-- 引入公共的页脚 -->
	<jsp:include page="../comm/footer.jsp"></jsp:include>

	<!-- 引入 jQuery -->
	<script src="<%=request.getContextPath()%>/js/require/jquery/jquery-1.12.4.min.js"></script>
	<!-- 引入 Bootstrap -->
	<script src="<%=request.getContextPath()%>/js/require/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	
	    // post 请求获取 参数有 url , data , success{} ,
	    $.post("<%=basePath%>verifyEmail.sv", "email=" + email,function(resultString){
	          
	    });
	
	</script>
</body>
</html>