/**
 * 
 */
package science.mrcuijt.servlet.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Administrator
 *
 */
public class GetRequestWrapper extends HttpServletRequestWrapper {

	public GetRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);

		if (values == null) {

			return null;

		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {

			if (values[i] != null && values[i].trim().length() > 0) {
				try {
					encodedValues[i] = new String(values[i].getBytes("iso-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				encodedValues[i] = values[i];
			}

		}

		return encodedValues;

	}

	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);

		if (value == null || value.trim().length() == 0) {

			return null;

		}

		try {
			value = new String(value.getBytes("iso-8859-1"), "UTF-8");
			return value;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
