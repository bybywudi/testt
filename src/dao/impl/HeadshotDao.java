package dao.impl;

import java.io.File;
import java.util.List;

import domain.web.Upfile;
import utils.BeanHandler;
import utils.JdbcUtils;

public class HeadshotDao {
	public void add(Upfile file) {
		String sql = "insert into headshot(id,uuidname,filename,savepath,uptime,description,userid) value(?,?,?,?,?,?,?)";
		Object params[] = {file.getId(),file.getUuidname(),file.getFilename(),file.getSavepath(),file.getUptime(),file.getDescription(),file.getUserid()};
		
		JdbcUtils.update(sql, params);
	}
	
	public List<Upfile> getAll(){
		return null;
		
	}
	
	public Upfile find(String id) {
		
		String sql = "select *from headshot where id=?";
		Object params[] = {id};
		
		return (Upfile)JdbcUtils.querry(sql, params, new BeanHandler(Upfile.class)); 
		
		
	}
	
	public Upfile findByFilename(String filename) {
		
		String sql = "select *from headshot where filename=?";
		Object params[] = {filename};
		
		return (Upfile)JdbcUtils.querry(sql, params, new BeanHandler(Upfile.class)); 
		
		
	}
	
	public Upfile findByUserid(String userid) {
		
		String sql = "select *from headshot where userid=?";
		Object params[] = {userid};
		
		return (Upfile)JdbcUtils.querry(sql, params, new BeanHandler(Upfile.class)); 
		
		
	}
	
	public boolean delete(Upfile file) {
		
		JdbcUtils.startTransaction();
		
		String sql = "delete from headshot where id=?";
		Object params[] = {file.getId()};
		JdbcUtils.update(sql, params);
		
		if(!deleteFile(file.getUuidname(),file.getSavepath())) {
			return false;
		}
		
		JdbcUtils.commitTransaction();

		return true;
	}
	
	public void update(Upfile file,String id) {//传入新的文件的信息和旧的文件的Id，id应该保持不变
		
		String sql = "update headshot set savepath=?,filename=?,description=?,uuidname=?,uptime=? where id=?";
		Object params[] = {file.getSavepath(),file.getFilename(),file.getDescription(),file.getUuidname(),file.getUptime(),id};
		
		JdbcUtils.update(sql, params);
	}
	
	/*public boolean deleteFile(String sPath) {  
	    boolean flag = false;    
	    File file = new File(sPath);    
	    // 路径为文件且不为空则进行删除    
	    if (file.isFile() && file.exists()) {    
	        file.delete();    
	        flag = true;    
	    }    
	    return flag;    
	}   */
	public boolean deleteFile(String uuidname,String savepath) {
		File folder = new File(savepath);  
		File[] files = folder.listFiles();  
		for(File file:files){  
		    if(file.getName().equals(uuidname)){  
			    if(file.exists()) {
			    	file.delete(); 
			    }
			    if(folder.length() == 0) {
			    	folder.delete();
			    }
			    return true;
		    }  
		} 
		return false;
	}
}
 