package test;
import java.util.*;

import org.junit.Test;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.web.User;

public class UserDaoTest {//此处一开始类名是Junit，一直出错，类名改成这个以后就好了，不清楚原因
/*
	@Test
	public void testAdd() {
		User user = new User();
		user.setBirthday(new Date());
		user.setEmail("bbb@sina.com");
		user.setId("0002");
		user.setNickname("张三");
		user.setUsername("bbb");
		user.setPassword("123");
		
		
		UserDao dao = new UserDaoImpl();
		dao.add(user);
	}
*/
	/*@Test
	public void testFind() {
		UserDaoImpl dao = new UserDaoImpl();
		dao.find("aaa", "123");
		
	}*/
	/*
	@Test
	public void testFind2() {
		UserDao dao = new UserDaoImpl();
		dao.find("bbb");
	}
	*/  
	@Test
	public void testUpdate() {

		UserDaoImpl dao = new UserDaoImpl();
		    
		dao.update("0002","email","zhangsan3@sina.com");
		
		
	}
}
