/**
 * 
 */
package science.mrcuijt.loh.servlet.loh.lease;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class DeleteLohHouseInfoResultServelt extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收参数
		String message = request.getParameter("message");

		if (message != null && message.trim().length() > 0) {
			try {
				message = new String(message.getBytes("iso-8859-1"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 设置消息
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/html/loh/lease/delete_loh_house_info_result.jsp").forward(request,
				response);

	}

}
