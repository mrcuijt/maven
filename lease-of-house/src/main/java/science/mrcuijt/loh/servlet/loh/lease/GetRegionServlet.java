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

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.entity.RegionInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;

/**
 * @author Administrator
 *
 */
public class GetRegionServlet extends HttpServlet {

	LohService lohService = new LohServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 消息
		String message = null;

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		String strRegionId = request.getParameter("regionId");

		Integer regionId = null;
		try {
			regionId = Integer.parseInt(strRegionId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (regionId == null) {

			pw.write("{}");
			pw.flush();
			pw.close();
			return;
		}

		// 根据 regionId 查询所有子级 Region 信息
		List<RegionInfo> regionInfo = lohService.findRegionInfoByParentRegionId(regionId);
		
		pw.write(JSON.toJSONString(regionInfo));
		
		pw.flush();
		
		pw.close();
	}
}
