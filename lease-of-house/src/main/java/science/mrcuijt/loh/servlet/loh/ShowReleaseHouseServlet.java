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

	private static Logger log = LoggerFactory.getLogger(ShowReleaseHouseServlet.class);

	private static LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = log.isDebugEnabled();

		String message = null;

		// 获取消息
		message = request.getParameter("message");

		if (message != null && message.trim().length() > 0) {
			message = new String(message.getBytes("iso-8859-1"), "UTF-8");
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

			// 保存房屋信息浏览记录
			LohHouseViewHistory lohHouseViewHistory = null;

			// 获取登录用户标识
			Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

			Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

			// 根据用户登录标识查询用户信息
			UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

			// 查询房屋信息浏览记录是否存在
			lohHouseViewHistory = lohService.findLohHouseViewHistoryByLohHouseInfoIdAndUserInfoId(
					lohHouseInfo.getLohHouseInfoId(), userInfo.getUserInfoId());

			if (lohHouseViewHistory == null) {
				// 添加新的房屋信息浏览记录
				lohHouseViewHistory = new LohHouseViewHistory();
				lohHouseViewHistory.setGmtCreate(new Date());
				lohHouseViewHistory.setGmtModified(new Date());
				lohHouseViewHistory.setLohHouseId(lohHouseInfo.getLohHouseInfoId());
				lohHouseViewHistory.setUserInfoId(userInfo.getUserInfoId());
				boolean addResult = lohService.addLohHouseViewHistory(lohHouseViewHistory);
				if (!addResult) {
					log.info("Add LohHouseViewHistory fail {}", lohHouseViewHistory);
				}
			} else {
				// 更新房屋信息浏览记录
				lohHouseViewHistory.setGmtModified(new Date());
				boolean updateResult = lohService.updateLohHouseViewHistoryByPrimaryKey(lohHouseViewHistory);
				if (!updateResult) {
					log.info("Upadte LohHouseViewHistory fail {}", lohHouseViewHistory);
				}
			}

			// 查询房屋类型信息
			LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());

			// 根据房屋信息 Id 查询，房屋文件信息
			List<LohFileInfo> lohFileInfoList = lohService.findLohFileInfoByLohHouseInfoId(lohHouseInfoId);

			// 根据地区 id 查询地区信息
			RegionInfo provience = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoProvinceId());
			RegionInfo city = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
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
		}

		request.getRequestDispatcher("/WEB-INF/html/loh/main/show_release_house.jsp").forward(request, response);

	}

}
