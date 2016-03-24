package com.module.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cache.RedisCacheUtil;
import com.common.Constant;

public class LoginFilter implements Filter{

	private final String LoginTimeControl = ".l.t.c";
	
	FilterConfig config; 
	public void destroy() {
		this.config = null; 
	}

	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
        System.out.println("before the login filter!");  
        // 将请求转换成HttpServletRequest 请求  
        HttpServletRequest hreq = (HttpServletRequest) req;  
        HttpServletResponse hrsp = (HttpServletResponse) rsp;  
        
        RedisCacheUtil rc = new RedisCacheUtil();
        
        String username = req.getParameter("username");
		int logintimes = 0;
		String existtimes = rc.get(username+this.LoginTimeControl);
		if(existtimes==null){
			System.out.println("第一次登录");
			logintimes = 1;
			rc.set(username+this.LoginTimeControl, String.valueOf(logintimes));
			rc.expire(username+this.LoginTimeControl, 1*60);
			chain.doFilter(req, hrsp);
		}else{
			logintimes = Integer.valueOf(existtimes);
			System.out.println("第"+logintimes+"次登录");
			if(logintimes<Integer.valueOf(Constant.MAX_LOGIN_TIMES)){//还能继续登录
				logintimes = logintimes + 1;
				rc.set(username+this.LoginTimeControl, String.valueOf(logintimes));
				chain.doFilter(req, rsp);
			}else{//已经达到最大登录次数，限制登录
				System.out.println("一天内登录过频繁！");
				hrsp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
//        // 记录日志  
//        System.out.println("Login Filter已经截获到用户的请求的地址:"+hreq.getServletPath() );  
//        //context.log("Filter已经截获到用户的请求的地址: " + hreq.getServletPath());  
//        try {  
//            // Filter 只是链式处理，请求依然转发到目的地址。  
//            chain.doFilter(req, rsp);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
        System.out.println("after the login filter!");  
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("begin do the login filter!");  
        this.config = config;  
	}
	

}
