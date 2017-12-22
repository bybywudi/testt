package web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.admin.Privilege;
import domain.admin.Resource;
import domain.web.User;
import service.impl.PrivilegeServiceImpl;

/**
 * Servlet Filter implementation class PermissionFilter
 */
@WebFilter("/PermissionFilter")
public class PrivilegeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PrivilegeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//3、得到用户想访问的资源
				String uri = request.getRequestURI();

				//4、得到需要的权限
				PrivilegeServiceImpl service = new PrivilegeServiceImpl();
				Resource r = service.findResource(uri);
				if(r==null) {

					chain.doFilter(request, response);
					return;
					
				}
					Privilege required_Privilege = r.getPrivilege();
				 
		//1、检查用户有没有登陆
		User user = (User) request.getSession().getAttribute("user");
		//2、如果没登录，让用户登录
		if(user == null) {
			request.setAttribute("message", "请先登录");
			request.setAttribute("pleaselogin", "true");
			request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
			return;
		}
		
		
		//5、判断用户是否有相应权限 
		List userallprivileges = service.getUserAllPrivilege(user.getId());
		if(!userallprivileges.contains(required_Privilege)) {
			//6、没有权限，则提示用户权限不足。联系管理
			request.setAttribute("message", "权限不足");
			request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
