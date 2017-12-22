package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import domain.web.Upfile;
import exception.NotImageException;
import service.impl.BusinessServiceImpl;
import service.impl.UpfileServiceImpl;
import utils.WebUtils;

/**
 * Servlet implementation class UpfileServlet
 */
@WebServlet("/UpfileServlet")
public class UpfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("message", "不支持的操作");
			request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
			return;
		}
		
		try {
			String savepath = this.getServletContext().getRealPath("/WEB-INF/headshot");
			Upfile file = WebUtils.doUpload(request,savepath);
			
			if(file == null) {
				request.setAttribute("message", "上传文件必须是图片");
				request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
				return;
			}
			
			UpfileServiceImpl service = new UpfileServiceImpl();
			service.addUpfile(file);
			
			request.setAttribute("message", "上传成功");
			request.getSession().setAttribute("headshot", file);
			request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
		}catch(FileUploadBase.FileSizeLimitExceededException e) {
			request.setAttribute("message", "图片大小不能超过1M");
			request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传失败");
			request.getRequestDispatcher("/WEB-INF/jsp/addfile.jsp").forward(request, response);
		}
		
	}

}
