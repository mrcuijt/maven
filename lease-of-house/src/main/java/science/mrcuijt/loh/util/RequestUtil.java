/**
 * 
 */
package science.mrcuijt.loh.util;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

/**
 * @author Administrator
 *
 */
public class RequestUtil {

	public static void parseRequest(HttpServletRequest request) {

		Date date = new Date();

		System.out.println("AuthType：\t" + request.getAuthType());
		System.out.println("CharacterEncoding：\t" + request.getCharacterEncoding());
		System.out.println("ContentLength：\t" + request.getContentLength());
		// System.out.println(request.getContentLengthLong());
		// java.lang.NoSuchMethodError:
		// javax.servlet.http.HttpServletRequest.getContentLengthLong()J
		System.out.println("ContentType：\t" + request.getContentType());
		System.out.println("ContextPath：\t" + request.getContextPath());
		System.out.println("Cookies：\t" + JSON.toJSONString(request.getCookies()));
		System.out.println("DispatcherType：\t" + request.getDispatcherType());
		System.out.println("HeaderNames：\t" + JSON.toJSONString(request.getHeaderNames()));
		System.out.println("LocalAddr：\t" + request.getLocalAddr());
		System.out.println("Locale：\t" + request.getLocale());
		System.out.println("LocalName：\t" + request.getLocalName());
		System.out.println("LocalPort：\t" + request.getLocalPort());
		System.out.println("Method：\t" + request.getMethod());
		System.out.println("PathInfo：\t" + request.getPathInfo());
		System.out.println("QueryString：\t" + request.getQueryString());
		System.out.println("RemoteAddr：\t" + request.getRemoteAddr());
		System.out.println("RemoteHost：\t" + request.getRemoteHost());
		System.out.println("RemotePort：\t" + request.getRemotePort());
		System.out.println("RemoteUser：\t" + request.getRemoteUser());
		System.out.println("RequestedSessionId：\t" + request.getRequestedSessionId());
		System.out.println("RequestURI：\t" + request.getRequestURI());
		System.out.println("RequestURL：\t" + request.getRequestURL());
		System.out.println("Scheme：\t" + request.getScheme());
		System.out.println("ServerName：\t" + request.getServerName());
		System.out.println("ServerPort：\t" + request.getServerPort());
		System.out.println("ServletPath：\t" + request.getServletPath());

		System.out.println("服务器接收到一个新的请求：");
		System.out.print("当前服务器时间为：");
		System.out.print(String.format("%tF ", date));
		System.out.println(String.format("%tT", date));
		System.out.println("==========================================");
		System.out.println("参数列表为：");

		Enumeration<String> param = request.getParameterNames();

		while (param.hasMoreElements()) {

			String parameterName = param.nextElement();

			String[] parameterValues = request.getParameterValues(parameterName);

			System.out.println(
					"ParameterName :" + parameterName + "\t ParameterValue :" + JSON.toJSONString(parameterValues));
		}

		System.out.println("读取请求参数结束。");
		System.out.println();
		System.out.println();
	}
}
