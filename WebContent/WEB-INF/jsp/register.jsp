<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/RegisterServlet"
		method="post">
		用户名：<input type="text" name="username">${form.errors.username }<br />
		密码：<input type="password" name="password">${form.errors.password }<br />
		确认密码：<input type="password" name="repassword">${form.errors.repassword }<br />
		邮箱：<input type="text" name="email">${form.errors.email }<br />
		生日：<input type="text" name="birthday">${form.errors.birthday }<br />
		昵称：<input type="text" name="nickname">${form.errors.nickname }<br />
		<input type="submit" value="注册">
	</form>
</body>
</html>