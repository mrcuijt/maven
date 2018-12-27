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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author Administrator
 *
 */
public class LoginFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

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

		boolean debug = LOG.isDebugEnabled();

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// 获取用户请求资源路径
		String url = httpServletRequest.getRequestURI();

		MDC.put("remoteHost", request.getRemoteHost());
		MDC.put("localAddr", request.getLocalAddr());
		MDC.put("sessionId", ((HttpServletRequest) request).getRequestedSessionId());
		// 判断用户是否是要去登录、登出还是注册
		if (url.endsWith(httpServletRequest.getContextPath() + "/login.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/logout.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/register.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/verifyCode.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/main/main.do")
				|| url.endsWith(httpServletRequest.getContextPath() + "/main/getRegion.do")) {
			LOG.info("requestURI={}", url);
			LOG.info("serverPath={}", httpServletRequest.getServletPath());
			fc.doFilter(request, response);
			MDC.clear();
			return;
		}

		// 判断用户是否登录
		// 获取 Session 中的用户登录标识
		Object loginInfoId = httpServletRequest.getSession().getAttribute("login_info_id");
		// 获取 Session 中的用户表id
		Object userInfoId = httpServletRequest.getSession().getAttribute("user_info_id");
		if (loginInfoId == null) {
			// 跳转到 登录
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.do");
			LOG.info("httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + \"/login.do\");");
			MDC.clear();
			return;
		}
		MDC.put("userid", userInfoId.toString());
		LOG.info("requestURI={}", url);
		LOG.info("serverPath={}", httpServletRequest.getServletPath());
		fc.doFilter(request, response);
		MDC.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.info("init LoginFilter");
	}

}
