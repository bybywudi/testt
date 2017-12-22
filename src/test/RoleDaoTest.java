package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.impl.HeadshotDao;
import dao.impl.PrivilegeDaoImpl;
import dao.impl.RoleDaoImpl;
import domain.admin.Privilege;
import domain.admin.Role;
import domain.web.Upfile;

public class RoleDaoTest {
	/*@Test
	public void testAdd() {
		RoleDaoImpl dao = new RoleDaoImpl();
		
		Role role = new Role();
		role.setId("001");
		role.setName("测试角色1");
		role.setDesciption("测试");
		
		dao.add(role);
	}*/
	
	/*@Test
	public void testFind() {

		
		RoleDaoImpl dao = new RoleDaoImpl();
		Role role = dao.find("001");
		
		System.out.println(role.getId());
	}*/
	
	/*@Test
	public void testDelete() {

		
		HeadshotDao dao = new HeadshotDao();
		Upfile file = dao.findByFilename("1.jpg");
		
		System.out.println(dao.delete(file));
	}*/
	/*
	@Test
	public void testUpdate() {

		RoleDaoImpl dao = new RoleDaoImpl();
		PrivilegeDaoImpl dao1 = new PrivilegeDaoImpl();
		
		List list = new ArrayList();
		Privilege p1 = dao1.find("001");
		System.out.println(p1.getId());
		
		Privilege p2 = dao1.find("002");
		list.add(p1);
		list.add(p2);
		
		Role role = dao.find("001");
		
		dao.updateRolePrivileges(role, list);
		
		System.out.println("ok");
		
		
		
	}*/
	
	@Test
	public void testGetAll() {
		RoleDaoImpl dao = new RoleDaoImpl();
		List list = dao.getAll();
	}
}
