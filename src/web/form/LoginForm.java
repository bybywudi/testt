package web.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class LoginForm {
	private String username;
	private String password;
	private Map errors = new HashMap();
	
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
	
	public Map getErrors() {
		return errors;
	}
	public void setErrors(Map errors) {
		this.errors = errors;
	}
	
	public boolean validate() {
		boolean isOK = true;
		
		if(this.username==null || this.username.trim().equals("")) {
			isOK = false;
			errors.put("username", "用户名不能为空");
		}else {
			if(!this.username.matches("[A-Za-z]{3,8}")){
				isOK = false;
				errors.put("username", "用户名必须为3-8位字母");
			}
		}
		
		if(this.password==null || this.password.trim().equals("")) {
			isOK = false;
			errors.put("password", "密码不能为空");
		}else {
			if(!this.password.matches("[A-Za-z0-9_@.]{3,12}")){
				isOK = false;
				errors.put("password", "密码中含有不合法符号，且必须为3-12位");
			}
		}
		
		return isOK;
		
	}
}
