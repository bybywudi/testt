package dao.impl;

import java.util.List;

import domain.admin.Privilege;
import domain.admin.Role;
import domain.web.Upfile;
import utils.BeanHandler;
import utils.BeanListHandler;
import utils.JdbcUtils;

public class PrivilegeDaoImpl {
	public void add(Privilege p) {
		String sql = "select *from privilege where isDelete=? and name=?";
		Object params[] = {"1",p.getName()};
		Privilege p1 = (Privilege) JdbcUtils.querry(sql, params, new BeanHandler(Privilege.class));

		if(p1!=null){

			sql = "update privilege set description=?,isDelete=? where name=?";
			Object params1[] = {p.getDescription(),"0",p.getName()};
			JdbcUtils.update(sql, params1);
		}else {

			sql = "insert into privilege(id,name,description,isDelete) value(?,?,?,?)";
			Object params2[] = {p.getId(),p.getName(),p.getDescription(),"0"};
			JdbcUtils.update(sql, params2);
		}
	}
	
	public Privilege find(String id) {
		String sql = "select *from privilege where isDelete=? and id=?";
		Object params[] = {"0",id};
		return (Privilege) JdbcUtils.querry(sql, params, new BeanHandler(Privilege.class)); 
	}
	
	public List<Privilege> getAll(){
		String sql = "select *from privilege where isDelete=?";
		Object params[] = {"0"};
		return (List<Privilege>) JdbcUtils.querry(sql,params,new BeanListHandler(Privilege.class));
	}
	
	public void deletePrivilege(Privilege p) {
		String sql = "update privilege set isDelete=? where id=?";
		Object params[] = {"1",p.getId()};
		JdbcUtils.update(sql, params);
	}
}
