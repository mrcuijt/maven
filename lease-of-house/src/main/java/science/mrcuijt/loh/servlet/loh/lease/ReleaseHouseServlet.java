/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ReleaseHouseServlet extends HttpServlet {

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/html/loh/lease/release_house.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String message = null;

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		String houseTitle = request.getParameter("houseTitle");

		String houseType = request.getParameter("houseType");

		String houseAddress = request.getParameter("houseAddress");

		String contacts = request.getParameter("contacts");

		String cellPhone = request.getParameter("cellPhone");

		LohHouseInfo lohHouseInfo = new LohHouseInfo();

		lohHouseInfo.setGmtCreate(new Date());
		lohHouseInfo.setGmtModified(new Date());
		lohHouseInfo.setHouseTitle(houseTitle);
		lohHouseInfo.setLohHouseTypeId(1);
		lohHouseInfo.setHouseAddress(houseAddress);
		lohHouseInfo.setContacts(contacts);
		lohHouseInfo.setCellPhone(cellPhone);
		lohHouseInfo.setUserInfoId(userInfoId);
		lohHouseInfo.setPushDate(new Date());

		boolean addLohHouseInfoResult = lohService.addLohHouseInfo(lohHouseInfo);

		if (!addLohHouseInfoResult) {
			message = "添加失败，请重试";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/loh/lease/release_house.jsp").forward(request, response);
			return;
		}

		// 添加成功，跳转到发布房屋信息列表页
		message = "添加成功！";
		response.sendRedirect(request.getContextPath() + "/loh/lease/releaseHouse.do?message=" + message);
	}

}
