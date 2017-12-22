package domain.web;

import java.util.ArrayList;
import java.util.List;

public class SerchInfo {
	public static List getInfolist() {
		return infolist;
	}


	private static String name = "name";
	private static String nickname = "nickname";
	private static String birthday = "birthday";
	private static String email = "email";
	
	private static List infolist = new ArrayList();
	
	static {
		infolist.add(name);
		infolist.add(nickname);
		infolist.add(birthday);
		infolist.add(email);
	}
	
}
