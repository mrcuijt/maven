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

	$.post(basePath + "loh/getRegion.do", "regionId=" + provinceId, function(
			result) {
		if (result) {
			var data = JSON.parse(result);
			data.forEach(function(value, index) {

				var option = document.createElement("option");
				$(option).val(value.regionInfoId);
				$(option).html(value.regionName);
				$("#city").append(option);

			});
			$("#city").trigger("change");
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

	$.post(basePath + "loh/getRegion.do", "regionId=" + cityId,
			function(result) {
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