package test;

import java.util.Date;

import org.junit.Test;

import domain.web.User;
import exception.EmailExitException;
import exception.NicknameExitException;
import exception.UserExitException;
import service.impl.BusinessServiceImpl;

public class BusinessServiceTest {
	@Test
	public void testRegister() throws EmailExitException {
		
		User user = new User();
		user.setBirthday(new Date());
		user.setEmail("bbb@sina.com");
		user.setId("0004");
		user.setNickname("张三");
		user.setUsername("s");
		user.setPassword("123");
		
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		
		try {
			service.register(user);
			System.out.println("注册成功");
		} catch (UserExitException e) {
			System.out.println("用户已存在");
		} catch (NicknameExitException e) {
			System.out.println("该昵称已存在");
		}
		
	}
	/*@Test
	public void testLogin() {
	
	BusinessServiceImpl b = new BusinessServiceImpl();
	
	b.login("aaa", "123");

}*/
	
	
}
