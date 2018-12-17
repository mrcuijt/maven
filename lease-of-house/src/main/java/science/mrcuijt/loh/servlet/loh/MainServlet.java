/**
 * 
 */
package science.mrcuijt.loh.servlet.loh;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.comm.LohConstants;
import science.mrcuijt.loh.entity.LohHouseType;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class MainServlet extends HttpServlet {

	private static Logger log = LoggerFactory.getLogger(MainServlet.class);

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = log.isDebugEnabled();

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 根据用户登录标识查询用户信息
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		// 根据用户信息Id 查询发布的房屋信息列表(分页查询)

		// 房屋所在省
		String province = request.getParameter("province");
		// 房屋所在市
		String city = request.getParameter("city");
		// 房屋所在县
		String county = request.getParameter("county");

		// 房屋类型
		String lohHouseType = request.getParameter("houseType");
		Integer lohHouseTypeId = null;
		// 房屋价格
		String lohPrice = request.getParameter("lohPrice");
		BigDecimal price = null;

		// 房屋地址
		String houseAddress = request.getParameter("houseAddress");

		// 数据校验处理

		// 房屋所在省id
		Integer provinceId = null;
		// 房屋所在市id
		Integer cityId = null;
		// 房屋所在县id
		Integer countyId = null;

		try {
			if (province != null && province.trim().length() > 0) {
				province = province.trim();
				provinceId = Integer.parseInt(province);
			}
		} catch (NumberFormatException e) {
			if (LohConstants.getProvinceId() > 0) {
				provinceId = LohConstants.getProvinceId();
			}
			if (debug) {
				log.debug("Convert provinceId fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}
		try {
			if (city != null && city.trim().length() > 0) {
				city = city.trim();
				cityId = Integer.parseInt(city);
			}
		} catch (NumberFormatException e) {
			if (debug) {
				log.debug("Convert cityId fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}
		try {
			if (county != null && county.trim().length() > 0) {
				county = county.trim();
				countyId = Integer.parseInt(county);
			}
		} catch (NumberFormatException e) {
			if (debug) {
				log.debug("Convert countyId fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}

		if (lohHouseType != null && lohHouseType.trim().length() > 0) {
			lohHouseType = lohHouseType.trim();
			try {
				lohHouseTypeId = Integer.parseInt(lohHouseType);
				if(lohHouseTypeId <= 0) {
					lohHouseTypeId = null;
				}
			} catch (NumberFormatException e) {
				if (debug) {
					log.debug("Convert lohHouseTypeId fail userId={} message={}", userInfoId, e.getMessage(), e);
				}
			}
		}

		if (lohPrice != null && lohPrice.trim().length() > 0) {
			try {
				lohPrice = lohPrice.trim();
				price = new BigDecimal(lohPrice);
			} catch (Exception e) {
				log.debug("Convert price fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}

		if (houseAddress != null && houseAddress.trim().length() > 0) {
			houseAddress = houseAddress.trim();
			houseAddress = new String(houseAddress.getBytes("ISO-8859-1"), "UTF-8");
		}

		// 准备分页参数
		String strPageIndex = request.getParameter("pageIndex");
		String strPageSize = request.getParameter("pageSize");

		Integer pageIndex = null;
		try {
			if (strPageIndex != null && strPageIndex.trim().length() > 0) {
				strPageIndex = strPageIndex.trim();
				pageIndex = Integer.parseInt(strPageIndex);
			}
		} catch (NumberFormatException e) {
			if (debug) {
				log.debug("Convert pageIndex fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}
		Integer pageSize = null;
		try {
			if (strPageSize != null && strPageSize.trim().length() > 0) {
				strPageSize = strPageSize.trim();
				pageSize = Integer.parseInt(strPageSize);
			}
		} catch (NumberFormatException e) {
			if (debug) {
				log.debug("Convert pageSize fail userId={} message={}", userInfoId, e.getMessage(), e);
			}
		}

		if (pageIndex == null || pageIndex <= 0) {
			pageIndex = 1;
		}

		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}

		// 分页查询条件
		Map<String, Object> queryParam = new HashMap<String, Object>();

		// 房屋所在省id
		if (provinceId != null) {
			queryParam.put("provinceId", provinceId);
		}

		// 房屋所在市id
		if (cityId != null && cityId > 0) {
			queryParam.put("cityId", cityId);
		}

		// 房屋所在县id
		if (countyId != null && countyId > 0) {
			queryParam.put("countyId", countyId);
		}

		// 房屋类型
		if (lohHouseTypeId != null) {
			queryParam.put("lohHouseTypeId", lohHouseTypeId);
		}

		// 房屋价格
		if (price != null) {
			queryParam.put("lohPrice", price);
		}

		// 房屋所在地
		if (houseAddress != null) {
			queryParam.put("houseAddress", houseAddress);
		}
		// 分页查询
		Map<String, Object> pagination = lohService.queryHouseInfoPagination(pageIndex, pageSize, queryParam);

		StringBuffer strbQueryParam = new StringBuffer();
		// 房屋所在省id
		if (provinceId != null) {
			strbQueryParam.append("&province=");
			strbQueryParam.append(provinceId);
		}

		// 房屋所在市id
		if (cityId != null && cityId > 0) {
			strbQueryParam.append("&city=");
			strbQueryParam.append(cityId);
		}

		// 房屋所在县id
		if (countyId != null && countyId > 0) {
			strbQueryParam.append("&county=");
			strbQueryParam.append(countyId);
		}

		// 房屋类型 id
		if (lohHouseTypeId != null) {
			strbQueryParam.append("&houseType=");
			strbQueryParam.append(lohHouseTypeId);
		}

		// 房屋价格
		if (price != null) {
			strbQueryParam.append("&lohPrice=");
			strbQueryParam.append(price);
		}

		// 房屋地址
		if (houseAddress != null) {
			strbQueryParam.append("&houseAddress=");
			strbQueryParam.append(houseAddress);
		}

		// 加载地区信息
		List<RegionInfo> provinceList = lohService.findRegionInfoByLevel(1);
		List<RegionInfo> cityList = null;
		List<RegionInfo> countyList = null;

		if (cityId != null && cityId > 0) {
			cityList = lohService.findRegionInfoByParentRegionId(provinceId);
		}
		if (countyId != null && countyId > 0) {
			countyList = lohService.findRegionInfoByParentRegionId(cityId);
		}

		// 查询加载房屋类型
		List<LohHouseType> lohHouseTypeList = lohService.findAllLohHouseType();

		// 添加地区信息
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("countyList", countyList);

		// 添加房屋类型
		request.setAttribute("lohHouseTypeList", lohHouseTypeList);

		// 查询自己发布的房屋信息列表
		request.setAttribute("pageIndex", pagination.get("pageIndex"));
		request.setAttribute("pageSize", pagination.get("pageSize"));
		request.setAttribute("totalPage", pagination.get("totalPage"));
		request.setAttribute("totalRecord", pagination.get("totalRecord"));
		request.setAttribute("pagination", pagination.get("pagination"));
		request.setAttribute("queryParam", strbQueryParam);
		request.setAttribute("province", provinceId);
		request.setAttribute("city", cityId);
		request.setAttribute("county", countyId);
		request.setAttribute("lohHouseTypeId", lohHouseTypeId);
		request.setAttribute("lohPrice", price);
		request.setAttribute("houseAddress", houseAddress);

		request.getRequestDispatcher("/WEB-INF/html/loh/main/main.jsp").forward(request, response);
	}
}
