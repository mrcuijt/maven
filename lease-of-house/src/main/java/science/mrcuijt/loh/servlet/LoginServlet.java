/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import science.mrcuijt.loh.entity.LoginInfo;
import science.mrcuijt.loh.service.LohService;
import science.mrcuijt.loh.service.impl.LohServiceImpl;
import science.mrcuijt.loh.util.AppMD5Util;
import science.mrcuijt.loh.util.RequestUtil;

/**
 * @author Administrator
 *
 */
public class LoginServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(LoginServlet.class);
	
	private LohService lohService = new LohServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/html/login/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
			message = "用户名不能为空";
			verifyResult = false;
		} else if (password == null || password.trim().length() == 0) {
			message = "密码不能为空";
			verifyResult = false;
		}else if (verifyCode == null || verifyCode.trim().length() == 0) {
			message = "验证码不能为空";
			verifyResult = false;
		}
		
		if (verifyResult) {
			// TODO 密码复杂度校验、密码 MD5 加密操作
			if (!verifyCode.trim().equals(request.getSession().getAttribute("verifyCode"))) {
				message = "验证码输入有误";
				verifyResult = false;
			}
		}
		
		// 查询用户是否存在
		LoginInfo loginInfo = null;
		if(verifyResult) {
			loginInfo = lohService.findLoginInfo(userName);
			
			if(loginInfo == null) {
				message = "用户名或密码错误请重试";
				verifyResult = false;
			}
			
			// 验证密码是否正确
			if(verifyResult) {
				// 比对输入密码与用户密码是否相同
				if(!AppMD5Util.getMD5(password.trim()).equals(loginInfo.getLoginPassword())) {
					message = "用户名或密码错误请重试";
					verifyResult = false;
				}
			}
		}
		
		// 验证不通过，登录失败
		if(!verifyResult) {
			request.setAttribute("message", message);
			request.setAttribute("userName", userName);
			request.getRequestDispatcher("/WEB-INF/html/login/login.jsp").forward(request, response);
			return;
		}
		
		// 登录成功
		// 保存用户登录标识
		request.getSession().setAttribute("login_info_id", loginInfo.getLoginInfoId());
		request.getSession().setAttribute("user_info_id", loginInfo.getUserInfoId());
		
		// 更新用户登录状态（1、记录用户登录时间 2、记录用户登录IP）
		loginInfo.setLastLoginTime(loginInfo.getCurrentLoginTime());
		loginInfo.setCurrentLoginTime(new Date());
		loginInfo.setLoginIp(request.getRemoteHost());
		
		boolean updateResult = lohService.updateLoginInfo(loginInfo);

		//request.getRequestDispatcher("/WEB-INF/html/loh/main/main.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/loh/main.do");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
	}

}
