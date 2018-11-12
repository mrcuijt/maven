/**
 * 
 */
package science.mrcuijt.loh.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import science.mrcuijt.loh.util.VerifyCode;

/**
 * @author Administrator
 *
 */
public class VerifyCodeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 创建对象
		VerifyCode vc = new VerifyCode();
		// 获取图片对象
		BufferedImage bi = vc.getImage();
		// 获得图片的文本内容
		String text = vc.getText();
		// 将系统生成的文本内容保存到session中
		request.getSession().setAttribute("verifyCode", text);
		// 向浏览器输出图片
		vc.output(bi, response.getOutputStream());

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
