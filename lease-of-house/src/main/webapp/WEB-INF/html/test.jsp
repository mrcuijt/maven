<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>


<!--[if lt IE 8]>
<script type="text/javascript" src="//g.alicdn.com/??aliyun/www-dpl/0.6.10/knight/js/iconfont-ie7.js"></script>
<![endif]-->

<!--[if lt IE 9]>
<link type="text/css" rel="stylesheet" href="//g.alicdn.com/??aliyun/www-dpl/0.6.10/knight/css/widget/header-ie.css"/>
<script type="text/javascript" src="//g.alicdn.com/??aliyun/www-dpl/0.6.10/knight/js/html5.js"></script>
<![endif]-->

<!--[if lt IE 9]>
<link type="text/css" rel="stylesheet" href="//g.alicdn.com/??aliyun/www-dpl/0.6.10/knight/css/widget/header-ie.css"/>
<script type="text/javascript" src="//g.alicdn.com/??aliyun/www-dpl/0.6.10/knight/js/html5.js"></script>
<![endif]-->

<!--scripts start -->

<script src="//alinw.alicdn.com/platform/c/jquery/1.11.3/dist/jquery.min.js"></script>

<script src="//g.alicdn.com/??aliyun/ali-init/0.0.7/index-min.js,tbc/global/0.0.8/index-min.js"></script>

<!-- scripts end -->
</head>

<script src="https://cdn.bootcss.com/picturefill/3.0.3/picturefill.min.js"></script>
<script src=" http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script> 
<script src=" http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script> 
</head>
<body>

	<!-- enctype="multipart/form-data" -->
	<form method="post" action="<%=request.getContextPath()%>/login.do">

		<table>
			<tr>
				<td>姓名：</td>
				<td><input name="name" type="text" value="张三" /></td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td><input name="name" type="text" value=",张,三" /></td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td><input name="age" type="text" value="23" /></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><input name="sex" type="text" value="男" /></td>
			</tr>
			<tr>
				<td><button type="reset">还原</button></td>
				<td><button type="submit">提交</button></td>
			</tr>
		</table>

	</form>

</body>
</html>