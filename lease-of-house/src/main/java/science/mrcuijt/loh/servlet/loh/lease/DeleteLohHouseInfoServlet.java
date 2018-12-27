/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class DeleteLohHouseInfoServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(GetRegionServlet.class);

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取用户登录标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 验证结果标识
		boolean verifyResult = true;

		// 获取租赁房屋id
		Integer lohHouseInfoId = null;

		// 消息
		String message = null;

		// 参数校验
		try {
			String paramId = request.getParameter("id");
			if (paramId != null && paramId.trim().length() > 0) {
				LOG.info("if(paramId != null && paramId.trim().length() > 0) [{}]", (paramId != null && paramId.trim().length() > 0));
				paramId = paramId.trim();
				lohHouseInfoId = Integer.parseInt(paramId);
			}
		} catch (NumberFormatException e) {
			LOG.info("Convert lohHouseId [{}] has error", request.getParameter("id"));
			verifyResult = false;
			message = "Parameter Not Support";
		}

		// 验证请求参数
		if (verifyResult) {
			if (lohHouseInfoId == null || lohHouseInfoId <= 0) {
				LOG.info("if (lohHouseId == null) [{}]", (lohHouseInfoId == null));
				message = "Parameter Not Support";
				verifyResult = false;
			}
		}

		// 验证房屋信息是否存在
		LohHouseInfo lohHouseInfo = null;
		if (verifyResult) {
			LOG.info("查询指定房屋租赁信息[{}]", lohHouseInfoId);
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if (lohHouseInfo == null) {
				LOG.info("if (lohHouseInfo == null) [{}]", (lohHouseInfo == null));
				verifyResult = false;
				message = "房屋租赁信息记录不存在，请重试。";
			}
		}

		// 验证是否是该用户发布的信息记录
		if (verifyResult) {
			if (userInfoId.intValue() != lohHouseInfo.getUserInfoId().intValue()) {
				LOG.info("if (userInfoId.intValue() != lohHouseInfo.getUserInfoId().intValue()) [{}]", (userInfoId.intValue() != lohHouseInfo.getUserInfoId().intValue()));
				verifyResult = false;
				message = "当前用户没有权限修改该房屋信息。";
			}
		}

		// 验证不通过
		if (!verifyResult) {
			LOG.info("if (!verifyResult) [{}]", (!verifyResult));
			message = URLEncoder.encode(message, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/loh/lease/main.do?message=" + message);
			LOG.info("response.sendRedirect({}\"/loh/lease/main.do?message={}\")", request.getContextPath(), URLDecoder.decode(message,"UTF-8"));
			return;
		}

		// 删除结果
		LOG.info("删除指定房屋[{}]信息记录", lohHouseInfo.getLohHouseInfoId());
		boolean deleteLohHouseResult = lohService.deleteLohHouseInfo(lohHouseInfo,
				request.getServletContext().getRealPath("/"));

		if (!deleteLohHouseResult) {
			LOG.info("if (!deleteLohHouseResult) [{}]", (!deleteLohHouseResult));
			message = "删除失败，请重试。";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/loh/lease/toDeleteReleaseHouse.do?id=" + lohHouseInfoId).forward(request, response);
			LOG.info("request.getRequestDispatcher(\"/loh/lease/toDeleteReleaseHouse.do?id=\" + lohHouseInfoId).forward(request, response)");
			return;
		}

		// 删除成功
		message = "删除成功！";
		// 中文参数做 URLEncode 加密
		message = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect(request.getContextPath() + "/loh/lease/deleteLohHouseInfoResult.do?message=" + message);
		LOG.info("response.sendRedirect(request.getContextPath() + \"/loh/lease/deleteLohHouseInfoResult.do?message={})", URLDecoder.decode(message, "UTF-8"));
	}
}
