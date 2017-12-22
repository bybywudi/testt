package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.web.User;
import exception.EmailExitException;
import exception.NicknameExitException;
import exception.UserExitException;
import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import web.form.RegisterForm;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		//1、判断数据是否符合要求
		RegisterForm form = WebUtils.request2Bean(request, RegisterForm.class);
		boolean b = form.validate();
		//2、校验没通过，则返回表单回显失败信息
		if(!b) {
			request.setAttribute("form", form);
			//request.setAttribute("test", "!!!!!!!!!!!!!!!!!!!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}
		//3、校验成功，处理注册请求
		User user = new User();
		WebUtils.copyBean(form, user);
		user.setId(WebUtils.generateID());
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		
		try {
			service.register(user);
			//6、没有抛出异常，则完成注册
			request.setAttribute("successregist", "注册成功");
			request.getRequestDispatcher("/WEB-INF/jsp/successregist.jsp").forward(request, response);
			return;
		} catch (UserExitException e) {
			//4、注册的用户已存在，则会抛出该异常
			//request.setAttribute("message", "该用户名已存在");
			form.getErrors().put("username", "该用户名已存在");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}catch (NicknameExitException e) {
			//7、昵称被注册
			//request.setAttribute("message", "该用户名已存在");
			form.getErrors().put("nickname", "昵称已被注册");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}catch (EmailExitException e) {
			//7、昵称被注册
			//request.setAttribute("message", "该用户名已存在");
			form.getErrors().put("email", "邮箱已被注册");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			return;
		}
		catch(Exception e) {
			//5、其他问题，跳转到全局消息显示页面
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
			request.setAttribute("message", "服务器出现未知错误");
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
