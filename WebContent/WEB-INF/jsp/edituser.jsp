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
	<form
		action="${pageContext.request.contextPath }/EditUserServlet?username=${user.username}"
		method="post">
		<a href="${pageContext.request.contextPath }/UpfileServlet?userid=${user.id}">上传头像</a>
		<c:if test="${headshot!=null }">
			<a href="${pageContext.request.contextPath }/DownLoadServlet?userid=${user.id}">保存头像</a>
		</c:if>
		<table frame="border" width="85%">
			<tr>
				<td>用户名：${user.username }</td>
			</tr>


			<tr>
				<td>昵称：${user.nickname }</td>
				<td>新的昵称：<input type="text" id="newnickname" name="newnickname">${form.errors.nickname }</td>
			</tr>

			<tr>
				<td>邮箱:${user.email }</td>
				<td>新的邮箱：<input type="text" id="newemail" name="newemail">${form.errors.email }</td>
			</tr>

			<tr>
				<td>生日：${user.birthday }</td>
				<td>新的生日：<input type="text" id="newbirthday" name="newbirthday">${form.errors.birthday }</td>
			</tr>
		</table>
		请输入密码：<input type="password" id="password" name="password">${message }
		<input type="submit" value="提交"><a href="index.jsp">首页</a>
	</form>
</body>
</html>