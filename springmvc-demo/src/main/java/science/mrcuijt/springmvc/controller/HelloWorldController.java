/**    
 * @Title: HelloWorldController.java  
 * @Package net.hyzx999.datasupport.controller  
 * @Description: TODO  
 * @author mrcuijt
 * @date 2017年11月16日 下午2:34:46  
 * @version     
 */
package science.mrcuijt.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**  
 * @ClassName: HelloWorldController  
 * @Description: TODO  
 * @author mrcuijt
 * @date 2017年11月16日 下午2:34:46  
 *    
 */
@Controller
@RequestMapping("/")
public class HelloWorldController {

	@RequestMapping("hello")
	public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "Hello World!");
		mav.setViewName("html/hello/hello_world");
		return mav;
	}

}
