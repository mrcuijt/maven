/**
 * 
 */
package science.mrcuijt.loh.util;

/**
 * @author Administrator
 *
 */
public class XSSUtil {

	public static String cleanXSS(String value) {

		// You'll need to remove the spaces from the html entities below

		value = value.replaceAll("<", "").replaceAll(">", "");

		value = value.replaceAll("\\(", "").replaceAll("\\)", "");

		value = value.replaceAll("'", "");

		value = value.replaceAll("eval\\((.*)\\)", "");

		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

		value = value.replaceAll("script", "");

		return value;

	}

}
