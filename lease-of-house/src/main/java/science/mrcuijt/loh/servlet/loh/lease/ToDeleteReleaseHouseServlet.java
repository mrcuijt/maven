/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.util.ArrayList;
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
public class ToDeleteReleaseHouseServlet extends HttpServlet {

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

		// 查询房屋类型信息
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());
		
		// 查询所有的房屋文件信息
		List<LohFileInfo> lohFileInfoList = lohService
				.findLohFileInfoByLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());
		
		// 根据地区 id 查询地区信息
		RegionInfo provience = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoProvinceId());
		RegionInfo city = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
		RegionInfo country = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCountyId());
		
		// 添加房屋信息
		request.setAttribute("lohHouseInfo", lohHouseInfo);
		// 添加房屋文件信息
		request.setAttribute("lohFileInfoList", lohFileInfoList);
		
		// 添加房屋类型信息
		request.setAttribute("lohHouseType", lohHouseType);
		
		// 添加地区信息
		request.setAttribute("provience", provience);
		request.setAttribute("city", city);
		request.setAttribute("country", country);

		// 将请求转发到 JSP
		request.getRequestDispatcher("/WEB-INF/html/loh/lease/to_delete_release_house.jsp").forward(request, response);
	}

}
