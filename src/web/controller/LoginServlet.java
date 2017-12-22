package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.web.Upfile;
import domain.web.User;
import exception.UserExitException;
import service.impl.BusinessServiceImpl;
import service.impl.UpfileServiceImpl;
import utils.WebUtils;
import web.form.LoginForm;
import web.form.RegisterForm;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet") 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int expirestime = 1*60;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
	/*	//1、判断数据是否符合要求
		LoginForm form = WebUtils.request2Bean(request, LoginForm.class);
		boolean b = form.validate();
		//2、校验没通过，则返回表单回显失败信息
		if(!b) {
			request.setAttribute("form", form);
			//request.setAttribute("test", "!!!!!!!!!!!!!!!!!!!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		//3、校验成功，处理注册请求
		User user = new User();
		WebUtils.copyBean(form, user);
		//user.setId(WebUtils.generateID());
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		
		try {
			User user2 = service.login(user.getUsername(),user.getPassword());
			if(user2 == null) {
				form.getErrors().put("username", "用户不存在");
				request.setAttribute("form", form);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			}
			
			
			//6、没有抛出异常，则完成注册
			request.setAttribute("successregist", "登陆成功");
			request.getRequestDispatcher("/WEB-INF/jsp/successregist.jsp").forward(request, response);
			return;
		} catch(Exception e) {
			//5、其他问题，跳转到全局消息显示页面
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
			request.setAttribute("message", "服务器出现未知错误");
			return;
		}*/
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		UpfileServiceImpl service2 = new UpfileServiceImpl();
		//System.out.println("true");
		
		User user = service.login(username, password);
		
		if(user!=null) {
			Upfile headshot = service2.findUpfileByUserid(user.getId());
			if(headshot != null) {
				request.getSession().setAttribute("headshot", headshot);
			}
			
			request.getSession().setAttribute("user", user);
			
			Cookie cookie = WebUtils.buildCookie(user,expirestime,request);
			
			response.addCookie(cookie);
			

			
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		request.setAttribute("message", "用户名或密码错误");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
