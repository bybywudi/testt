<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

${message }
<c:if test="${pleaselogin!=null }">

	<c:if test="${user==null }">
		<a href="${pageContext.request.contextPath }/RegisterUIServlet">注册</a>
		<a href="${pageContext.request.contextPath }/LoginUIServlet">登陆</a>
		
		
	</c:if>
</c:if>
</body>
</html>