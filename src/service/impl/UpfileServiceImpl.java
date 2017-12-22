package service.impl;

import java.util.List;

import dao.impl.HeadshotDao;
import domain.web.Upfile;

public class UpfileServiceImpl {
	
	private HeadshotDao dao = new HeadshotDao();
	
	public void addUpfile(Upfile file) {
		Upfile existfile = dao.findByUserid(file.getUserid());
		
		if(existfile != null) {
			dao.delete(existfile);
		}
		
		dao.add(file);
	}
	
	public List getAllUpfile() {
		return dao.getAll();
	}
	
	public Upfile findUpfile(String id) {
		return dao.find(id);
	}
	
	public Upfile findUpfileByUserid(String userid) {
		return dao.findByUserid(userid);
	}
}
