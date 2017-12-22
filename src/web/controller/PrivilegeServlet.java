package web.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
import utils.WebUtils;

/**
 * 处理所有权限相关的请求
 */
@WebServlet("/PrivilegeServlet")
public class PrivilegeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PrivilegeServiceImpl service = new PrivilegeServiceImpl();
	BusinessServiceImpl service1 = new BusinessServiceImpl();

    public PrivilegeServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String method = request.getParameter("method");
		//权限管理
		if("getAllPrivilege".equals(method)) {
			getAllPrivilege(request,response);
		}
		
		if("addPrivilege".equals(method)) {
			addPrivilege(request,response);
		}
		
		if("addPrivilegeUI".equals(method)) {
			addPrivilegeUI(request,response);
		}
		
		//资源管理
		
		if("getAllResource".equals(method)) {
			getAllResource(request,response);
		}
		
		if("addResourceUI".equals(method)) {
			addResourceUI(request,response);
		}
		
		if("addResource".equals(method)) {
			addResource(request,response);
		}
		
		if("updateResourcePrivilegeUI".equals(method)) {
			updateResourcePrivilegeUI(request,response);
		}
		
		if("updateResourcePrivilege".equals(method)) {
			updateResourcePrivilege(request,response);
		}
		//角色管理
		if("getAllRole".equals(method)) {
			getAllRole(request,response);
		}
		
		if("addRoleUI".equals(method)) {
			addRoleUI(request,response);
		}
		
		if("addRole".equals(method)) {
			addRole(request,response);
		}
		
		if("updateRolePrivilegeUI".equals(method)) {
			updateRolePrivilegeUI(request,response);
		}
		
		if("updateRolePrivilege".equals(method)) {
			updateRolePrivilege(request,response);
		}
		
		//用户管理
		if("updateUserRoleUI".equals(method)) {
			updateUserRoleUI(request,response);
		}
		
		if("updateUserRole".equals(method)) {
			updateUserRole(request,response);
		}

	}


	//权限管理

	private void addPrivilegeUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("addPrivilegeUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		
	}


	private void addPrivilege(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Privilege p =WebUtils.request2Bean(request, Privilege.class);
			p.setId(UUID.randomUUID().toString());

			service.addPrivilege(p);

			
			request.setAttribute("message", "添加成功");
			List list = service.getAllPrivilege();
			request.setAttribute("privilegelist", list);
			request.setAttribute("allprivilege", "true");
			request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		}catch(Exception e) {
			request.setAttribute("message", "服务器出错了");
		}
		
	}

	private void getAllPrivilege(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List list = service.getAllPrivilege();
		request.setAttribute("privilegelist", list);
		request.setAttribute("allprivilege", "true");
		String message = request.getParameter("message");
		if(message!=null) {
			request.setAttribute("message", message);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		
	}
	//资源管理
	private void getAllResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list = service.getAllResource();
		request.setAttribute("resourcelist", list);
		request.setAttribute("allresource", "true");
		String message = request.getParameter("message");
		if(message!=null) {
			request.setAttribute("message", message);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		
	}
	
	private void addResourceUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("addResourceUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}

	private void addResource(HttpServletRequest request, HttpServletResponse response) {
		try {	
			Resource r = WebUtils.request2Bean(request, Resource.class);
			r.setId(UUID.randomUUID().toString());
			service.addResource(r);
			
			request.setAttribute("message", "添加成功");
			List list = service.getAllResource();
			request.setAttribute("resourcelist", list);
			request.setAttribute("allresource", "true");
			request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		}catch(Exception e) {
			request.setAttribute("message", "服务器出错了");
		}
		
	}
	
	private void updateResourcePrivilegeUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resourceid = request.getParameter("id");
		Resource r = service.findResourceById(resourceid);
		
		List list = service.getAllPrivilege();
		request.setAttribute("privilegelist", list);
		request.setAttribute("resource", r);
		
		request.setAttribute("updateResourcePrivilegeUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}
	
	private void updateResourcePrivilege(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resourceid = request.getParameter("rid");
		String privilegeid = request.getParameter("pid");
		service.updateResourcePrivilege(resourceid, privilegeid);
		
		Resource r = service.findResourceById(resourceid);
		List list = service.getAllPrivilege();

		request.setAttribute("privilegelist", list);
		request.setAttribute("resource", r);
		
		request.setAttribute("updateResourcePrivilegeUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}
	//角色管理的方法
	private void getAllRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List list = service.getAllRole();
		request.setAttribute("rolelist", list);
		request.setAttribute("allrole", "true");
		String message = request.getParameter("message");
		if(message!=null) {
			request.setAttribute("message", message);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		
	}
	
	private void addRoleUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List list = service.getAllPrivilege();
		request.setAttribute("privilegelist", list);
		
		request.setAttribute("addRoleUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}
	
	private void addRole(HttpServletRequest request, HttpServletResponse response) {
		try {	
			Role role = WebUtils.request2Bean(request, Role.class);
			String[] privilegeids = request.getParameterValues("pid");
			role.setId(UUID.randomUUID().toString());
			service.addRole(role);
			
			service.updateRolePrivilege(role.getId(), privilegeids);
			                 
			request.setAttribute("message", "添加成功");
			this.getAllRole(request, response);
			//List list = service.getAllRole();
			//request.setAttribute("rolelist", list);
			//request.setAttribute("allrole", "true");
			//request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		}catch(Exception e) {
			request.setAttribute("message", "服务器出错了");
		}
		
	}
	
	private void updateRolePrivilegeUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String roleid = request.getParameter("id");
		Role role= service.findRole(roleid);
		
		List list = service.getAllPrivilege();
		request.setAttribute("privilegelist", list);
		request.setAttribute("role", role);
		
		request.setAttribute("updateRolePrivilegeUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}
	
	private void updateRolePrivilege(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String roleid = request.getParameter("roleid");
		String[] privilegeids = request.getParameterValues("pid");
		
		service.updateRolePrivilege(roleid, privilegeids);
		
		Role role = service.findRole(roleid);
		List list = service.getAllPrivilege();

		request.setAttribute("privilegelist", list);
		request.setAttribute("role", role);
		
		request.setAttribute("updateRolePrivilegeUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}

	//角色管理

	private void updateUserRoleUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("id");
		User user = service1.findById(userid);
		
		request.setAttribute("user", user);
		
		List rlist = service.getAllRole();
		request.setAttribute("rolelist", rlist);
		
		List plist = service.getUserAllPrivilege(userid);
		request.setAttribute("userplist", plist);
		
		request.setAttribute("updateUserRoleUI", "true");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
	}
	
	
	private void updateUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] roleids = request.getParameterValues("roleid");
		String userid = request.getParameter("userid");
		
		service.updateUserRole(userid, roleids);
		
		User user = service1.findById(userid);
		request.setAttribute("user", user);
		
		List rlist = service.getAllRole();
		request.setAttribute("rolelist", rlist);
		
		List plist = service.getUserAllPrivilege(userid);
		request.setAttribute("userplist", plist);
		
		request.setAttribute("updateUserRoleUI", "true");
		//request.setAttribute("message", "修改成功");
		request.getRequestDispatcher("/WEB-INF/jsp/manager.jsp").forward(request, response);
		
				

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
