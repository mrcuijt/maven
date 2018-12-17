/**
 * 
 */
package science.mrcuijt.loh.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import science.mrcuijt.loh.util.XSSUtil;

/**
 * @author Administrator
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);

		if (values == null) {

			return null;

		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {

			encodedValues[i] = XSSUtil.cleanXSS(values[i]);

		}

		return encodedValues;

	}

	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);

		if (value == null) {

			return null;

		}

		return XSSUtil.cleanXSS(value);

	}

	public String getHeader(String name) {

		String value = super.getHeader(name);

		if (value == null)

			return null;

		return XSSUtil.cleanXSS(value);

	}

}
