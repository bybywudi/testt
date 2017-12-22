package web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

public class CharacterEncodingFilter implements Filter {
	
	private FilterConfig config;
	private String defaultCharset = "UTF-8";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String charset = this.config.getInitParameter("charset");
		if(charset==null) {
			charset = defaultCharset;
		}
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		request.setCharacterEncoding(charset);
		
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);
		
		chain.doFilter(new MyRequest(request), response);

	}
	
	class MyRequest extends HttpServletRequestWrapper{
		
		
		private HttpServletRequest request;
		public MyRequest(HttpServletRequest request) {
			super(request);//调用默认包装类，防止覆盖200多个接口方法，工作量过大
			this.request = request;
		}
		
		public String getParameter(String name) {
			
			String value = this.request.getParameter(name);
			
			if(!request.getMethod().equalsIgnoreCase("get")){
				return value;
			}
			
			if(value == null) {
				return null;
			}
			
			try {
				return value = new String(value.getBytes("iso8859-1"),request.getCharacterEncoding());//如果是get方式，则将得到的iso8859-1编码的数据转换成服务器的编码数据
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
		
		
		}
	}
}
	
	/*包装设计模式
	 * 1、写一个类，实现与被增强对象相同的接口
	 * 2、定义一个变量，记住被增强对象
	 * 3、定义一个构造方法，接收被增强对象
	 * 4、覆盖向增强的方法
	 * 5、对于不想增强的方法，直接调用被增强对象的方法
	 *
	 *
	 *	
	 * */
	/*
	class MyRequest implements HttpServletRequest{//1、写一个类，实现与被增强对象相同的接口
		private HttpServletRequest request;//2、定义一个变量，记住被增强对象
		public MyRequest(HttpServletRequest request) {//3、定义一个构造方法，接收被增强对象
			this.request = request;
		}
		
		@Override
		public String getParameter(String name) {
			
			String value = request.getParameter(name);
			
			if(!request.getMethod().equalsIgnoreCase("get")){
				return value;
			}
			
			if(value == null) {
				return null;
			}
			
			try {
				return value = new String(value.getBytes("iso8859-1"),request.getCharacterEncoding());//如果是get方式，则将得到的iso8859-1编码的数据转换成服务器的编码数据
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
		}
		
	}*/



