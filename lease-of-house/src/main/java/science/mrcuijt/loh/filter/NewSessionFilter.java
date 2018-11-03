/**
 * 
 */
package science.mrcuijt.loh.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class NewSessionFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(NewSessionFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession oldSession = ((HttpServletRequest)request).getSession();
		// get the content of old session.
		Enumeration<String> attributeNames = oldSession.getAttributeNames();
		Map<String, Object> attributeMap = new HashMap<String, Object>();
		while (attributeNames != null && attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			attributeMap.put(attributeName, oldSession.getAttribute(attributeName));
		}
		oldSession.invalidate();
		HttpSession newSession = ((HttpServletRequest)request).getSession(true);
		// put the content into the new session.
		for (String key : attributeMap.keySet()) {
			newSession.setAttribute(key, attributeMap.get(key));
		}
		
        chain.doFilter(request, response);
	}

	private void createNewSession(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (httpRequest.getSession() != null) {
            	LOG.debug("old Session:" + httpRequest.getSession().getId());
                HttpSession session = httpRequest.getSession();
                HashMap<String, Object> old = new HashMap<String, Object>();
                Enumeration<String> keys = session.getAttributeNames();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    old.put(key, session.getAttribute(key));
                    session.removeAttribute(key);
                }
                
                if (!httpRequest.getSession().isNew()){
                    session.invalidate();
                    session = httpRequest.getSession(true);
                    LOG.debug("new Session:" + session.getId());
                }
 
                for (Iterator<Entry<String, Object>> it = old.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                    session.setAttribute((String) entry.getKey(), entry.getValue());
                }
            }
        }
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

}
