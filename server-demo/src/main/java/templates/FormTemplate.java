/**
 * 
 */
package templates;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author Administrator
 *
 */
public class FormTemplate {

	private static Integer port = null;
	private static String host = null;
	public static Map<String, Object> root = new HashMap<String, Object>();
	public static Configuration cfg = null;
	
	static {
		// 设置端口
		Properties prop = new Properties();
		try {
			prop.load(Class.forName("test.ThreadService").getResourceAsStream("/res.properties"));
			port = Integer.parseInt(prop.getProperty("SERVER.PORT"));
			host = prop.getProperty("SERVER.HOST");
			root.put("port", port + "");
			root.put("host", host);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create your Configuration instance, and specify if up to what FreeMarker
		// version (here 2.3.22) do you want to apply the fixes that are not 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		cfg = new Configuration(Configuration.VERSION_2_3_22);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		try {
			
			cfg.setDirectoryForTemplateLoading(new File("target/classes/templates"));
			
			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			cfg.setDefaultEncoding("UTF-8");

			// Sets how errors will appear.
			// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
