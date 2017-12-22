package utils;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.web.Upfile;
import domain.web.User;
import exception.NotImageException;

public class WebUtils {
	/*public static <T> T request2Bean(HttpServletRequest request,Class<T> beanClass) {//泛型
		
		
		try {
			//1、创建要封装数据的bean
			T bean = beanClass.newInstance();
			
			//2、数据整到上边的Bean中
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				
				BeanUtils.setProperty(bean, name, value);
			}
			
			return bean;
			
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		
		
	}*/
	
public static final String types[] = {"jpg","gif","jpeg","png"};
	
public static <T> T request2Bean(HttpServletRequest request,Class<T> beanClass) {//泛型
		
		
		try {
			//1、创建要封装数据的bean
			T bean = beanClass.newInstance();
			Map map = request.getParameterMap();
			
			ConvertUtils.register(new Converter() {
				public Object convert(Class type,Object value) {
					if(value==null) {
						return null;
					}
					String str = (String) value;
					if(str.trim().equals("")) {
						return null;
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						return df.parse(str);
					}catch(ParseException e){
						throw new RuntimeException(e);
					}
				}
			}, Date.class);
			
			BeanUtils.populate(bean, map);
			return bean;
			
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		
		
	}
	
	public static void copyBean(Object src,Object dest) {
			//注册一个日期转换器
			ConvertUtils.register(new Converter() {
			public Object convert(Class type,Object value) {
				
				if(value==null) {
					return null;
				}
				
				String str = (String) value;
				
				if(str.trim().equals("")) {
					return null;
				}
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return df.parse(str);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				
			}
			
		}, Date.class);
			
			try {
				BeanUtils.copyProperties(dest,src);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			} 
	}
	
	//生成全球唯一ID
	public static String generateID() {
		
		
		return UUID.randomUUID().toString();
		
	}
	
	private static String generateFilename(String filename) {
		
		String ext = filename.substring(filename.lastIndexOf(".")+1);
		return UUID.randomUUID().toString() + "_" + ext;
		
	}
	
	private static String generateSavePath(String path,String filename) {
		int hashcode = filename.hashCode();
		int dir1 = hashcode&15;
		int dir2 = (hashcode>>4)&0xf;
		
		String savepath = path + File.separator +dir1 + File.separator + dir2;
		File file = new File(savepath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		return savepath;
	}
	
	public static Upfile doUpload(HttpServletRequest request,String uppath) throws FileSizeLimitExceededException {
		
		Upfile bean = new Upfile();
		
		try {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/temp")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");//解决上传文件中文乱码问题
		upload.setFileSizeMax(1000*1000);//设置上传文件大小限制，会抛出FileSizeLimitExceededException异常
		
		List<FileItem> list = upload.parseRequest(request);//解析上传的文件
		for(FileItem item : list) {
			if(item.isFormField()) {//判断是不是普通输入项
				String name = item.getFieldName();//获得非文件输入项的名称
				String value = item.getString("UTF-8");//获得值
				BeanUtils.setProperty(bean, name, value);
			}else {
				
				String filename = item.getName().substring(item.getName().lastIndexOf("\\")+1);
				String type = filename.substring(filename.lastIndexOf(".")+1);
				
				if(!contains(types,type)) {
					return null;
				}
				
				
				
				/*if(!isImage(item)) {
					return null;
				}*/
				
				//得到上传文件名
				
				//得到UUID名
				String uuidname = generateFilename(filename);
				//得到保存路径
				String savepath = generateSavePath(uppath,uuidname);
				String id = generateID();
				
				InputStream in = item.getInputStream();
				int len = 0;
				byte buffer[] = new byte[1024];
				FileOutputStream out = new FileOutputStream(savepath + "\\" + uuidname);
				while((len=in.read(buffer))>0) {
					out.write(buffer,0,len);
				}
				in.close();
				out.close();
				item.delete();
				
				bean.setFilename(filename);
				bean.setId(id);
				bean.setSavepath(savepath);
				bean.setUptime(new Date());
				bean.setUuidname(uuidname);
			}
			
		}
		
		return bean;
		
		} catch(FileUploadBase.FileSizeLimitExceededException e) {
			throw e;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	//通过判断图片的宽高来判断文件是不是图片
	public static boolean isImage(File item) {  
	    if (! item.exists()) {  
	        return false;  
	    }  
	    Image img = null;  
	    try {  
	        img = ImageIO.read(item);  
	        if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {  
	            return false;  
	        }  
	        return true;  
	    } catch (Exception e) {  
	        return false;  
	    } finally {  
	        img = null;  
	    }  
	} 
	
	public static boolean contains(String[] arr, String targetValue) {
	    for(String s: arr){
	        if(s.equals(targetValue))
	            return true;
	    }
	    return false;
	}
	
public static Cookie buildCookie(User user, int expirestime, HttpServletRequest request) {
		
		long currenttime = System.currentTimeMillis();
		String md5_Server = user.getUsername() + ":" + (currenttime + expirestime*1000) + ":" + user.getPassword();
		md5_Server = ServiceUtils.md5(md5_Server);
		
		String autoLogin = user.getUsername() + ":" + (currenttime + expirestime*1000) + ":" + md5_Server;
		
		Cookie cookie = new Cookie("autoLogin",autoLogin);
		cookie.setMaxAge(expirestime);
		cookie.setPath("request.getContextPath()");
		return cookie;
	}
	

}
