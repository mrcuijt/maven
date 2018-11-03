/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.entity.UserInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.RequestUtil;

/**
 * @author Administrator
 *
 */
public class RegisterServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(RegisterServlet.class);
	
	private LohService lohService = new LohServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/html/login/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 解决 POST 请求中文乱码问题
		request.setCharacterEncoding("UTF-8");

		// 获取用户名
		String userName = request.getParameter("username");

		// 获取密码
		String password = request.getParameter("password");
		
		String message = null;
		
		boolean verifyResult = true;
		
		// 基本数据校验
		if(userName == null || userName.trim().length() == 0) {
			message = "用户名不能为空";
			verifyResult = false;
		}else if(password == null || password.trim().length() == 0) {
			message = "密码不能为空";
			verifyResult = false;
		}
		
		// 查询是否有同名的用户
		if(verifyResult) {
			if(lohService.existsUser(userName)) {
				message = "用户名已存在";
				verifyResult = false;
			}
		}
		
		if(!verifyResult) {
			request.setAttribute("userName", userName);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/login/register.jsp").forward(request, response);
			return;
		}
		
		// 初始化用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setGmtCreate(new Date());
		userInfo.setGmtModified(new Date());
		userInfo.setUserName(userName);
		
		// 初始用户登录信息
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setGmtCreate(new Date());
		loginInfo.setGmtModified(new Date());
		loginInfo.setLoginAccount(userName);
		loginInfo.setLoginPassword(password);
		
		// 调用用户注册的业务逻辑
		boolean registerResult = lohService.userRegister(userInfo, loginInfo);
		
		if(!registerResult) {
			message = "用户注册失败，请刷新页面后重试。";
			request.setAttribute("userName", userName);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/html/login/register.jsp").forward(request, response);
			return;
		}
		
		message = "注册成功！";
		
		// 设置会话消息
		request.getSession().setAttribute("message", message);
		
		// 注册成功重定向到结果页面
		response.sendRedirect(request.getContextPath() + "/registerResult.do");
	}

}
