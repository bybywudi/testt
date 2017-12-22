package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import dao.impl.PrivilegeDaoImpl;
import domain.admin.Privilege;

public class PrivilegeDaoTest {
	/*@Test
	public void testAdd() {
		PrivilegeDaoImpl dao = new PrivilegeDaoImpl();
		
		Privilege p = new Privilege();
		p.setId("002");
		p.setName("测试权限2");
		p.setDescription("测试权限2");
		
		dao.add(p);
	}*/
	
	/*@Test
	public void testFind() {

		
		PrivilegeDaoImpl dao = new PrivilegeDaoImpl();
		Privilege p = dao.find("001");
		System.out.println(p.getId());
	}*/
	
	@Test
	public void testGetAll() {

		
		PrivilegeDaoImpl dao = new PrivilegeDaoImpl();
		List list = dao.getAll();
		
		System.out.println("ok");
	}
}
