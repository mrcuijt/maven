/**
 * 
 */
package science.mrcuijt.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class DemoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getMethod();
		
		String userName = null;
		String password = null;
		
//		if (method.equals("GET")) {
//			userName = request.getParameter("userName");
//			if (userName != null && userName.trim().length() > 0) {
//				userName = new String(userName.getBytes("iso-8859-1"), "UTF-8");
//			}
//			password = request.getParameter("password");
//			if (password != null && password.trim().length() > 0) {
//				password = new String(password.getBytes("iso-8859-1"), "UTF-8");
//			}
//		} else {
//			userName = request.getParameter("userName");
//			password = request.getParameter("password");
//		}

//		System.out.println(request.getMethod());
//		System.out.println(userName);
//		System.out.println(password);

		Enumeration<String> pnames = request.getParameterNames();
        while (pnames.hasMoreElements()) {
            String pname = pnames.nextElement();
            String pvalues[] = request.getParameterValues(pname);
            StringBuilder result = new StringBuilder(pname);
            result.append('=');
            for (int i = 0; i < pvalues.length; i++) {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(pvalues[i]);
            }
            System.out.println(result.toString());
        }
		
		response.sendRedirect(request.getContextPath() + "/html/demo.jsp");
//        request.getRequestDispatcher("/html/demo.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
