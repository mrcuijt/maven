/**
 * 
 */
package science.mrcuijt.loh.servlet.admin.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.service.LohAdminService;
import science.mrcuijt.loh.service.impl.LohAdminServiceImpl;

/**
 * @author Administrator
 *
 */
public class AddDataServlet extends HttpServlet {

	private LohAdminService lohAdminService = new LohAdminServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 查询房屋类型
		List<LohHouseType> lohHouseTypeList = lohAdminService.findLohHouseTypeList();
		
		request.setAttribute("lohHouseTypeList", lohHouseTypeList);
		request.getRequestDispatcher("/WEB-INF/html/admin/data/add_data.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 解决 POST 请求中文乱码
		request.setCharacterEncoding("UTF-8");

		String houseType = request.getParameter("houseType");

		String message = "";

		// 基本数据校验
		if (houseType == null || houseType.trim().length() == 0) {

			message = "房屋类型不能为空";
			response.sendRedirect(request.getContextPath() + "/loh/admin/data/addData.do");
			return;
		}

		// 查找是否已有该房屋类型
		boolean exists = lohAdminService.findLohHouseTypeByHouseType(houseType);

		if (exists) {
			message = "已存在该房屋类型";
			response.sendRedirect(request.getContextPath() + "/loh/admin/data/addData.do");
			return;
		}

		// 添加该房屋类型
		LohHouseType lohHouseType = new LohHouseType();
		lohHouseType.setGmtCreate(new Date());
		lohHouseType.setGmtModified(new Date());
		lohHouseType.setHouseType(houseType);

		boolean addLohHouseTypeResult = lohAdminService.addLohHouseType(lohHouseType);

		if (!addLohHouseTypeResult) {
			message = "添加失败";
			response.sendRedirect(request.getContextPath() + "/loh/admin/data/addData.do");
			return;
		}

		message = "添加成功";
		response.sendRedirect(request.getContextPath() + "/loh/admin/data/addData.do");
	}

}
