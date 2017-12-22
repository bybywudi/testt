package dao.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import dao.UserDao;
import domain.admin.Privilege;
import domain.admin.Role;
import domain.web.QuerryResult;
import domain.web.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import utils.BeanListHandler;
import utils.JdbcUtils;
import utils.ServiceUtils;
import utils.XmlUtils;

public class UserDaoImpl implements UserDao {
	@Override
	public void add(User user){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			//String sql = "insert into users(id,name,password,nickname,email,birthday) " + "values('"+user.getId()+"','"+user.getUsername()+"','"+user.getPassword()+"','"+user.getNickname()+"','"+user.getEmail()+"','"+user.getBirthday().toLocaleString()+"')";
			String sql = "insert into users(id,name,password,nickname,email,birthday,isDelete) values(?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1, user.getId());
			st.setString(2, user.getUsername());
			st.setString(3, user.getPassword());
			st.setString(4, user.getNickname());
			st.setString(5, user.getEmail());
			st.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			st.setString(7, "0");
			
			st.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	/*
	@Override
	public User find(String username,String password){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "select * from users where name='"+username+"' and password='"+password+"'";
			rs = st.executeQuery(sql);
			
			//数据封装到Bean
			
			/*String date = (String) rs.getObject("birthday");
			if(date==null || date.equals("")) {
			user.setBirthday(null);
			}else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				user.setBirthday(df.parse(date));
			}*/
			/*if(rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
			
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
	}*/
	
	public User find(String username,String password){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from users where name=? and password=?";
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			st.setString(2, password);
			
			rs = st.executeQuery();
			
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return null;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {user.getId(),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list);
				
				return user;
			}
			
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
	}
	
	public List getUser(String information,String condition){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql;
		
		List list = new LinkedList();
		try {
			conn = JdbcUtils.getConnection();
			
			switch(information){
				case "name":
					sql = "select * from users where name like ?";
					st = conn.prepareStatement(sql);
					st.setString(1, condition + "%");
					rs = st.executeQuery();
					break;
					
				case "nickname":
					sql = "select * from users where nickname like ?";
					st = conn.prepareStatement(sql);
					st.setString(1, condition + "%");
					rs = st.executeQuery();
					break;
					
				case "email":
					sql = "select * from users where email=?";
					st = conn.prepareStatement(sql);
					st.setString(1, condition);
					rs = st.executeQuery();
					break;
					
				case "birthday":
					sql = "select * from users where birthday like ?";
					st = conn.prepareStatement(sql);
					st.setString(1, condition + "%");
					rs = st.executeQuery();
					break;
			}
			while(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					continue;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {user.getId(),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list1 = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list1);
				
				list.add(user);
			}
			
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
	}
	
	public List getUser(){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from users";
			st = conn.prepareStatement(sql);

			
			rs = st.executeQuery();
			
			List list = new LinkedList();
			
			while(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					continue;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {user.getId(),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list1 = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list1);
				
				list.add(user);
			}
			
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
	}
	
	@Override
	public boolean find(String username) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from users where name=?";
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return false;
				}
				return true;
			}
			return false;
			
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	public User findByName(String username) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from users where name=?";
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return null;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {user.getId(),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list);
				return user;
			}
			return null;
			
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	public User findById(String id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from users where id=?";
			st = conn.prepareStatement(sql);
			st.setString(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return null;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {id,"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list);

				return user;
			}
			return null;
			
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	public boolean findNickname(String nickname){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from users where nickname=?";
			st = conn.prepareStatement(sql);
			st.setString(1, nickname);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return false;
				}
				return true;
			}
			return false;
			
			
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	public boolean findEmail(String email){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from users where email=?";
			st = conn.prepareStatement(sql);
			st.setString(1, email);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					return false;
				}
				return true;
			}
			return false;
			
			
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	public QuerryResult pageQuerry(int startindex,int pagesize) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		QuerryResult qr = new QuerryResult();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from users where isDelete=? limit ?,?";
			st = conn.prepareStatement(sql);
			
			st.setString(1, "0");
			st.setInt(2, startindex);
			st.setInt(3, pagesize);
			rs = st.executeQuery();
			List list = new LinkedList();
			
			while(rs.next()) {
				if(rs.getString("isDelete").equals("1")) {
					continue;
				}
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {rs.getString("id"),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list1 = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list1);
				
				
				list.add(user);
			}
			qr.setList(list);
			
			sql = "select count(*) from users where isDelete=?";
			st = conn.prepareStatement(sql);
			st.setString(1, "0");
			rs = st.executeQuery();
			if(rs.next()) {
				qr.setTotalrecord(rs.getInt(1));
			}
			return qr;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		

		
	}
	
