package web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.web.PageBean;
import domain.web.QuerryInfo;
import domain.web.SerchInfo;
import domain.web.User;
import service.impl.BusinessServiceImpl;
import utils.WebUtils;

/**
 * Servlet implementation class ListUserServlet
 */
@WebServlet("/ListUserServlet")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String information = request.getParameter("information");
		String condition = request.getParameter("condition");
		
		//String information = "name";
		//String condition = null;
		
		BusinessServiceImpl service = new BusinessServiceImpl();
		
		List list = new LinkedList();
		
		QuerryInfo qi = WebUtils.request2Bean(request, QuerryInfo.class);
		/*QuerryInfo qi = new QuerryInfo();
		if(request.getAttribute("pagesize").toString().trim() != "" && request.getAttribute("pagesize") != null) {
			qi.setPagesize((int) request.getAttribute("pagesize"));
		}
		if(request.getAttribute("currentpage").toString().trim() != "" && request.getAttribute("currentpage") != null) {
			qi.setCurrentpage((int) request.getAttribute("currentpage"));
		}*/

		
		
		PageBean pagebean = new PageBean();
		
		
		
		if(condition == null || condition.trim() == "") {
			
			pagebean = service.pageQuerry(qi);
			//list = service.getAll();
			//response.getWriter().write("1");
		}else {
			//list = service.get(information, condition);
			pagebean = service.pageQuerry(qi, information, condition);
		}
		
		
		if(!pagebean.getList().isEmpty()) {
			request.setAttribute("pagebean", pagebean);
			request.setAttribute("condition", condition);
			request.setAttribute("information", information);
			//request.setAttribute("serchinfo", SerchInfo.getInfolist());
			request.getRequestDispatcher("/WEB-INF/jsp/listuser.jsp").forward(request, response);
			return;
		}
		
		
		//request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
		request.setAttribute("pagebean", pagebean);
		request.setAttribute("condition", condition);
		request.setAttribute("information", information);
		//request.setAttribute("serchinfo", SerchInfo.getInfolist());
		request.setAttribute("message", "该用户不存在");
		request.getRequestDispatcher("/WEB-INF/jsp/listuser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
