package web.form;

import java.util.*;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class EditUserForm {

		private String newemail;
		private String newnickname;
		private String newbirthday;
 
		private Map errors = new HashMap();
		
		public String getNewemail() {
			return newemail;
		}


		public void setNewemail(String newemail) {
			this.newemail = newemail;
		}


		public String getNewnickname() {
			return newnickname;
		}


		public void setNewnickname(String newnickname) {
			this.newnickname = newnickname;
		}


		public String getNewbirthday() {
			return newbirthday;
		}


		public void setNewbirthday(String newbirthday) {
			this.newbirthday = newbirthday;
		}


		public Map getErrors() {
			return errors;
		}


		public void setErrors(Map errors) {
			this.errors = errors;
		}



		 
		
		public boolean validate() {
			boolean isOK = true;
			
			
			if(this.newemail==null || this.newemail.trim().equals("")) {
				//isOK = false;
				//errors.put("email", "邮箱不能为空");
			}else {
				if(!this.newemail.matches("^[A-Za-z_]{1,}[0-9]{0,}@[A-Za-z0-9]{1,}\\.([A-Za-z]{1,})+")){
					isOK = false;
					errors.put("email", "邮箱不合法");
				}
			}
			
			if(this.newnickname==null || this.newnickname.trim().equals("")) {
				//isOK = false;
				//errors.put("nickname", "昵称不能为空");
			}else {
				if(!this.newnickname.matches("[\u4e00-\u9fa5A-Za-z0-9]{0,10}")){
					isOK = false;
					errors.put("nickname", "昵称必须为十位以内的汉字，数字，字母组合");
				}
			}
			

			if((this.newbirthday !=null && !this.newbirthday.trim().equals(""))){
				
				try {
					DateLocaleConverter dlc = new DateLocaleConverter();
					dlc.convert(this.newbirthday,"yyyy-MM-dd");
				}catch(Exception e) {
				isOK = false;
				errors.put("birthday", "生日格式不对");
				}
			}
			
			return isOK;
			
		}

	

}