public QuerryResult pageQuerry(int startindex,int pagesize,String information,String condition) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		QuerryResult qr = new QuerryResult();
		try {
			conn = JdbcUtils.getConnection();
			List list = new LinkedList();
			String sql;
			
			
			switch(information){
			case "name":
				sql = "select count(*) from users where isDelete=? and name like ?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				rs = st.executeQuery();
				if(rs.next()) {
					qr.setTotalrecord(rs.getInt(1));
				}
				
				sql = "select * from users where isDelete=? and name like ? limit ?,?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				st.setInt(3, startindex);
				st.setInt(4, pagesize);
				rs = st.executeQuery();
				
				break;
				
			case "nickname":
				sql = "select count(*) from users where isDelete=? and nickname like ?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				rs = st.executeQuery();
				if(rs.next()) {
					qr.setTotalrecord(rs.getInt(1));
				}
				
				sql = "select * from users where isDelete=? and nickname like ? limit ?,?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				st.setInt(3, startindex);
				st.setInt(4, pagesize);
				rs = st.executeQuery();
				break;
				
			case "email":
				sql = "select count(*) from users where isDelete=? and email=?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition);
				rs = st.executeQuery();
				if(rs.next()) {
					qr.setTotalrecord(rs.getInt(1));
				}
				
				sql = "select * from users where isDelete=? and email=? limit ?,?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition);
				st.setInt(3, startindex);
				st.setInt(4, pagesize);
				rs = st.executeQuery();
				break;
				
			case "birthday":
				sql = "select count(*) from users where isDelete=? and birthday like ?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				rs = st.executeQuery();
				if(rs.next()) {
					qr.setTotalrecord(rs.getInt(1));
				}
				
				sql = "select * from users where isDelete=? and birthday like ? limit ?,?";
				st = conn.prepareStatement(sql);
				st.setString(1,"0");
				st.setString(2, condition + "%");
				st.setInt(3, startindex);
				st.setInt(4, pagesize);
				rs = st.executeQuery();
				break;
		}
			
			while(rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getString("id"));
				user.setNickname(rs.getString("nickname"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setBirthday(rs.getDate("birthday"));
				
				Object params[] = {rs.getString("id"),"0"};
				sql = "select r.* from users_role ur,role r where ur.users_id=? and r.id=ur.role_id and r.isDelete=?";
				List list1 = (List) JdbcUtils.querry(sql, params, new BeanListHandler(Role.class));
				user.getRoles().addAll(list1);
				
				list.add(user);
			}
			qr.setList(list);
			
			return qr;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			JdbcUtils.release(conn, st, rs);
		}
		

		
	}
 

public void update(String id,String target,String newvalue){
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql;
		int num;
		switch(target){
		case "nickname":
			sql = "update users set nickname=? where id=?";
			st = conn.prepareStatement(sql);
			st.setString(1, newvalue);
			st.setString(2, id);
			
			st.executeUpdate();
			break;
		
		case "email":
			sql = "update users set email=? where id=?";
			st = conn.prepareStatement(sql);
			st.setString(1, newvalue);
			st.setString(2, id);
			
			st.executeUpdate();
			break;
			
		case "birthday":
			sql = "update users set birthday=? where id=?";
			st = conn.prepareStatement(sql);
			st.setString(1, newvalue);
			st.setString(2, id);
			
			st.executeUpdate();
			break;
			
		case "password":
			sql = "update users set password=? where id=?";
			newvalue = ServiceUtils.md5(newvalue);
			st = conn.prepareStatement(sql);
			st.setString(1, newvalue);
			st.setString(2, id);
			
			st.executeUpdate();
			break;
		
		}
		
		
	}catch(Exception e){
		throw new RuntimeException(e);
	}finally {
		JdbcUtils.release(conn, st, rs);
	}
	
	
}

public void updateUserRole(User user,List<Role> roles) {
	String sql = "delete from users_role where users_id=?";
	Object params[] = {user.getId()};
	JdbcUtils.update(sql, params);
	
	for(Role role : roles) {
		sql = "insert into users_role(users_id,role_id) value(?,?)";
		Object params1[] = {user.getId(),role.getId()};
		JdbcUtils.update(sql, params1);
	}
}

public void deleteUser(User user) {
	String sql = "update users set isDelete=? where id=?";
	Object params[] = {"1",user.getId()};
	JdbcUtils.update(sql, params);
}

/*public Boolean update(String id,String target,String newvalue){
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select * from users where id=?";
		st = conn.prepareStatement(sql);
		st.setString(1, id);
		
		rs = st.executeQuery();
		
		if(rs.next()) {
			rs.updateString(target, newvalue);
			return true;
		}
		
		return false;
	
	}catch(Exception e){
		throw new RuntimeException(e);
	}finally {
		JdbcUtils.release(conn, st, rs);
	}
}*/

}


	