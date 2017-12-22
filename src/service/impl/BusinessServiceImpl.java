package service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.web.PageBean;
import domain.web.QuerryInfo;
import domain.web.QuerryResult;
import domain.web.User;
import exception.EmailExitException;
import exception.NicknameExitException;
import exception.PasswordErroException;
import exception.UserExitException;
import utils.ServiceUtils;

//提供对WEB层的所有业务
public class BusinessServiceImpl {
	
	private UserDao dao = new UserDaoImpl();
	
	public void register(User user) throws UserExitException, NicknameExitException, EmailExitException {
		
		boolean b = dao.find(user.getUsername());
		if(b) {
			throw new UserExitException();//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
		}
		
		boolean b1 = dao.findNickname(user.getNickname());
		if(b1) {
			throw new NicknameExitException();//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
		}
		
		boolean b2 = dao.findEmail(user.getEmail());
		if(b2) {
			throw new EmailExitException();//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
		}
		
		user.setPassword(ServiceUtils.md5(user.getPassword()));//将密码用Md5编码加密
		dao.add(user);
		
	}
	
	public User login(String username,String password){
		
		password = ServiceUtils.md5(password);
		return dao.find(username, password);
			
	}
	
	
	public List get(String information,String condition){
		
		return dao.getUser(information, condition);
			
	}
	
	public List getAll(){
		
		return dao.getUser();
			
	}
	
	public PageBean pageQuerry(QuerryInfo qi) {
		
		
		QuerryResult qr = dao.pageQuerry(qi.getStartindex(),qi.getPagesize());
		
		PageBean bean = new PageBean();
		bean.setCurrentpage(qi.getCurrentpage());
		bean.setList(qr.getList());
		bean.setPagesize(qi.getPagesize());
		bean.setTotalrecord(qr.getTotalrecord());
		
		return bean;
		
	}
	
	public PageBean pageQuerry(QuerryInfo qi,String information,String condition) {
		
		QuerryResult qr = dao.pageQuerry(qi.getStartindex(),qi.getPagesize(),information,condition);

		PageBean bean = new PageBean();
		bean.setCurrentpage(qi.getCurrentpage());
		bean.setList(qr.getList());
		bean.setPagesize(qi.getPagesize());
		bean.setTotalrecord(qr.getTotalrecord());
		
		return bean;
		
	}
	
	public int updateUser(User user,String target,String newvalue) {
		/*if(dao.find(user.getUsername(), user.getPassword()) == null) {
			
			return 0;
		}
		*/
		if(target == "nickname") {
			boolean b1 = dao.findNickname(newvalue);
			if(b1) {
				return 1;//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
			}
		}
		if(target == "email") {
			boolean b2 = dao.findEmail(newvalue);
			if(b2) {
				return 2;//注册用户已存在，则必须给WEB层抛出编译时异常，让WEB层必须处理异常，给用户友好提示
			}
		}
		//System.out.println(user.getId());
		//System.out.println(target);
		//System.out.println(newvalue);
		
		
		dao.update(user.getId(), target, newvalue);
		return 3;
	}
	
	public User findByName(String username) {
		return dao.findByName(username);
	}
	
	public User findById(String id) {
		return dao.findById(id);
	}
	
	public void deleteUser(User user) {
		dao.deleteUser(user);
	}
}
