/**
 * 
 * 
 * 创建时间：2017-10-29 上午8:26:49
 * @author：崔旧涛
 */
package science.mrcuijt.struts2.action;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 * 
 * 
 * 创建时间：2017-10-29 上午8:26:49
 * @author 崔旧涛
 * 
 */
@Namespace("/")
@ParentPackage("base")
@Action(value = "hello", results = { @Result(name = "success", type = "dispatcher", location = "/html/hello_world.jsp") })
public class HelloAction implements Serializable {

	private String message = null;

	/**
	 * 
	 * 
	 * 
	 * 开发时间：2017-10-29 上午8:28:19
	 * @author：崔旧涛
	 * @return
	 */
	public String execute() {

		message = "Hello Maven & Struts2";

		return "success";
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
