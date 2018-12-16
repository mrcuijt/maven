/**
 * 
 */
package science.mrcuijt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class DispatcherRequsetGetServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = null;
		String password = null;

		userName = request.getParameter("userName");
		password = request.getParameter("password");
		
		System.out.println(request.getQueryString());
		System.out.println(request.getMethod());
		System.out.println(userName);
		System.out.println(password);

		request.getRequestDispatcher("/html/get.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.service(request, response);
	}
}
