package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import domain.web.QuerryResult;
import domain.web.User;

public interface UserDao {

	void add(User user);

	User find(String username, String password);

	boolean find(String username);

	boolean findNickname(String nickname);

	boolean findEmail(String email);

	List getUser(String information, String condition);

	List getUser();

	QuerryResult pageQuerry(int startindex, int pagesize);

	QuerryResult pageQuerry(int startindex, int pagesize, String information, String condition);

	void update(String id, String target, String newvalue);

	User findByName(String username);

	User findById(String id);

	void deleteUser(User user);

}