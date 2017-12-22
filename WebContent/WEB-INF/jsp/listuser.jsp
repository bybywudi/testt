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
	<a href="${pageContext.request.contextPath }/index.jsp" target="main">首页</a>
	<br/>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllPrivilege" target="main">权限管理</a>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllResource" target="main">资源管理</a>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllRole" target="main">角色管理</a>
	<a href="${pageContext.request.contextPath }/ListUserUIServlet" target="main">用户管理</a>
	<br/>

<script type="text/javascript">
	function setpagesize(currentpage,pagesize,oldvalue){
		
			//var pagesize = document.getElementById("pagesize").value;
			if(pagesize<=0 || pagesize!=parseInt(pagesize)){
				alert("请输入正整数");
				document.getElementById("pagesize").value = oldvalue;
			}else{
			
				var condition = document.getElementById("condition").value;
				var information = document.getElementById("information").value;
				
	
				/*if((condition.length == 0 || condition.replace(/(^s*)|(s*$)/g, "").length ==0 || condition == undefined || condition == null) && (totalrecord.length == 0 || totalrecord.replace(/(^s*)|(s*$)/g, "").length ==0 || totalrecord == undefined || totalrecord == null)){
					alert('请输入搜索条件'); 
				}*/
				window.location.href = '${pageContext.request.contextPath}/ListUserServlet?currentpage=1' + '&pagesize=' + pagesize+ '&condition=' + condition+ '&information=' + information;
			}
	}
	
	function gotopage(pagenum,oldvalue,totalpage){
		if(pagenum<=0 || pagenum!=parseInt(pagenum)){
			alert("请输入正整数");
			document.getElementById("pagenum").value = oldvalue;
		}else{
			if(pagenum>totalpage){
				alert("请输入页数范围内的数");
				document.getElementById("pagenum").value = oldvalue;
			}else{
				var pagesize = document.getElementById("pagesize").value;
				var condition = document.getElementById("condition").value;
				var information = document.getElementById("information").value;
				
				window.location.href = '${pageContext.request.contextPath}/ListUserServlet?currentpage=' + pagenum + '&pagesize=' + pagesize+ '&condition=' + condition+ '&information=' + information;
			}
		}
}
	
	/*function pagesizevalue(){
		if(document.getElementById("pagesize") == null){
			var pagesize = 1;
		}else{
		
			var pagesize = document.getElementById("pagesize").value;
		}
		document.getElementById("pagesize_oncheked").value = pagesize;

	}*/
