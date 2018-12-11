/**
 * 
 */
package science.mrcuijt.loh.servlet.main;

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

	private static Logger log = LoggerFactory.getLogger(GetRegionServlet.class);

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = log.isDebugEnabled();

		response.setCharacterEncoding("UTF-8");

		log.trace("Get response CharacterEncoding {}", response.getCharacterEncoding());

		response.setContentType("text/plain;charset=UTF-8");

		log.trace("Get response ContentType {}", response.getContentType());

		// 消息
		String message = null;

		PrintWriter pw = response.getWriter();

		String strRegionId = request.getParameter("regionId");

		log.info("Get requset parameter regionId {}", strRegionId);

		Integer regionId = null;

		try {

			regionId = Integer.parseInt(strRegionId);

			// 根据 regionId 查询所有子级 Region 信息
			List<RegionInfo> regionInfoList = lohService.findRegionInfoByParentRegionId(regionId);

			RegionInfoVO<RegionInfo> regionInfoVO = new RegionInfoVO<>();

			regionInfoVO.setMessage(LohConstants.SUCCESS_MESSAGE);

			regionInfoVO.setStatus(LohConstants.SUCCESS_CODE);

			regionInfoVO.setResult(regionInfoList);

			pw.write(JSON.toJSONString(regionInfoVO));

			pw.flush();

			pw.close();

		} catch (NumberFormatException e) {

			log.error("regionId parseInt error {}", strRegionId, e);

			// 返回接口调用失败时的异常消息

			RegionInfoVO<RegionInfo> regionInfoVO = new RegionInfoVO<>();

			regionInfoVO.setMessage("Not Supper parameter");

			regionInfoVO.setStatus(LohConstants.ERROR_CODE);

			pw.write(JSON.toJSONString(regionInfoVO));

			pw.flush();

			pw.close();
		}

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("Get region begin");

		super.service(request, response);

		log.info("Get region end");

	}

}
