/**
 * 
 */
function deleteLohHouseViewHistory(dom) {

	var id = $(dom).attr("data");

	$.post(basePath + "/loh/deleteLohHouseViewHistory.do", "id=" + id, function(data) {
		
		if(data && data.status == 200){
			console.info(data.message);
		}else if(data && data.status == 500){
			console.info(data.message);
		}
		window.location.reload();
	});

}