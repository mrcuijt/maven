/**
 * 
 */
package science.mrcuijt.loh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class LoginFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(LoginFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// 获取用户请求资源路径
		String url = httpServletRequest.getRequestURI();

		// 判断用户是否是要去登录、登出还是注册
		if (url.endsWith(httpServletRequest.getContextPath() + "/login.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/logout.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/register.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/verifyCode.do")) {
			fc.doFilter(request, response);
			return;
		}

		// 判断用户是否登录
		// 获取 Session 中的用户登录标识
		Object loginInfoId = httpServletRequest.getSession().getAttribute("login_info_id");
		//
		if (loginInfoId == null) {
			// 跳转到 登录
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.do");
			return;
		}

		fc.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.debug("init LoginFilter ");
	}

}
