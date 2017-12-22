package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import domain.admin.Privilege;
import domain.admin.Resource;
import domain.admin.Role;
import domain.web.User;
import utils.BeanHandler;
import utils.BeanListHandler;
import utils.JdbcUtils;

public class RoleDaoImpl {
	public void add(Role role) {
		String sql = "select *from role where isDelete=? and name=?";
		Object params[] = {"1",role.getName()};
		Role role1 = (Role) JdbcUtils.querry(sql, params, new BeanHandler(Role.class));

		if(role1!=null){

			sql = "update role set description=?,isDelete=? where name=?";
			Object params1[] = {role.getDescription(),"0",role.getName()};
			JdbcUtils.update(sql, params1);
		}else {

			sql = "insert into role(id,name,description,isDelete) value(?,?,?,?)";
			Object params2[] = {role.getId(),role.getName(),role.getDescription(),"0"};
			JdbcUtils.update(sql, params2);
		}

	}
	
	public Role find(String id) {
		String sql = "select *from role where isDelete=? and id=?";
		Object params[] = {"0",id};

		Role role =  (Role) JdbcUtils.querry(sql, params, new BeanHandler(Role.class)); 
		
		if(role.getIsDelete().equals("1")) {
			return null;
		}
		
		Object params1[] = {id,"0"};
		sql = "select p.* from role_privilege rp,privilege p where rp.role_id=? and p.id=rp.privilege_id and p.isDelete=?";
		List list = (List) JdbcUtils.querry(sql, params1, new BeanListHandler(Privilege.class));
		
		role.getPrivileges().addAll(list);
		return role;
	}
	
	public List getAll() {
		String sql = "select *from role where isDelete=?";
		Object params[]= {"0"};
		List<Role> list = (List<Role>) JdbcUtils.querry(sql, params,new BeanListHandler(Role.class));
		
		for(Role role : list) {
			sql = "select p.* from role_privilege rp,privilege p where rp.role_id=? and p.id=rp.privilege_id and p.isDelete=?";
			Object params1[] = {role.getId(),"0"};
			List list1 = (List) JdbcUtils.querry(sql, params1, new BeanListHandler(Privilege.class));
			role.getPrivileges().addAll(list1);
		}
		return list;
	}
	/*
	public List getAll(){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from role";
			st = conn.prepareStatement(sql);

			
			rs = st.executeQuery();
			
			List list = new LinkedList();
			
			while(rs.next()) {
				Role role = new Role();
				role.setId(rs.getString("id"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
				
				list.add(role);
			}
			
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}*/
	
	
	public void updateRolePrivileges(Role role,List<Privilege> privileges) {
		String sql = "delete from role_privilege where role_id=?";
		Object params[] = {role.getId()};
		JdbcUtils.update(sql, params);
		
		for(Privilege p : privileges) {
			sql = "insert into role_privilege(role_id,privilege_id) value(?,?)";
			Object params1[] = {role.getId(),p.getId()};
			JdbcUtils.update(sql, params1);
		}
	}
	
	public void deleteRole(Role role) {
		String sql = "update role set isDelete=? where id=?";
		Object params[] = {"1",role.getId()};
		JdbcUtils.update(sql, params);
	}
}
