/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.AppMD5Util;

/**
 * @author Administrator
 *
 */
public class LoginServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
	
	private LohService lohService = new LohServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/html/login/login.jsp").forward(request, response);
		LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/login/login.jsp\").forward(request, response)");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean debug = LOG.isDebugEnabled();

		// 解决 POST 请求中文参数乱码问题
		request.setCharacterEncoding("UTF-8");

		// 获取登录用户名
		String userName = request.getParameter("userName");

		// 获取登录密码
		String password = request.getParameter("password");

		// 获取验证码
		String verifyCode = request.getParameter("verifyCode");

		// 消息
		String message = null;

		// 验证结果
		boolean verifyResult = true;

		// 基本数据校验
		if (userName == null || userName.trim().length() == 0) {
			LOG.info("if (userName == null || userName.trim().length() == 0) [{}]", (userName == null || userName.trim().length() == 0));
			message = "用户名不能为空";
			verifyResult = false;
		} else if (password == null || password.trim().length() == 0) {
			LOG.info("else if (password == null || password.trim().length() == 0) [{}]", (password == null || password.trim().length() == 0));
			message = "密码不能为空";
			verifyResult = false;
		}else if (verifyCode == null || verifyCode.trim().length() == 0) {
			LOG.info("else if (verifyCode == null || verifyCode.trim().length() == 0) [{}]", (verifyCode == null || verifyCode.trim().length() == 0));
			message = "验证码不能为空";
			verifyResult = false;
		}

		// 验证输入验证码
		if (verifyResult) {
			// TODO 密码复杂度校验、密码 MD5 加密操作
			if (!verifyCode.trim().equals(request.getSession().getAttribute("verifyCode"))) {
				message = "验证码输入有误";
				verifyResult = false;
			}
		}

		// 查询用户是否存在
		LoginInfo loginInfo = null;
		if (verifyResult) {
			LOG.info("根据用户名[{}]查询用户信息", userName);
			loginInfo = lohService.findLoginInfo(userName);

			if (loginInfo == null) {
				LOG.info("if(loginInfo == null) [{}]", (loginInfo == null));
				message = "用户名或密码错误请重试";
				verifyResult = false;
			}
		}

		// 验证密码是否正确
		if(verifyResult) {
			// 比对输入密码与用户密码是否相同
			if (!AppMD5Util.getMD5(password.trim()).equals(loginInfo.getLoginPassword())) {
				LOG.info("if (!AppMD5Util.getMD5(password.trim()).equals(loginInfo.getLoginPassword())) [{}]", (!AppMD5Util.getMD5(password.trim()).equals(loginInfo.getLoginPassword())));
				message = "用户名或密码错误请重试";
				verifyResult = false;
			}
		}

		// 验证不通过，登录失败
		if (!verifyResult) {
			LOG.info("if (!verifyResult) [{}]", (!verifyResult));
			LOG.info("Login Fail LoginUser={} message={}", userName, message);
			request.setAttribute("message", message);
			request.setAttribute("userName", userName);
			request.getRequestDispatcher("/WEB-INF/html/login/login.jsp").forward(request, response);
			LOG.info("request.getRequestDispatcher(\"/WEB-INF/html/login/login.jsp\").forward(request, response)");
			return;
		}

		// 登录成功
		// 保存用户登录标识
		request.getSession().setAttribute("login_info_id", loginInfo.getLoginInfoId());
		request.getSession().setAttribute("user_info_id", loginInfo.getUserInfoId());

		LOG.info("Login Success LoginUser=[{}]", userName);

		// 更新用户登录状态（1、记录用户登录时间 2、记录用户登录IP）
		loginInfo.setLastLoginTime(loginInfo.getCurrentLoginTime());
		loginInfo.setCurrentLoginTime(new Date());
		loginInfo.setLoginIp(request.getRemoteHost());

		try {
			LOG.info("更新用户[{}]登录信息", loginInfo.getUserInfoId());
			boolean updateResult = lohService.updateLoginInfo(loginInfo);
		} catch (SQLException e) {
			LOG.error("Login Success , Update LoginInfo Error userid=[{}]", loginInfo.getUserInfoId(), e);
		}

		//request.getRequestDispatcher("/WEB-INF/html/loh/main/main.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/loh/main.do");
		LOG.info("response.sendRedirect(request.getContextPath()+\"/loh/main.do\") {}{}", request.getContextPath(), "/loh/main.do");
	}
}
