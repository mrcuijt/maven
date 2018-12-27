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

	private static Logger LOG = LoggerFactory.getLogger(DeleteLohHouseViewHistoryServlet.class);

	private static LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取登录用户标识
		Integer loginInfoId = (Integer) request.getSession().getAttribute("login_info_id");

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		// 验证结果标识
		boolean verifyResult = true;

		LOG.info("查询指定用户信息[{}]记录", userInfoId);
		// 根据用户登录标识查询用户信息
		UserInfo userInfo = lohService.findUserInfoByPrimaryKey(userInfoId);

		Integer lohHouseInfoId = null;
		Integer lohHoueseViewHistoryId = null;

		try {
			String paramId = request.getParameter("id");
			if (paramId != null && paramId.trim().length() > 0) {
				paramId = paramId.trim();
				lohHouseInfoId = Integer.parseInt(paramId);
			}
		} catch (NumberFormatException e) {
			LOG.info("Convert lohHouseInfoId [{}] has error", request.getParameter("id"));
			verifyResult = false;
		}

		LohHouseViewHistoryVO lohHouseViewHistoryVO = new LohHouseViewHistoryVO();

		if(verifyResult) {
			if (lohHouseInfoId == null || lohHouseInfoId <= 0) {
				LOG.info("if (lohHouseInfoId == null || lohHouseInfoId <= 0) [{}]", (lohHouseInfoId == null || lohHouseInfoId <= 0));
				verifyResult = false;
			}
		}
		
		// 查询房屋信息是否存在
		LohHouseInfo lohHouseInfo = null;
		if(verifyResult) {
			LOG.info("查询指定房屋信息[{}]记录", lohHouseInfoId);
			lohHouseInfo = lohService.findLohHouseInfoByPrimaryKey(lohHouseInfoId);
			if (lohHouseInfo == null) {
				verifyResult = false;
			}
		}
		
		// 查询房屋信息浏览记录
		LohHouseViewHistory lohHouseViewHistory = null;
		if(verifyResult) {
			LOG.info("查询当前用户[{}]指定房屋信息浏览记录[{}]", userInfoId, lohHouseInfoId);
			lohHouseViewHistory = lohService
					.findLohHouseViewHistoryByLohHouseInfoIdAndUserInfoId(lohHouseInfoId, userInfoId);
			if (lohHouseViewHistory == null) {
				LOG.info("if (lohHouseViewHistory == null) [{}]", (lohHouseViewHistory == null));
				verifyResult = false;
			}
		}

		// 验证不通过
		if (!verifyResult) {
			LOG.info("if (!verifyResult) [{}]", (!verifyResult));
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Parameter not support");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}

		LOG.info("删除指定房屋信息浏览记录[{}]", lohHouseViewHistory.getLohHouseViewHistoryId());
		boolean deleteLohHoueseViewHistoryResult = lohService
				.deleteLohHouseViewHistoryByPrimaryKey(lohHouseViewHistory.getLohHouseViewHistoryId());

		if (!deleteLohHoueseViewHistoryResult) {
			LOG.info("if (!deleteLohHoueseViewHistoryResult) [{}]", (!deleteLohHoueseViewHistoryResult));
			PrintWriter out = response.getWriter();
			lohHouseViewHistoryVO.setStatus(LohConstants.ERROR_CODE);
			lohHouseViewHistoryVO.setMessage("Delete fail , please try again ");
			out.write(JSON.toJSONString(lohHouseViewHistoryVO));
			return;
		}

		LOG.info("删除房屋信息浏览记录[{}]成功", lohHouseViewHistory.getLohHouseViewHistoryId());
		PrintWriter out = response.getWriter();
		lohHouseViewHistoryVO.setStatus(LohConstants.SUCCESS_CODE);
		lohHouseViewHistoryVO.setMessage(LohConstants.SUCCESS_MESSAGE);
		out.write(JSON.toJSONString(lohHouseViewHistoryVO));
	}
}
