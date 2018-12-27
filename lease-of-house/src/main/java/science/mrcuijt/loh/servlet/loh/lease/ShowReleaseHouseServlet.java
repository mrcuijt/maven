/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.entity.LohFileInfo;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ShowReleaseHouseServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ShowReleaseHouseServlet.class);

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = LOG.isDebugEnabled();

		boolean verifyResult = true;

		String message = null;

		// 获取消息
		message = request.getParameter("message");

		if (message != null && message.trim().length() > 0) {
			LOG.info("if (message != null && message.trim().length() > 0) [{}]", (message != null && message.trim().length() > 0));
			message = new String(message.getBytes("iso-8859-1"),"UTF-8");
			request.setAttribute("message", message);
		}

		Integer lohHouseInfoId = null;

		try {
			String paramId = request.getParameter("id");
			if (paramId != null && paramId.trim().length() > 0) {
				paramId = paramId.trim();
				lohHouseInfoId = Integer.parseInt(paramId);
			}
		} catch (NumberFormatException e) {
			LOG.warn("Convert lohHouseInfoId [{}] has error message=[{}]", request.getParameter("id"), e.getMessage());
			verifyResult = false;
			message = "Parameter Not Support";
		}

		if (verifyResult) {
			if (lohHouseInfoId == null || lohHouseInfoId <= 0) {
				LOG.info("if (lohHouseInfoId == null || lohHouseInfoId <= 0) [{}]", (lohHouseInfoId == null || lohHouseInfoId <= 0));
				verifyResult = false;
				message = "Parameter Not Support";
			}
		}

		// 查询房屋信息
		LohHouseInfo lohHouseInfo = null;
		if (verifyResult) {
			LOG.info("查找指定房屋信息[{}]信息", lohHouseInfoId);
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if (lohHouseInfo == null) {
				LOG.info("if (lohHouseInfo == null) [{}]", (lohHouseInfo == null));
				verifyResult = false;
				message = "房屋租赁信息不存在";
			}
		}

		if (!verifyResult) {
			message = URLEncoder.encode(message, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/loh/lease/main.do?message=" + message);
			LOG.info("response.sendRedirect(request.getContextPath() + \"/loh/lease/main.do?message=\")", URLDecoder .decode(message, "UTF-8"));
			return;
		}

		// 查询房屋类型信息
		LOG.info("查询指定房屋类型[{}]信息", lohHouseInfo.getLohHouseTypeId());
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());

		// 根据房屋信息 Id 查询，房屋文件信息
		LOG.info("查询房屋信息[{}]关联的房屋文件信息", lohHouseInfoId);
		List<LohFileInfo> lohFileInfoList = lohService.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);

		// 根据地区 id 查询地区信息
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoProvinceId());
		RegionInfo provience = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoProvinceId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCityId());
		RegionInfo city = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCountyId());
		RegionInfo country = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCountyId());

		// 设置房屋信息
		request.setAttribute("lohHouseInfo", lohHouseInfo);
		// 设置房屋类型
		request.setAttribute("lohHouseType", lohHouseType);
		// 设置房屋文件信息
		request.setAttribute("lohFileInfoList", lohFileInfoList);
		// 设置地区信息
		request.setAttribute("provience", provience);
		request.setAttribute("city", city);
		request.setAttribute("country", country);

		request.getRequestDispatcher("/WEB-INF/html/loh/lease/show_release_house.jsp").forward(request, response);
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/lease/show_release_house.jsp\").forward(request, response)");
	}

}
