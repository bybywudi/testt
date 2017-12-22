package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.web.User;
import service.impl.BusinessServiceImpl;
import utils.ServiceUtils;

public class AutoLoginFilter implements Filter {
	 
	private FilterConfig config;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//1、判断用户有没有登陆，如果没有，则自动登陆
		User user = (User) request.getSession().getAttribute("user");
		
		if(user != null) {
			chain.doFilter(request, response);
			return;
		}
		
		//2、没登陆，再执行自动登陆逻辑
		
		//先看用户有没有带自动登陆的cookie，检查格式
		Cookie autoLogin = null;
		Cookie cookies[] = request.getCookies();
		for(int i=0;cookies!=null && i<cookies.length;i++) {
			if(cookies[i].getName().equals("autoLogin")) {
				autoLogin = cookies[i];
			}
		}
		
		if(autoLogin == null) {
			chain.doFilter(request, response);
			return;
		}
		
		//用户带了cookie，检查有没有过期
		//System.out.println(autoLogin.getValue());
		String values[] = autoLogin.getValue().split("\\:");
		if(values.length != 3) {
			chain.doFilter(request, response);
			return;
		}
		
		long expirestime = Long.parseLong(values[1]);//因为设计的cookie格式中，第二个为存在时间的值，所以判断第二个冒号前的值
		//System.out.println(expirestime);
		if(System.currentTimeMillis() > expirestime) {
			chain.doFilter(request, response);
			return;
		}
		
		//如果时间也有效，则检查剩下的用户名和md5编码
		
		String username = values[0];
		String md5_Client = values[2];
		//System.out.println("client:" + md5_Client);
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		
		user = service.findByName(username);
		if(user == null) {
			chain.doFilter(request, response);
			return;
		}
		
		String md5_Server = ServiceUtils.md5(user.getUsername() + ":" + expirestime + ":" + user.getPassword());
		//System.out.println("server:" + md5_Server);
		//System.out.println(md5_Server.equals(md5_Client));
		if(!md5_Server.equals(md5_Client)) {
			chain.doFilter(request, response);
			return;
		}
		
		request.getSession().setAttribute("user", user);
		//System.out.println("ok");
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;

	}

}
