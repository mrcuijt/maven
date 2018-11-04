/**
 * 
 */
package science.mrcuijt.loh.servlet.admin.data;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.service.LohAdminService;
import science.mrcuijt.loh.service.impl.LohAdminServiceImpl;

/**
 * @author Administrator
 *
 */
public class DeleteLohHouseTypeServlet extends HttpServlet {

	private LohAdminService lohAdminService = new LohAdminServiceImpl();
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 消息
		String message = "";
		
		// 删除 LohHouseType 的业务逻辑

		// 获取参数

		String strLohHouseTypeId = request.getParameter("houseTypeId");

		// 查询房屋类型是否存在
		Integer IohHouseTypeId = Integer.parseInt(strLohHouseTypeId);

		if (IohHouseTypeId == null || IohHouseTypeId <= 0) {
			message = "参数错误";
		}

		// 房屋类型
		LohHouseType lohHouseType = lohAdminService.findLohHouseTypeByPrimaryKey(IohHouseTypeId);
		
		if(lohHouseType == null) {
			message = "房屋类型不存在";
			return;
		}
		
		// 查询房屋类型是否有对应的房屋信息依赖
		List<LohHouseInfo> lohHouseInfoList = lohAdminService.findLohHouseInfoByLohHouseTypeId(IohHouseTypeId);

	}

}
