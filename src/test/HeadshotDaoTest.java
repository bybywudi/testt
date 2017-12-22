package test;
import java.util.*;

import org.junit.Test;

import dao.UserDao;
import dao.impl.HeadshotDao;
import dao.impl.UserDaoImpl;
import domain.web.Upfile;
import domain.web.User;

public class HeadshotDaoTest {//此处一开始类名是Junit，一直出错，类名改成这个以后就好了，不清楚原因
	/*@Test
	public void testAdd() {
		Upfile file = new Upfile();
		
		file.setId("1");
		file.setFilename("abc");
		file.setSavepath("c:\\");
		file.setDescription("good");
		file.setUptime(new Date());
		file.setUuidname("11111-11111_abc");
		file.setUserid("0002");
		
		HeadshotDao dao = new HeadshotDao();
		dao.add(file);
	}*/
	
/*	@Test
	public void testFind() {

		
		HeadshotDao dao = new HeadshotDao();
		Upfile file = dao.find("1");
		
		System.out.println(file.getId());
		System.out.println(file.getFilename());
		System.out.println(file.getUuidname());
	}*/
	
	/*@Test
	public void testDelete() {

		
		HeadshotDao dao = new HeadshotDao();
		Upfile file = dao.findByFilename("1.jpg");
		
		System.out.println(dao.delete(file));
	}*/
	
	/*@Test
	public void testUpdate() {

		UserDaoImpl dao = new UserDaoImpl();
		    
		dao.update("0002","email","zhangsan3@sina.com");
		
		
	}*/
}
