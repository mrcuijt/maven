/**
 * 
 */
package science.mrcuijt.loh.servlet.loh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.comm.LohConstants;
import science.mrcuijt.loh.entity.LohHouseInfo;
import science.mrcuijt.loh.entity.LohHouseViewHistory;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.entity.vo.LohHouseViewHistoryVO;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class DeleteLohHouseViewHistoryServlet extends HttpServlet {

	private static Logger log = LoggerFactory.getLogger(DeleteLohHouseViewHistoryServlet.class);

	private static LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 根据用户登录标识查询用户信息
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		Integer lohHouseInfoId = null;
		Integer lohHoueseViewHistoryId = null;

		try {
			if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0) {
				lohHouseInfoId = Integer.parseInt(request.getParameter("id"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		LohHouseViewHistoryVO lohHouseViewHistoryVO = new LohHouseViewHistoryVO();

		if (lohHouseInfoId == null) {
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Paramenter not support");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}
		
		// 查询房屋信息是否存在
		LohHouseInfo lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
		
		if (lohHouseInfo == null) {
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Paramenter not support");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}
		
		// 查询房屋信息浏览记录
		LohHouseViewHistory lohHouseViewHistory = lohService
				.findLohHouseViewHistoryByLohHouseInfoIdAndUserInfoId(lohHouseInfoId, userInfoId);

		if (lohHouseViewHistory == null) {
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Paramenter not support");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}
		
		boolean deleteLohHoueseViewHistoryResult = lohService
				.deleteLohHouseViewHistoryByPrimaryKey(lohHouseViewHistory.getLohHouseViewHistoryId());

		if (!deleteLohHoueseViewHistoryResult) {
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Delete fail , please try again ");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}

		PrintWriter out = response.getWriter();
		lohHouseViewHistoryVO.setStatus(LohConstants.SUCCESS_CODE);
		lohHouseViewHistoryVO.setMessage(LohConstants.SUCCESS_MESSAGE);
		out.write(JSON.toJSONString(lohHouseViewHistoryVO));
	}
}
