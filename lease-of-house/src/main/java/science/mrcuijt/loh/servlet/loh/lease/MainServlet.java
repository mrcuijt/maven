/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class MainServlet extends HttpServlet {

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 根据用户登录标识查询用户信息
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		// 根据用户信息Id 查询发布的房屋信息列表(分页查询)

		// 房屋类型
		String lohHouseType = request.getParameter("lohHouseType");
		Integer lohHouseTypeId = null;
		// 房屋价格
		String lohPrice = request.getParameter("lohPrice");
		BigDecimal price = null;

		// 房屋地址
		String houseAddress = request.getParameter("houseAddress");

		// 数据校验处理
		if (lohHouseType != null && lohHouseType.trim().length() > 0) {
			try {
				lohHouseTypeId = Integer.parseInt(lohHouseType);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if (lohPrice == null || lohPrice.trim().length() == 0) {
			price = null;
		} else {
			price = new BigDecimal(lohPrice);
		}

		if (houseAddress == null || houseAddress.trim().length() == 0) {
			houseAddress = null;
		}

		// 准备分页参数
		String strPageIndex = request.getParameter("pageIndex");
		String strPageSize = request.getParameter("pageSize");

		if (strPageIndex == null || strPageIndex.trim().length() == 0) {
			strPageIndex = "0";
		}

		if (strPageSize == null || strPageSize.trim().length() == 0) {
			strPageSize = "0";
		}

		Integer pageIndex = Integer.parseInt(strPageIndex);
		Integer pageSize = Integer.parseInt(strPageSize);

		if (pageIndex == null || pageIndex <= 0) {
			pageIndex = 1;
		}

		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}

		// 分页查询条件
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userInfoId", userInfoId);
		if (lohHouseTypeId != null) {
			queryParam.put("lohHouseTypeId", lohHouseTypeId);
		}

		if (price != null) {
			queryParam.put("lohPrice", price);
		}

		if (houseAddress != null) {
			try {
				houseAddress = new String(houseAddress.getBytes("ISO-8859-1"), "UTF-8");
				queryParam.put("houseAddress", houseAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 分页查询
		Map<String, Object> pagination = lohService.queryHouseInfoPagination(pageIndex, pageSize, queryParam);

		StringBuffer strbQueryParam = new StringBuffer();
		if (lohHouseTypeId != null) {
			strbQueryParam.append("&lohHouseType=");
			strbQueryParam.append(lohHouseTypeId);
		}
		if (price != null) {
			strbQueryParam.append("&lohPrice=");
			strbQueryParam.append(price);
		}
		if (houseAddress != null) {
			strbQueryParam.append("&houseAddress=");
			strbQueryParam.append(houseAddress);
		}

		// 查询自己发布的房屋信息列表
		request.setAttribute("pageIndex", pagination.get("pageIndex"));
		request.setAttribute("pageSize", pagination.get("pageSize"));
		request.setAttribute("totalPage", pagination.get("totalPage"));
		request.setAttribute("totalRecord", pagination.get("totalRecord"));
		request.setAttribute("pagination", pagination.get("pagination"));
		request.setAttribute("queryParam", strbQueryParam);
		request.setAttribute("lohHouseType", lohHouseTypeId);
		request.setAttribute("lohPrice", price);
		request.setAttribute("houseAddress", houseAddress);
		request.getRequestDispatcher("/WEB-INF/html/loh/lease/main.jsp").forward(request, response);
	}

}
