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
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ToUpdateReleaseHouseServlet extends HttpServlet {

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 获取修改房屋的 ID
		Integer lohHouseId = null;

		// 消息
		String message = null;

		try {
			lohHouseId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 验证参数
		if (lohHouseId == null) {

			message = "房屋信息不存在";
			response.sendRedirect(request.getContextPath() + "loh/lease/main.do");
			return;
		}

		// 查询房屋信息是否存在
		LohHouseInfo lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseId);

		if (lohHouseInfo == null) {

			message = "房屋信息不存在";
			response.sendRedirect(request.getContextPath() + "loh/lease/main.do");
			return;
		}

		// 查询房屋信息是否属于当前登录用户
		if (userInfoId.intValue() != lohHouseInfo.getUserInfoId().intValue()) {

			message = "当前用户没有权限修改该房屋信息。";
			response.sendRedirect(request.getContextPath() + "loh/lease/main.do");
			return;
		}

		// 查询房屋类型
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());
		
		// 查询房屋类型列表
		List<LohHouseType> LohHouseTypeList = lohService.findAllLohHouseType();
		
		// 加载地区信息
		List<RegionInfo> provinceList = null;
		List<RegionInfo> cityList = null;
		List<RegionInfo> countyList = null;
		
		provinceList = lohService.findRegionInfoByLevel(1);
		
		if(lohHouseInfo.getRegionInfoCityId() != null && lohHouseInfo.getRegionInfoCityId() > 0) {
			RegionInfo regionInfo = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
			cityList = lohService.findRegionInfoByParentRegionId(regionInfo.getParentRegionId());
		}
		
		if(lohHouseInfo.getRegionInfoCountyId() != null && lohHouseInfo.getRegionInfoCountyId() > 0) {
			RegionInfo regionInfo = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCountyId());
			countyList = lohService.findRegionInfoByParentRegionId(regionInfo.getParentRegionId());
		}
		
		
		// 查询房屋信息关联的房屋文件信息
		List<LohFileInfo> lohFileInfos = lohService.findLohFileInfoByLohHouseInfoId(lohHouseId);

		// 添加房屋信息
		request.setAttribute("lohHouseInfo", lohHouseInfo);
		// 添加房屋类型
		
		// 添加所有的房屋类型
		request.setAttribute("LohHouseTypeList", LohHouseTypeList);
		// 添加地区信息
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("countyList", countyList);
		// 添加房屋文件信息
		request.setAttribute("lohFileInfos", lohFileInfos);

		request.getRequestDispatcher("/WEB-INF/html/loh/lease/to_update_release_house.jsp").forward(request, response);
	}

}
