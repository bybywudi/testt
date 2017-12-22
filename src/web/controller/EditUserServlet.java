package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.web.User;
import exception.EmailExitException;
import exception.UserExitException;
import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import web.form.EditUserForm;
import web.form.RegisterForm;

/**
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		EditUserForm form = WebUtils.request2Bean(request,EditUserForm.class);
		boolean b = form.validate();
		if(!b) {
			request.setAttribute("form", form);
			//request.setAttribute("test", "!!!!!!!!!!!!!!!!!!!!!!!!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/edituser.jsp").forward(request, response);
			return; 
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newemail = request.getParameter("newemail");
		String newnickname = request.getParameter("newnickname");
		String newbirthday = request.getParameter("newbirthday");
		int result;
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		User user = service.login(username, password);
		
		if(user == null) {
			request.setAttribute("message", "密码错误！");
			request.getRequestDispatcher("/WEB-INF/jsp/edituser.jsp").forward(request, response);
			return;
		}
		
	if(!((newemail==null || newemail.trim().equals("")) && (newbirthday==null || newbirthday.trim().equals("")) && (newnickname==null || newnickname.trim().equals("")))) {
			if(!(newemail==null || newemail.trim().equals(""))) {
				
					result = service.updateUser(user, "email", newemail);
					if(result == 2) {
					
						form.getErrors().put("email", "该邮箱已被注册");
	
					}
					if(result == 3) {
						
						form.getErrors().put("email", "邮箱修改成功");
	
					}
			
			}
			
			if(!(newbirthday==null || newbirthday.trim().equals(""))) {
				
				result = service.updateUser(user, "birthday", newbirthday);
	
				if(result == 3) {
					
					form.getErrors().put("birthday", "生日修改成功");
	
				}
		
		}
			
			if(!(newnickname==null || newnickname.trim().equals(""))) {
				
				result = service.updateUser(user, "nickname", newnickname);
				if(result == 1) {
				
					form.getErrors().put("nickname", "该昵称已存在");
	
				}
				if(result == 3) {
					
					form.getErrors().put("nickname", "昵称修改成功");
	
				}
		
		}
			user = service.login(username, password);
			request.getSession().setAttribute("user", user);
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/edituser.jsp").forward(request, response);
			return;
	}
	
	request.setAttribute("message", "表单不能全为空");
	request.getRequestDispatcher("/WEB-INF/jsp/edituser.jsp").forward(request, response);
	return;

}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
