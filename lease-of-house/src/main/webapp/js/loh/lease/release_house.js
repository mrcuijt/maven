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

	$.post(basePath + "loh/getRegion.do", "regionId=" + provinceId, function(result) {
		if (result) {
			var data = JSON.parse(result);
			data.forEach(function(value, index) {

				var option = document.createElement("option");
				$(option).val(value.regionInfoId);
				$(option).html(value.regionName);
				$("#city").append(option);

			});
			$("#city").trigger("change")
		}
	});
}

$("#city").bind("change", onCityChange);
function onCityChange(e) {

	$("#county").html("");

	// 获取 province 地区 id
	var cityId = $(e.currentTarget).val();

	$.post(basePath + "loh/getRegion.do", "regionId=" + cityId,function(result) {
		if (result) {
			var data = JSON.parse(result);
			data.forEach(function(value, index) {
				var option = document.createElement("option");
				$(option).val(value.regionInfoId);
				$(option).html(value.regionName);
				$("#county").append(option);
			});
		}
	});
}