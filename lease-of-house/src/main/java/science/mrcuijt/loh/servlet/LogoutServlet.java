/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class LogoutServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer userInfoId = (Integer) request.getSession().getAttribute("user_info_id");

		LOG.info("清除用户[{}]会话标识", userInfoId);
		// 清空 Session
		request.getSession().invalidate();

		response.sendRedirect(request.getContextPath() + "/login.do");
		LOG.info("response.sendRedirect(request.getContextPath() + \"/login.do\")");
	}

}
