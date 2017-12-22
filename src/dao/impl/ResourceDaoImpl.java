package dao.impl;

import java.util.List;

import domain.admin.Privilege;
import domain.admin.Resource;
import domain.admin.Role;
import utils.BeanHandler;
import utils.BeanListHandler;
import utils.JdbcUtils;

public class ResourceDaoImpl {
	public void add(Resource r) {
		String sql = "select *from resource where isDelete=? and uri=?";
		Object params[] = {"1",r.getUri()};
		Resource r1 = (Resource) JdbcUtils.querry(sql, params, new BeanHandler(Resource.class));

		if(r1!=null){

			sql = "update resource set description=?,isDelete=? where uri=?";
			Object params1[] = {r.getDescription(),"0",r.getUri()};
			JdbcUtils.update(sql, params1);
		}else {
			sql = "insert into resource(id,uri,description,isDelete) value(?,?,?,?)";
			Object params2[] = {r.getId(),r.getUri(),r.getDescription(),"0"};
			JdbcUtils.update(sql, params2);
		}

	}
	
	public Resource find(String uri) {
		String sql = "select *from resource where isDelete=? and uri=?";
		Object params[] = {"0",uri};
		Resource r = (Resource) JdbcUtils.querry(sql, params, new BeanHandler(Resource.class));
		
		if(r == null) {
			return null;
		}
		
		Object params1[] = {uri,"0"};
		sql = "select p.* from resource r,privilege p where r.uri=? and p.id=r.privilege_id and p.isDelete=?";
		Privilege p = (Privilege) JdbcUtils.querry(sql, params1, new BeanHandler(Privilege.class));
		
		
		r.setPrivilege(p);
		return r;
	}
	
	public Resource findById(String id) {
		String sql = "select *from resource where isDelete=? and id=?";
		Object params[] = {"0",id};
		Resource r = (Resource) JdbcUtils.querry(sql, params, new BeanHandler(Resource.class));
		

		if(r == null) {
			return null;
		}
		
		Object params1[] = {id,"0"};
		sql = "select p.* from resource r,privilege p where r.id=? and p.id=r.privilege_id and p.isDelete=?";
		Privilege p = (Privilege) JdbcUtils.querry(sql, params1, new BeanHandler(Privilege.class));
		r.setPrivilege(p);
		return r;
	}
	
	public List getAll() {
		String sql = "select *from resource where isDelete=?";
		Object params[] = {"0"};
		List<Resource> list = (List<Resource>) JdbcUtils.querry(sql, params, new BeanListHandler(Resource.class));
		
		for(Resource r : list) {
			sql = "select p.* from resource r,privilege p where r.id=? and p.id=r.privilege_id and p.isDelete=?";
			Object params1[] = {r.getId(),"0"};
			Privilege p = (Privilege) JdbcUtils.querry(sql, params1, new BeanHandler(Privilege.class));
			r.setPrivilege(p);
		}
		return list;
	}
	
	public void updatePrivilege(Resource r,Privilege p) {
		String sql = "update resource set privilege_id=? where id=?";
		Object params[]= {p.getId(),r.getId()};
		JdbcUtils.update(sql, params);
	}
	
	public void deleteResource(Resource r) {
		String sql = "update resource set isDelete=? where id=?";
		Object params[] = {"1",r.getId()};
		JdbcUtils.update(sql, params);
	}
	
}
