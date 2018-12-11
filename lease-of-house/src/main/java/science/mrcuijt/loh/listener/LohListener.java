/**
 * 
 */
package science.mrcuijt.loh.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.loh.comm.LohConstants;
import science.mrcuijt.loh.util.JDBCUtil;

/**
 * @author Administrator
 *
 */
public class LohListener implements ServletContextListener {

	private static Logger log = LoggerFactory.getLogger(LohListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		log.trace("LohListener Listener ContextInitialized begin");

		boolean debug = log.isDebugEnabled();

		InputStream is = null;

		try {

			log.trace("Load project.properties file");

			is = LohListener.class.getResourceAsStream("/project.properties");

			if (is == null) {
				throw new FileNotFoundException("Can't find project.properties");
			}

			log.trace("Load properties begin");

			Properties prop = new Properties();

			prop.load(is);

			log.trace("Load properties end");

			log.trace("Set ServletContent attribute regionLimit = {}", prop.get("project.region.baseRegion.limit"));

			// TODO: 待验证配置文件赋值是否存在问题
			sce.getServletContext().setAttribute("regionLimit", prop.get("project.region.baseRegion.limit"));

			log.trace("Set LohConstants RegionLimit = {}", prop.get("project.region.baseRegion.limit"));

			LohConstants.setRegionLimit(Integer.parseInt((String) prop.get("project.region.baseRegion.limit")));

			log.trace("Set LohConstants ProvinceId = {}", prop.get("project.region.baseRegion.provinceId"));

			LohConstants.setProvinceId(Integer.parseInt((String) prop.get("project.region.baseRegion.provinceId")));

		} catch (FileNotFoundException e) {

			log.error(e.getMessage(), e);

			throw new RuntimeException(e);

		} catch (IOException e) {

			e.printStackTrace();

			if (debug) {

				log.debug("Init lohLintener Exception message {}", e.getMessage(), e);

			}

			log.info("Load project.properties fail , set default property");

			log.trace("Set ServletContent attribute regionLimit = {}", -1);

			sce.getServletContext().setAttribute("regionLimit", -1);

			log.trace("Set LohConstants RegionLimit = {}", -1);
			// 设置
			LohConstants.setRegionLimit(-1);

			log.trace("Set LohConstants ProvinceId = {}", -1);

			LohConstants.setProvinceId(-1);

		} catch (Exception e) {

			log.error("LohListener Listener fail", e);

			throw new RuntimeException(e);

		} finally {

			if (is != null) {

				try {

					is.close();

				} catch (IOException e) {

					if (debug) {

						log.debug("Close inputStream fail , message {}", e.getMessage(), e);

					}
				}
			}
		}

		JDBCUtil.closeAll(null, null, JDBCUtil.getConnection());

		log.trace("LohListener Listener ContextInitialized end ");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.trace("LohListener Listener Destroyed.");
	}

}
