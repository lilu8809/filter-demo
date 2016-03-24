package com.module.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter{

	private String encoding;  
    private Map<String, String> params = new HashMap<String, String>();  
	public void destroy() {
		System.out.println("destroy()---end do the encoding filter!");  
        params=null;  
        encoding=null;  
	}

	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("before encoding " + encoding + " filter！");  
        req.setCharacterEncoding(encoding);  
//        req.setAttribute(, o);
        // resp.setCharacterEncoding(encoding);  
        // resp.setContentType("text/html;charset="+encoding);  
        chain.doFilter(req, rsp);        
        System.out.println("after encoding " + encoding + " filter！");  
        System.err.println("----------------------------------------");  
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("begin do the encoding filter!");  
        encoding = config.getInitParameter("encoding");  
        for (Enumeration e = config.getInitParameterNames(); e  
                .hasMoreElements();) {  
            String name = (String) e.nextElement();  
            String value = config.getInitParameter(name);  
            params.put(name, value);  
        }
	}

}
