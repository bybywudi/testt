package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.admin.Privilege;
import domain.admin.Resource;
import domain.admin.Role;
import domain.web.User;
import service.impl.BusinessServiceImpl;
import service.impl.PrivilegeServiceImpl;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrivilegeServiceImpl service = new PrivilegeServiceImpl();
	BusinessServiceImpl service1 = new BusinessServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String method = request.getParameter("method");
		//权限管理
		if("deleteUser".equals(method)) {
			deleteUser(request,response);
		}
		
		if("deleteRole".equals(method)) {
			deleteRole(request,response);
		}
		
		if("deleteResource".equals(method)) {
			deleteResource(request,response);
		}
		
		if("deletePrivilege".equals(method)) {
			deletePrivilege(request,response);
		}
		
	}

	private void deletePrivilege(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("id");
		Privilege p = service.findPrivilege(pid);
		
		service.deletePrivilege(p);

		response.sendRedirect(request.getContextPath()+"/PrivilegeServlet?method=getAllPrivilege&message=successfully deleted");
		//request.getRequestDispatcher("/PrivilegeServlet").forward(request, response);
		
	}

	private void deleteResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rid = request.getParameter("id");
		Resource r = service.findResourceById(rid);
		
		service.deleteResouce(r);
		
		response.sendRedirect(request.getContextPath()+"/PrivilegeServlet?method=getAllResource&message=successfully deleted");
		
	}

	private void deleteRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roleid = request.getParameter("id");
		Role role = service.findRole(roleid);
		
		service.deleteRole(role);
		response.sendRedirect(request.getContextPath()+"/PrivilegeServlet?method=getAllRole&message=successfully deleted");
		
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("id");
		User user = service1.findById(userid);
		
		service1.deleteUser(user);
		request.setAttribute("message", "删除成功");
		request.getRequestDispatcher("/WEB-INF/jsp/listuser.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
