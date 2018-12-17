/**
 * 
 */
package science.mrcuijt.loh.servlet.loh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class ViewHistoryServlet extends HttpServlet {

	private static Logger log = LoggerFactory.getLogger(ViewHistoryServlet.class);

	private static LohService lohService = new LohServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = log.isDebugEnabled();

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 根据用户登录标识查询用户信息
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		// 页面序号
		Integer pageIndex = null;
		// 页面尺寸
		Integer pageSize = null;

		// 准备分页参数
		String strPageIndex = request.getParameter("pageIndex");
		String strPageSize = request.getParameter("pageSize");

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

		// 分页查询条件
		Map<String, Object> queryParam = new HashMap<String, Object>();

		// 用户标识
		queryParam.put("userInfoId", userInfoId);

		// 分页查询
		Map<String, Object> pagination = lohService.queryLohHouseViewHistoryPagination(pageIndex, pageSize, queryParam);

		// 查询自己发布的房屋信息列表
		request.setAttribute("pageIndex", pagination.get("pageIndex"));
		request.setAttribute("pageSize", pagination.get("pageSize"));
		request.setAttribute("totalPage", pagination.get("totalPage"));
		request.setAttribute("totalRecord", pagination.get("totalRecord"));
		request.setAttribute("pagination", pagination.get("pagination"));
		request.setAttribute("lohHouseInfoList", pagination.get("lohHouseInfoList"));

		request.getRequestDispatcher("/WEB-INF/html/loh/main/view_history.jsp").forward(request, response);
	}
}
