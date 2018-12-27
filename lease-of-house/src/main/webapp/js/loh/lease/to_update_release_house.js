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
