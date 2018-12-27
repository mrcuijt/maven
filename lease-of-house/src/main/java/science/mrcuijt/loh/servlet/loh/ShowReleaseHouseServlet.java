/**
 * 
 */
package science.mrcuijt.loh.servlet.loh;

import java.io.IOException;
import java.util.Date;
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
import science.mrcuijt.loh.entity.LohHouseViewHistory;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ShowReleaseHouseServlet extends HttpServlet {

	private static Logger LOG = LoggerFactory.getLogger(ShowReleaseHouseServlet.class);

	private static LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = LOG.isDebugEnabled();

		Integer lohHouseInfoId = null;

		String message = null;

		boolean verifyResult = true;

		try {
			String strLohHouseInfoId = request.getParameter("id");
			if (strLohHouseInfoId != null && strLohHouseInfoId.trim().length() > 0) {
				LOG.info("if (strLohHouseInfoId != null && strLohHouseInfoId.trim().length() > 0) [{}]", (strLohHouseInfoId != null && strLohHouseInfoId.trim().length() > 0));
				strLohHouseInfoId = strLohHouseInfoId.trim();
				lohHouseInfoId = Integer.parseInt(strLohHouseInfoId);
			}
		} catch (NumberFormatException e) {
			LOG.error("Parse lohHouseInfoId has error {}", e.getMessage(), e);
			message = "Parameter Not Support";
			verifyResult = false;
		}

		if(verifyResult) {
			if (lohHouseInfoId == null || lohHouseInfoId <= 0) {
				LOG.info("if (lohHouseInfoId == null || lohHouseInfoId <= 0) [{}]", (lohHouseInfoId == null || lohHouseInfoId <= 0));
				message = "Parameter Not Support";
				verifyResult = false;
			}
		}

		// 查询房屋信息
		LohHouseInfo lohHouseInfo = null;
		if (verifyResult) {
			LOG.info("查询房屋信息[{}]", lohHouseInfoId);
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if (lohHouseInfo == null) {
				LOG.info("if(lohHouseInfo == null) [{}]", (lohHouseInfo == null));
				message = "房屋租赁信息记录不存在";
			}
		}

		// 验证不通过
		if (!verifyResult) {
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/common/error.jsp").forward(request, response);
			LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/common/error.jsp\").forward(request, response)");
			return;
		}

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 根据用户登录标识查询用户信息
		LOG.info("查询用户[{}]信息", userInfoId);
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		// 保存房屋信息浏览记录
		LohHouseViewHistory lohHouseViewHistory = null;
		LOG.info("根据当前用户[{}]查询房屋信息浏览记录[{}]", userInfo.getUserInfoId(), lohHouseInfoId);
		// 查询房屋信息浏览记录是否存在
		lohHouseViewHistory = lohService.findLohHouseViewHistoryByLohHouseInfoIdAndUserInfoId(
				lohHouseInfo.getLohHouseInfoId(), userInfo.getUserInfoId());

		if (lohHouseViewHistory == null) {
			LOG.info("if (lohHouseViewHistory == null) [{}]", (lohHouseViewHistory == null));
			// 添加新的房屋信息浏览记录
			lohHouseViewHistory = new LohHouseViewHistory();
			lohHouseViewHistory.setGmtCreate(new Date());
			lohHouseViewHistory.setGmtModified(new Date());
			lohHouseViewHistory.setLohHouseId(lohHouseInfo.getLohHouseInfoId());
			lohHouseViewHistory.setUserInfoId(userInfo.getUserInfoId());
			boolean addResult = lohService.addLohHouseViewHistory(lohHouseViewHistory);
			if (addResult) {
				LOG.info("if (addResult) [{}]", (addResult));
				LOG.info("Add LohHouseViewHistory Success {}", lohHouseViewHistory);
			} else {
				LOG.info("if (addResult) [{}]", (addResult));
				LOG.info("Add LohHouseViewHistory fail {}", lohHouseViewHistory);
			}
		} else {
			LOG.info("if (lohHouseViewHistory == null) [{}]", (lohHouseViewHistory == null));
			// 更新房屋信息浏览记录
			lohHouseViewHistory.setGmtModified(new Date());
			boolean updateResult = lohService.updateLohHouseViewHistoryByPrimaryKey(lohHouseViewHistory);
			if (updateResult) {
				LOG.info("if (updateResult) [{}]", (updateResult));
				LOG.info("Upadte LohHouseViewHistory [{}] Success", lohHouseViewHistory.getLohHouseViewHistoryId());
				} else {
				LOG.info("if (updateResult) [{}]", (updateResult));
				LOG.info("Upadte LohHouseViewHistory [{}] fail", lohHouseViewHistory);
			}
		}

		LOG.info("查询房屋类型为[{}]的信息", lohHouseInfo.getLohHouseTypeId());
		// 查询房屋类型信息
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());

		LOG.info("查询租赁房屋信息[{}]关联的房屋文件信息", lohHouseInfoId);
		// 根据房屋信息 Id 查询，房屋文件信息
		List<LohFileInfo> lohFileInfoList = lohService.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);

		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoProvinceId());
		// 根据地区 id 查询地区信息
		RegionInfo provience = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoProvinceId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCityId());
		RegionInfo city = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCountyId());
		RegionInfo country = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCountyId());

		// 添加房屋信息
		request.setAttribute("lohHouseInfo", lohHouseInfo);
		// 添加房屋类型
		request.setAttribute("lohHouseType", lohHouseType);
		// 添加房屋文件信息
		request.setAttribute("lohFileInfoList", lohFileInfoList);
		// 添加地区信息
		request.setAttribute("provience", provience);
		request.setAttribute("city", city);
		request.setAttribute("country", country);

		request.getRequestDispatcher("/WEB-INF/html/loh/main/show_release_house.jsp").forward(request, response);
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/main/show_release_house.jsp\").forward(request, response);");
	}

}
