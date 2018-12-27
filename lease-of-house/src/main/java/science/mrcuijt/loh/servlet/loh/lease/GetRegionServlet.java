/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.comm.LohConstants;
import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.entity.vo.RegionInfoVO;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class GetRegionServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(GetRegionServlet.class);

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		response.setContentType("text/plain;charset=UTF-8");

		PrintWriter pw = response.getWriter();

		Integer regionId = null;

		try {
			String strRegionId = request.getParameter("regionId");
			if (strRegionId != null && strRegionId.trim().length() > 0) {
				strRegionId = strRegionId.trim();
				regionId = Integer.parseInt(strRegionId);
			}
		} catch (NumberFormatException e) {
			LOG.error("Convert regionId has error {}", e.getMessage(), e);
		}

		if (regionId == null) {
			LOG.info("if (regionId == null) [{}]", (regionId == null));
			LOG.info("返回接口请求的异常消息");
			// 返回接口调用失败时的异常消息
			RegionInfoVO<RegionInfo> regionInfoVO = new RegionInfoVO<>();
			regionInfoVO.setMessage("Not Support parameter");
			regionInfoVO.setStatus(LohConstants.ERROR_CODE);
			pw.write(JSON.toJSONString(regionInfoVO));
			pw.flush();
			pw.close();
			return;
		}

		// 根据 regionId 查询所有子级 Region 信息
		LOG.info("查询指定父级地区[{}]下的子地区信息", regionId);
		List<RegionInfo> regionInfoList = lohService.findRegionInfoByParentRegionId(regionId);
		LOG.info("返回地区信息的JSON字符串");
		RegionInfoVO<RegionInfo> regionInfoVO = new RegionInfoVO<>();
		regionInfoVO.setMessage(LohConstants.SUCCESS_MESSAGE);
		regionInfoVO.setStatus(LohConstants.SUCCESS_CODE);
		regionInfoVO.setResult(regionInfoList);
		pw.write(JSON.toJSONString(regionInfoVO));
		pw.flush();
		pw.close();
	}
}
