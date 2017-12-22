package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class ServiceUtils {
	
	public static String md5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");//得到Md5编译方法
			byte md5[] = md.digest(message.getBytes());//使用MD5加密传入的参数，存为比特形式
			
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		
		
	}
	
}
