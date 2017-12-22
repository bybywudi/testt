package service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import dao.impl.PrivilegeDaoImpl;
import dao.impl.ResourceDaoImpl;
import dao.impl.RoleDaoImpl;
import dao.impl.UserDaoImpl;
import domain.admin.Privilege;
import domain.admin.Resource;
import domain.admin.Role;
import domain.web.User;

public class PrivilegeServiceImpl {
	private ResourceDaoImpl rdao = new ResourceDaoImpl();
	private PrivilegeDaoImpl pdao = new PrivilegeDaoImpl();
	private RoleDaoImpl roledao = new RoleDaoImpl();
	private UserDaoImpl udao = new UserDaoImpl();
	//资源相关的服务
	public void addResource(Resource r) {
		rdao.add(r);
	}
	
	public Resource findResource(String uri) {
		return rdao.find(uri);
	}
	
	public Resource findResourceById(String id) {
		return rdao.findById(id);
	}
	
	public List getAllResource() {
		return rdao.getAll();
	}
	
	public void updateResourcePrivilege(String resourceid,String privilegeid) {
		Resource r = rdao.findById(resourceid);
		Privilege p = pdao.find(privilegeid);
		rdao.updatePrivilege(r, p);
	}
	//权限相关的服务
	public void addPrivilege(Privilege p) {
		pdao.add(p);
	}
	
	public Privilege findPrivilege(String id) {
		return pdao.find(id);
	}
	
	public List getAllPrivilege() {
		return pdao.getAll();
	}
	//角色相关的服务
	public void addRole(Role role) {
		roledao.add(role);
	}
	
	public Role findRole(String id) {
		return roledao.find(id);
	}
	
	public List getAllRole() {
		return roledao.getAll();
	}
	
	public void updateRolePrivilege(String roleid,String[] privilege_ids) {
		
		
		Role role = roledao.find(roleid);
		List list = new ArrayList();
		for(int i=0;privilege_ids!=null && i<privilege_ids.length;i++) {
			list.add(pdao.find(privilege_ids[i]));
		}
		
		roledao.updateRolePrivileges(role, list);
	}
	//用户相关的服务
	public void updateUserRole(String userid,String[] roleids) {
		
		User user = udao.findById(userid);
		List list = new ArrayList();
		for(int i=0;roleids!=null && i<roleids.length;i++) {
			list.add(roledao.find(roleids[i]));
		}
		
		udao.updateUserRole(user, list);
	}
	
	public List getUserAllPrivilege(String userid) {
		
		List<Privilege> allPrivilege = new ArrayList();
		
		User user = udao.findById(userid);
		Set<Role> roles = user.getRoles();
		for(Role r : roles) {
			r = roledao.find(r.getId());
			Set privileges = r.getPrivileges();
			allPrivilege.addAll(privileges);
		}
		
		
		//去掉各个角色之间重合的权限
		for(int i=0;i<allPrivilege.size()-1;i++) {
			for(int j=i+1;j<allPrivilege.size();j++) {
				if(allPrivilege.get(i).getId().toString().equals(allPrivilege.get(j).getId().toString())) {
					allPrivilege.remove(j);
				}
			}
		}
		
		return allPrivilege;
		
	}
	
	public void deleteRole(Role role) {
		roledao.deleteRole(role);
	}
	
	public void deleteResouce(Resource r) {
		rdao.deleteResource(r);
	}
	
	public void deletePrivilege(Privilege p) {
		pdao.deletePrivilege(p);
	}
}
