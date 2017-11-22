/**
 * 
 * 
 * 创建时间：2017-11-19 下午10:00:19
 * @author：崔旧涛
 */
package science.mrcuijt.springmvc.controller.json;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 * 创建时间：2017-11-19 下午10:00:19
 * @author 崔旧涛
 * 
 */
@Controller
@RequestMapping("/json/")
public class JsonSupportController implements Serializable {

	@RequestMapping("helloJsonSupport")
	@ResponseBody
	public Object helloJsonSupport(HttpServletResponse response, Model m) {

		System.out.println("Hello JsonSupportController !");

		String[] arry = new String[] { "22", "33" , "中文" };
		
		m.addAttribute("message", "我是 JSON 消息");

		return arry;
	}

}
