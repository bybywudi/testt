<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${user!=null }">
	欢迎你:${user.nickname } <a
			href="${pageContext.request.contextPath }/LogoutServlet">注销</a>
		 
		 <a href="${pageContext.request.contextPath }/ListUserUIServlet">查找用户</a>
		<a href="${pageContext.request.contextPath }/EditUserUIServlet">修改信息</a>
		<a href="${pageContext.request.contextPath }/ManageUIServlet">管理</a>
	</c:if>
 

	<c:if test="${user==null }">
		<a href="${pageContext.request.contextPath }/RegisterUIServlet">注册</a>
		<a href="${pageContext.request.contextPath }/LoginUIServlet">登陆</a>
		
		                       
	</c:if>
</body>
</html>