</script>

	<table>
		<tr>
			<td>
				<form action="${pageContext.request.contextPath }/ListUserServlet"
					method="post">

					<c:set var="name" value="name" />
					<c:set var="nickname" value="nickname" />
					<c:set var="email" value="email" />
					<c:set var="birthday" value="birthday" />
					<c:set var="zero" value="0" />

					查找类型：
					<c:choose>
						<c:when test="${information==name}">
							<select name="information" id="information">
								<option value="name" selected="selected">用户名</option>
								<option value="nickname"}>昵称</option>
								<option value="email"}>邮箱</option>
								<option value="birthday">生日</option>
							</select>
						</c:when>
						<c:when test="${information==nickname}">
							<select name="information" id="information">
								<option value="name">用户名</option>
								<option value="nickname" selected="selected"}>昵称</option>
								<option value="email"}>邮箱</option>
								<option value="birthday">生日</option>
							</select>
						</c:when>
						<c:when test="${information==email}">
							<select name="information" id="information">
								<option value="name">用户名</option>
								<option value="nickname"}>昵称</option>
								<option value="email" selected="selected"}>邮箱</option>
								<option value="birthday">生日</option>
							</select>
						</c:when>
						<c:when test="${information==birthday}">
							<select name="information" id="information">
								<option value="name">用户名</option>
								<option value="nickname"}>昵称</option>
								<option value="email"}>邮箱</option>
								<option value="birthday" selected="selected">生日</option>
							</select>
						</c:when>
						<c:otherwise>
							<select name="information" id="information">
								<option value="name">用户名</option>
								<option value="nickname"}>昵称</option>
								<option value="email"}>邮箱</option>
								<option value="birthday">生日</option>
							</select>
						</c:otherwise>
					</c:choose>

					<c:if test="${empty pagebean.pagesize}">
						<input type="hidden" value="10" name="pagesize">	<%-- 默认的每页显示数 --%>
						
					</c:if>

					<c:if test="${!empty pagebean.pagesize}">
						<input type="hidden" value="${pagebean.pagesize }" name="pagesize">
					</c:if>

					查找条件：<input type="text" id="condition" value="${condition }"
						name="condition"> <input type="submit" value="查找">
				</form>
			</td>

			<td>
			<%-- 
				<form action="${pageContext.request.contextPath }/ListUserServlet"
					method="post">
					<input type="hidden" value="" name="condition">
					<c:if test="${empty pagebean.pagesize}">
						<input type="hidden" value="5" name="pagesize">						
					</c:if>

					<c:if test="${!empty pagebean.pagesize}">
						<input type="hidden" value="${pagebean.pagesize }" name="pagesize">
					</c:if>
					<input type="submit" value="查找全部"><br />
				</form>
			--%>
			<td>
		</tr>
	</table>

	${message }


	<table frame="border" width="85%">

		<tr>
			<td>用户名</td>
			<td>昵称</td>
			<td>邮箱</td>
			<td>生日</td>
		</tr>

		<c:forEach var="user" items="${requestScope.pagebean.list }">
			<tr>
				<td>${user.username }</td>
				<td>${user.nickname }</td>
				<td>${user.email }</td>
				<td>${user.birthday }</td>
				<td><a href="${pageContext.request.contextPath }/PrivilegeServlet?method=updateUserRoleUI&id=${user.id}">编辑用户</a></td>
				<td><a href="${pageContext.request.contextPath }/DeleteServlet?method=deleteUser&id=${user.id}">删除用户</a></td>
			</tr>
		</c:forEach>
		<br />


		<c:if test="${!empty pagebean}">
		共[${pagebean.totalrecord }]条记录,
		每页<input type="text" id="pagesize" value="${pagebean.pagesize }"
				onchange="setpagesize(${pagebean.currentpage },this.value,${pagebean.pagesize })"
				style="width: 30px" maxlength="3">条,
		共[${pagebean.totalpage }]页,
		当前第[${pagebean.currentpage }]页
		<c:if test="${pagebean.currentpage!=1 }">
				<a
					href="${pageContext.request.contextPath }/ListUserServlet?currentpage=${pagebean.previouspage}&pagesize=${pagebean.pagesize }&condition=${condition }&information=${information}">上一页&nbsp;</a>
			</c:if>


			<!-- 共[${pagebean.totalrecord }]条记录,每页[${pagebean.pagesize }]条,共[${pagebean.totalpage }]页,当前第[${pagebean.currentpage }]页-->



			<c:forEach var="pagenum" items="${pagebean.pagebar }">

				<c:if test="${pagenum==pagebean.currentpage }">
					<font color="red">${pagenum }&nbsp;</font>
				</c:if>
				<c:if test="${pagenum!=pagebean.currentpage }">
					<a
						href="${pageContext.request.contextPath }/ListUserServlet?currentpage=${pagenum}&pagesize=${pagebean.pagesize }&condition=${condition }&information=${information}">${pagenum }&nbsp;</a>
				</c:if>

			</c:forEach>

			<c:if test="${pagebean.currentpage!=pagebean.totalpage&&pagebean.totalrecord!=0 }">
				<a
					href="${pageContext.request.contextPath }/ListUserServlet?currentpage=${pagebean.nextpage}&pagesize=${pagebean.pagesize }&condition=${condition }&information=${information}">下一页&nbsp;</a>
			</c:if>

			<input type="text" id="pagenum" style="width: 30px">
			<input type="button" value="GO"
				onclick="gotopage(document.getElementById('pagenum').value,${pagebean.currentpage},${pagebean.totalpage })">
		</c:if>
	</table>
</body>
</html>