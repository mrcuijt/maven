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
public class ToDeleteReleaseHouseServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ToDeleteReleaseHouseServlet.class);

	private LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
			LOG.info("Convert lohHouseInfoId [{}] has error", request.getParameter("id"));
			verifyResult = false;
			message = "Parameter Not Support";
		}

		// 验证请求参数
		if (verifyResult) {
			if (lohHouseInfoId == null || lohHouseInfoId <= 0) {
				LOG.info("if (lohHouseInfoId == null) [{}]", (lohHouseInfoId == null));
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

		LOG.info("查询指定房屋类型[{}]", lohHouseInfo.getLohHouseTypeId());
		// 查询房屋类型信息
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());

		LOG.info("查询全部房屋类型");
		// 查询所有的房屋文件信息
		List<LohFileInfo> lohFileInfoList = lohService
				.findLohFileInfoByLohHouseInfoId(lohHouseInfo.getLohHouseInfoId());

		// 根据地区 id 查询地区信息
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoProvinceId());
		RegionInfo provience = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoProvinceId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCityId());
		RegionInfo city = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
		LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCountyId());
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
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/lease/to_delete_release_house.jsp\").forward(request, response)");
	}

}
