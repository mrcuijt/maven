/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ShowReleaseHouseServlet extends HttpServlet {

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String message = null;

		// 获取消息
		message = request.getParameter("message");

		if (message != null && message.trim().length() > 0) {
			message = new String(message.getBytes("iso-8859-1"),"UTF-8");
			request.setAttribute("message", message);
		}

		Integer lohHouseInfoId = null;

		try {
			lohHouseInfoId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (lohHouseInfoId != null && lohHouseInfoId > 0) {

			// 查询房屋信息
			LohHouseInfo lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			
			// 查询房屋类型信息
			LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());
			
			// 根据房屋信息 Id 查询，房屋文件信息
			List<LohFileInfo> lohFileInfoList = lohService.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);
			
			// 添加房屋信息
			request.setAttribute("lohHouseInfo", lohHouseInfo);
			
			// 添加房屋类型
			request.setAttribute("lohHouseType", lohHouseType);
			
			// 添加房屋文件信息
			request.setAttribute("lohFileInfoList", lohFileInfoList);
		}


		request.getRequestDispatcher("/WEB-INF/html/loh/lease/show_release_house.jsp").forward(request, response);
	}

}
