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
public class ToUpdateReleaseHouseServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ToUpdateReleaseHouseServlet.class);

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = LOG.isDebugEnabled();

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 获取修改房屋的 ID
		Integer lohHouseId = null;

		// 请求参数验证结果
		boolean verifyResult = true;

		// 消息
		String message = null;

		try {
			String paramId = request.getParameter("id");
			if (paramId != null && paramId.trim().length() > 0) {
				LOG.info("if(paramId != null && paramId.trim().length() > 0) [{}]", (paramId != null && paramId.trim().length() > 0));
				paramId = paramId.trim();
				lohHouseId = Integer.parseInt(paramId);
			}
		} catch (NumberFormatException e) {
			LOG.info("Convert lohHouseId [{}] has error", request.getParameter("id"));
			verifyResult = false;
			message = "Parameter Not Support";
		}

		// 验证请求参数
		if (verifyResult) {
			if (lohHouseId == null || lohHouseId <= 0) {
				LOG.info("if (lohHouseId == null) [{}]", (lohHouseId == null));
				message = "Parameter Not Support";
				verifyResult = false;
			}
		}

		// 验证房屋信息是否存在
		LohHouseInfo lohHouseInfo = null;
		if (verifyResult) {
			LOG.info("查询指定房屋租赁信息[{}]", lohHouseId);
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseId);
			if (lohHouseInfo == null) {
				LOG.info("if (lohHouseInfo == null) [{}]", (lohHouseInfo == null));
				verifyResult = false;
				message = "房屋租赁信息记录不存在，请重试。";
			}
		}

		// 验证房屋信息是否属于当前登录用户
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
		// 查询房屋类型
		LohHouseType lohHouseType = lohService.findLohHouseTypeByPrimaryKey(lohHouseInfo.getLohHouseTypeId());

		LOG.info("查询全部房屋类型");
		// 查询房屋类型列表
		List<LohHouseType> LohHouseTypeList = lohService.findAllLohHouseType();

		// 加载地区信息
		List<RegionInfo> provinceList = null;
		List<RegionInfo> cityList = null;
		List<RegionInfo> countyList = null;

		LOG.info("查询地区级别为[{}]的地区信息", 1);
		provinceList = lohService.findRegionInfoByLevel(1);

		if (lohHouseInfo.getRegionInfoCityId() != null && lohHouseInfo.getRegionInfoCityId() > 0) {
			LOG.info("if (lohHouseInfo.getRegionInfoCityId() != null && lohHouseInfo.getRegionInfoCityId() > 0) [{}]",  (lohHouseInfo.getRegionInfoCityId() != null && lohHouseInfo.getRegionInfoCityId() > 0));
			LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCityId());
			RegionInfo regionInfo = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCityId());
			LOG.info("查询相同父级地区[{}]的地区信息列表", regionInfo.getParentRegionId());
			cityList = lohService.findRegionInfoByParentRegionId(regionInfo.getParentRegionId());
		}

		if(lohHouseInfo.getRegionInfoCountyId() != null && lohHouseInfo.getRegionInfoCountyId() > 0) {
			LOG.info("查询指定地区信息[{}]", lohHouseInfo.getRegionInfoCountyId());
			RegionInfo regionInfo = lohService.findRegionInfoByPrimaryKey(lohHouseInfo.getRegionInfoCountyId());
			LOG.info("查询相同父级地区[{}]的地区信息列表", regionInfo.getParentRegionId());
			countyList = lohService.findRegionInfoByParentRegionId(regionInfo.getParentRegionId());
		}

		// 查询房屋信息关联的房屋文件信息
		LOG.info("查询房屋信息[{}]关联的房屋文件信息", lohHouseId);
		List<LohFileInfo> lohFileInfos = lohService.findLohFileInfoByLohHouseInfoId(lohHouseId);

		// 添加房屋信息
		request.setAttribute("lohHouseInfo", lohHouseInfo);
		// 添加所有的房屋类型
		request.setAttribute("LohHouseTypeList", LohHouseTypeList);
		// 添加地区信息
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("countyList", countyList);
		// 添加房屋文件信息
		request.setAttribute("lohFileInfos", lohFileInfos);

		request.getRequestDispatcher("/WEB-INF/html/loh/lease/to_update_release_house.jsp").forward(request, response);
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/loh/lease/to_update_release_house.jsp\").forward(request, response)");
	}

}
