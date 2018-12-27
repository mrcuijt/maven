/**
 * 
 */
$(function() {
	initRegionInfo();
});

function initRegionInfo(){
	// 绑定添加图片按钮的单击事件
	$("#province").bind("change", onProvinceChange);
	$("#city").bind("change", onCityChange);

	if ($("#city").find("option").length == 1) {
		$("#province").trigger("change");
	}

	if ($("#county").find("option").length == 1) {
		$("#city").trigger("change");
	}

}

function onProvinceChange(e) {

	$("#city").html("");
	$("#county").html("");

	var option = document.createElement("option");
	$(option).val(-1);
	$(option).html("请选择");
	$("#city").append(option);
	
	// 获取 province 地区 id
	var provinceId = $(e.currentTarget).val();

	$.post(basePath + "loh/getRegion.do", "regionId=" + provinceId, function(data) {

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

	$.post(basePath + "loh/getRegion.do", "regionId=" + cityId, function(data) {

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