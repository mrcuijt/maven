/**
 * 
 */
$(function() {
	// 绑定添加图片按钮的单击事件
	$("#addFile").bind("click", addUploadFile);
	$(".img-del-btn").bind("click", deleteImage);
	$("#province").bind("change", onProvinceChange);
	$("#city").bind("change", onCityChange);
});

function addUploadFile(e) {

	var $input = $("<input/>");
	$input.attr("name", "image");
	$input.attr("type", "file");
	$input.attr("placeholder", "请选择图片");

	$(e.target).parent().append($input);
}

function deleteImage(e) {

	$(e.target).parent().remove();
}

function onProvinceChange(e) {

	$("#city").html("");
	$("#county").html("");

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
			$("#city").trigger("change")
		}
	});
}

function onCityChange(e) {

	$("#county").html("");

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
