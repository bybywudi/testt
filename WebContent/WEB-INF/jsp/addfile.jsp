<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="${pageContext.request.contextPath }/UpfileServlet" method="post" enctype="multipart/form-data">
	${message}
	
	<input type="hidden" name="userid" value="${user.id}">
	<table>
		<tr>
			<td>上传文件</td>
			<td>
				<input type="file" name="file">
			</td>
		</tr>
		
		<tr>
			<td>签名</td>
			<td>
				<textarea rows="6" cols="50" name="description"></textarea>
			</td>
		</tr>
	
	</table>
	<input type="submit" value="上传">
</form>
</body>
</html>
