/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.net.URLEncoder;

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
public class DeleteLohHouseInfoServlet extends HttpServlet {

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取用户登录标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 验证结果标识
		boolean verifyReuslt = true;

		// 获取租赁房屋id
		Integer lohHouseInfoId = null;

		// 消息
		String message = null;

		// 参数校验
		try {
			lohHouseInfoId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			verifyReuslt = false;
			message = "参数错误";
			e.printStackTrace();
		}

		LohHouseInfo lohHouseInfo = null;
		if (verifyReuslt) {
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if (lohHouseInfo == null) {
				verifyReuslt = false;
				message = "房屋租赁信息记录不存在，请重试。";
			}
		}

		// 验证是否是该用户发布的信息记录
		if (verifyReuslt) {
			if (userInfoId.intValue() != lohHouseInfo.getUserInfoId()) {
				verifyReuslt = false;
				message = "当前用户没有权限修改该房屋信息。";
			}
		}

		// 验证不通过
		if (!verifyReuslt) {
			// 设置通知消息
			request.setAttribute("messgage", message);
			request.getRequestDispatcher("/loh/lease/main.do").forward(request, response);
			return;
		}

		// 删除结果
		boolean deleteLohHouseResult = lohService.deleteLohHouseInfo(lohHouseInfo,
				request.getServletContext().getRealPath("/"));

		if (!deleteLohHouseResult) {
			message = "删除失败，请重试。";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/loh/lease/toDeleteReleaseHouse.do?id=" + lohHouseInfoId).forward(request,
					response);
			return;
		}

		// 删除成功
		message = "删除成功！";
		// 中文参数做 URLEncode 加密
		message = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect(request.getContextPath() + "/loh/lease/deleteLohHouseInfoResult.do?message=" + message);
	}
}
