/**
 * 
 */
package science.mrcuijt.loh.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.comm.LohConstants;

/**
 * @author Administrator
 *
 */
public class LohListener implements ServletContextListener {

	private static Logger log = LoggerFactory.getLogger(LohListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		log.trace("LohListener Listener ContextInitialized begin ");

		boolean debug = log.isDebugEnabled();

		InputStream is = null;

		try {

			is = LohListener.class.getResourceAsStream("/project.properties");

			Properties prop = new Properties();

			prop.load(is);

			// TODO: 待验证配置文件赋值是否存在问题
			sce.getServletContext().setAttribute("regionLimit", prop.get("project.region.baseRegion.limit"));

			LohConstants.setRegionLimit(Integer.parseInt((String) prop.get("project.region.baseRegion.limit")));

		} catch (Exception e) {

			if (debug) {

				log.debug("Init lohLintener Exception message {}", e.getMessage(), e);

			}

			log.info("Load project.properties fail , set default property");

			sce.getServletContext().setAttribute("regionLimit", -1);

			LohConstants.setRegionLimit(-1);

		} finally {

			if (is != null) {

				try {

					is.close();

				} catch (IOException e) {

					if (debug) {

						log.debug("Init lohLintener close inputStream fail , message {}", e.getMessage(), e);

					}
				}
			}
		}

		log.trace("LohListener Listener ContextInitialized end ");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
