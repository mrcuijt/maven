/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class ErrorHandler extends HttpServlet {

	// 处理 GET 方法请求的方法
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		String message = "";

		StringBuffer strb = new StringBuffer();

		if (throwable == null && statusCode == null) {
			strb.append("错误信息丢失");
		} else if (statusCode != null) {
			strb.append("错误代码 : ");
			strb.append(statusCode);
		} else {
			strb.append("错误信息");
			strb.append("<br/>");
			strb.append("Servlet Name: ");
			strb.append(servletName);
			strb.append("<br/>");
			strb.append("异常类型: ");
			strb.append(throwable.getClass().getName());
			strb.append("<br/>");
			strb.append("请求 URI: ");
			strb.append(requestUri);
			strb.append("<br/>");
			strb.append("异常信息: ");
			strb.append(throwable.getMessage());
		}

		message = strb.toString();
		
		request.setAttribute("message", message);
		
		request.getRequestDispatcher("/WEB-INF/html/common/error.jsp").forward(request, response);
	}

	// 处理 POST 方法请求的方法
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
