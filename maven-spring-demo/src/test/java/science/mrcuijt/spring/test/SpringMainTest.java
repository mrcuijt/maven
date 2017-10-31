/**
 * 
 * 
 * 创建时间：2017-10-31 下午11:52:43
 * @author：崔旧涛
 */
package science.mrcuijt.spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import science.mrcuijt.spring.entity.HelloWorld;

/**
 * 
 * 
 * 创建时间：2017-10-31 下午11:52:43
 * @author 崔旧涛
 * 
 */
public class SpringMainTest {

	/**
	 * 入口函数值
	 *
	 * 开发时间：2017-10-31 下午11:52:43
	 * @author：崔旧涛
	 * @param：args-传入的参数
	 * @return void
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		HelloWorld obj = (HelloWorld) context.getBean("helloBean");

		System.out.println("Name: " + obj.getName());
		System.out.println("Password: " + obj.getPassword());
		System.out.println("Message: " + obj.getMessage());
		System.out.println();
		System.out.println();
		System.out.println();
		
		obj.setName("mrcuijt");

		obj = null;

		obj = (HelloWorld) context.getBean("helloBean");
		
		System.out.println("Name: " + obj.getName());
		System.out.println("Password: " + obj.getPassword());
		System.out.println("Message: " + obj.getMessage());
	}

}
