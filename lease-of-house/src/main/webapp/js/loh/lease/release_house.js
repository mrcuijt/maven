/**
 * 
 */

$("#province").bind("change", onProvinceChange);
$("#province").trigger("change");
function onProvinceChange(e) {

	$("#city").html("");
	$("#county").html("");

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

$("#city").bind("change", onCityChange);
function onCityChange(e) {

	$("#county").html("");

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