/**
 * 
 */
$(function() {
	// 绑定添加图片按钮的单击事件
	$("#province").bind("change", onProvinceChange);
	$("#city").bind("change", onCityChange);
	$("#province").trigger("change");
	
	initQueryPagination();
	// 房屋信息列表查询
	queryLohHousePagination();
});

function onProvinceChange(e) {

	$("#city").html("");
	$("#county").html("");

	var option = document.createElement("option");
	$(option).val(-1);
	$(option).html("请选择");
	$("#city").append(option);
	
	// 获取 province 地区 id
	var provinceId = $(e.currentTarget).val();

	$.post(basePath + "main/getRegion.do", "regionId=" + provinceId, function(data) {
		
		data = JSON.parse(data);
		
		if (data && data.status == 200 && data.result) {
			var regionList = data.result;
			regionList.forEach(function(value, index) {
				var option = document.createElement("option");
				$(option).val(value.regionInfoId);
				$(option).html(value.regionName);
				$("#city").append(option);
			});
			$("#city").trigger("change");
		}else if(data && data.status == 500){
			alert("请求失败！" + data.message);
		}else{
			alert("未知错误，请重试！")
		}
	});
}

function onCityChange(e) {

	$("#county").html("");

	var option = document.createElement("option");
	$(option).val(-1);
	$(option).html("请选择");
	$("#county").append(option);
	
	// 获取 province 地区 id
	var cityId = $(e.currentTarget).val();

	$.post(basePath + "main/getRegion.do", "regionId=" + cityId,function(data) {
		
		data = JSON.parse(data);
		
		if (data && data.status == 200 && data.result) {
			var regionList = data.result;
			regionList.forEach(function(value, index) {
				var option = document.createElement("option");
				$(option).val(value.regionInfoId);
				$(option).html(value.regionName);
				$("#county").append(option);
			});
		}else if(data && data.status == 500){
			alert("请求失败！" + data.message);
		}else{
			alert("未知错误，请重试！")
		}
	});
}

function initQueryPagination(){
	$(".loh-house-search-btn").bind("click",queryLohHousePagination);
	$(".loh-house-info").find("tr").last().find("a").bind("click",nextPage)
}

function nextPage(e){
	var option = $(e.target).attr("page");
	var pageIndex = $(".loh-house-info").find("tr").last().find("input[name=pageIndex]").val();
	var lastPage = $(".loh-house-info").find("tr").last().find("span.totalPage").html();
	
	if(pageIndex && !isNaN(pageIndex)){
		pageIndex = parseInt(pageIndex);
	}else{
		pageIndex = 1;
	}
	
	if(lastPage && !isNaN(lastPage)){
		lastPage = parseInt(lastPage);
	}else{
		lastPage = 1;
	}

	switch (option) {
	case "first":
		pageIndex = 1;
		break;
	case "perv":
		if (pageIndex > 1) {
			pageIndex -= 1;
		}
		break;
	case "next":
		if ((pageIndex + 1) <= lastPage) {
			pageIndex += 1;
		}
		break;
	case "last":
		pageIndex = lastPage;
		break;
	}
	
	// 更新页面序号
	$(".loh-house-info").find("tr").last().find("input[name=pageIndex]").val(pageIndex);
	
	// 房屋信息列表查询
	queryLohHousePagination();
}

// 房屋信息列表查询
function queryLohHousePagination(){
	
	const tds = ["houseTitle","lohHouseTypeId","price","houseAddress","pushDate","contacts","cellPhone","img"];
	
	var queryParam = $("<form>").append($(".container>.row>.col-sm-8").clone()).serialize();

	$.post("",queryParam,function(data){

		var tableHead = $(".loh-house-info").find("tr").first().clone();
		
		var tableBar = $(".loh-house-info").find("tr").last().clone(true);
		
		var table = $("<table>");
		
		table.addClass($(".loh-house-info").attr("class"));
		table.attr("border",$(".loh-house-info").attr("border"));

		table.append(tableHead);

		data = filterXSS(data);

		data = JSON.parse(data);

		if (data && data.status == 200 && data.result) {
			var lohHouseInfoList = data.result.page;
			var no = 1;
			lohHouseInfoList.forEach(function(value, index) {
				
				// tr
				var tr = $("<tr>");
				
				// number
				tr.append($("<td>").html(no));
				
				no++;
				
				// item
				tds.forEach(function(value, index) {
					let td = $("<td>").addClass(value);
					tr.append(td);
				});
				
				for (var tag in value) {
					for(let i = 0; i < tds.length; i++){
						let td = tr.find("td." + tds[i]);
						if (tag === tds[i] && td.length > 0) {
							td.html(value[tag]);
							break;
						}
					}
				}
				
				// 查看
				let see = $("<td>").append($("<a>").html("查看").attr("href","javascript:void(0);").attr("lohHouseInfoId",value["lohHouseInfoId"]))
				
				see.find("a").bind("click",seeHouseInfo);
				
				tr.append(see);
				
				// 收藏
				tr.append($("<td>").html("收藏"));
				
				table.append(tr);
			});
			
			tableBar.find("span.totalRecord").html(data.result.totalRecord);
			tableBar.find("span.pageIndex").html(data.result.pageIndex);
			tableBar.find("span.totalPage").html(data.result.totalPage);
			
			tableBar.find("input[name=pageIndex]").val(data.result.pageIndex);
			
		} else if (data && data.status == 500) {
			alert("请求失败！" + data.message);
		} else {
			alert("未知错误，请重试！")
		}
		
		table.append(tableBar);
		
		$(".container>.row>.col-sm-8>.col-sm-12").html("");
		
		$(".container>.row>.col-sm-8>.col-sm-12").append(table);
		
	});
	

	function seeHouseInfo(e) {
		window.location.href = basePath + "loh/showReleaseHouse.do?id="
				+ $(e.target).attr("lohHouseInfoId");
	}
}


