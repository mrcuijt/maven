<html>
<head>
  <title>Welcome!</title>
</head>
<body>
	<H1>Welcome to the Mini Server</H1>
	
	<span>POST 文本表单</span>
	<h3>Method POST enctype="application/x-www-form-urlencoded"</h3>
	<form action="//${host}:${port}" method="POST">
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<span>POST 文本表单</span>
	<h3>Method POST enctype="multipart/form-data"</h3>
	<form action="//${host}:${port}" method="POST" enctype="multipart/form-data">
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<span>POST 文件表单</span>
	<h3>Method POST enctype="application/x-www-form-urlencoded"</h3>
	<form action="//${host}:${port}" method="POST">
		<input name="image" type="file" placeholder="请选择图片" />
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<span>POST 文件表单</span>
	<h3>Method POST enctype="multipart/form-data"</h3>
	<form action="//${host}:${port}" method="POST" enctype="multipart/form-data">
		<input name="image" type="file" placeholder="请选择图片" />
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<span>混合表单 POST 文件表单</span>
	<h3>Method POST enctype="multipart/form-data"</h3>
	<form action="//${host}:${port}" method="POST" enctype="multipart/form-data">
		<input name="image" type="file" placeholder="请选择图片" />
		<input name="image" type="file" placeholder="请选择图片" />
		<input name="image" type="file" placeholder="请选择图片" />
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<h3>Method POST enctype="multipart/form-data"</h3>
	<form action="//${host}:${port}" method="POST" enctype="multipart/form-data">
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<h3>Method POST enctype="application/x-www-form-urlencoded"</h3>
	<form action="//${host}:${port}" method="POST">
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<h3>Method GET enctype="application/x-www-form-urlencoded"</h3>
	<form action="//${host}:${port}" method="GET">
		<input name="image" type="file" placeholder="请选择图片" /><br/>
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<h3>Method GET enctype="multipart/form-data"</h3>
	<form action="//${host}:${port}" method="GET" enctype="multipart/form-data">
		<input name="image" type="file" placeholder="请选择图片" /><br/>
		<input name="cellPhone" type="text" placeholder="请输入联系方式"  value=""/>
		<button class="btn btn-default" type="submit">提交</button>
	</form>
	
	<script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
	<button id="ajax" class="btn btn-default" type="button">ajax</button>
	<script>
		$("#ajax").bind("click",function(){
			$.post("",{age:"22",name:"张三"},function(data){console.info(data);})
		})
	</script>
</body>
</html>
