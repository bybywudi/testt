package web.form;

import java.util.*;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class RegisterForm {
	private String username;
	private String password;
	private String repassword;
	private String email;
	private String nickname;
	private String birthday;
	
	private Map errors = new HashMap();
	 
	public Map getErrors() {
		return errors;
	}
	public void setErrors(Map errors) {
		this.errors = errors;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public boolean validate() {
		boolean isOK = true;
		
		if(this.username==null || this.username.trim().equals("")) {
			isOK = false;
			errors.put("username", "用户名不能为空");
		}else {
			if(!this.username.matches("[A-Za-z0-9]{3,12}")){
				isOK = false;
				errors.put("username", "用户名必须为3-12位数字或字母");
			}
		}
		
		if(this.password==null || this.password.trim().equals("")) {
			isOK = false;
			errors.put("password", "密码不能为空");
		}else {
			if(!this.password.matches("[A-Za-z0-9_@.]{3,12}")){
				isOK = false;
				errors.put("password", "密码中不能含有不合法符号，且必须为3-12位");
			}
		}
		

		if(this.repassword==null || this.repassword.trim().equals("")) {
			isOK = false;
			errors.put("repassword", "请重复输入密码");
		}else {
			if(!this.repassword.equals(this.password)) {
				isOK = false;
				errors.put("repassword", "两次密码不一致");
			}
		}
		
		if(this.email==null || this.email.trim().equals("")) {
			isOK = false;
			errors.put("email", "邮箱不能为空");
		}else {
			if(!this.email.matches("^[A-Za-z_]{1,}[0-9]{0,}@[A-Za-z0-9]{1,}\\.([A-Za-z]{1,})+")){
				isOK = false;
				errors.put("email", "邮箱不合法");
			}
		}
		
		if(this.nickname==null || this.nickname.trim().equals("")) {
			isOK = false;
			errors.put("nickname", "昵称不能为空");
		}else {
			if(!this.nickname.matches("[\u4e00-\u9fa5A-Za-z0-9]{0,10}")){
				isOK = false;
				errors.put("nickname", "昵称必须为十位以内的汉字，数字，字母组合");
			}
		}
		

		if((this.birthday !=null && !this.birthday.trim().equals(""))){
			
			try {
				DateLocaleConverter dlc = new DateLocaleConverter();
				dlc.convert(this.birthday,"yyyy-MM-dd");
			}catch(Exception e) {
			isOK = false;
			errors.put("birthday", "生日格式不对");
			}
		}
		
		return isOK;
		
	}

}
