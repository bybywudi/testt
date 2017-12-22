<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
</head>
<body>
	<a href="${pageContext.request.contextPath }/index.jsp" target="main">首页</a>
	<br/>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllPrivilege" target="main">权限管理</a>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllResource" target="main">资源管理</a>
	<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=getAllRole" target="main">角色管理</a>
	<a href="${pageContext.request.contextPath }/ListUserUIServlet" target="main">用户管理</a>
	<br/>
	
	<%-- 列出所有权限的页面 --%>
	<c:if test="${allprivilege!=null }">
		<table width="60%">
			<tr>
				<td></td>
				<td></td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=addPrivilegeUI">添加权限</a>
				</td>
				<td>${message}</td>
			</tr>
		</table>
		<table width="60%" frame="border">
			<tr>
				<td>权限名称</td>
				<td>权限描述</td>
				<td>操作</td>
			</tr>
		<c:forEach var="p" items="${requestScope.privilegelist }">
		<tr>
				<td>${p.name }</td>
				<td>${p.description }</td>
				<td>
					<a href="">修改</a>
					<a href="${pageContext.request.contextPath }/DeleteServlet?method=deletePrivilege&id=${p.id}">删除</a>
				</td>
		</tr>
		</c:forEach>
		</table>
	</c:if>
	
	<%-- 添加权限的页面 --%>
	<c:if test="${addPrivilegeUI!=null }">
	<form action="${pageContext.request.contextPath }/PrivilegeServlet">
		<table width="60%">
			<tr>
				<td>权限名称</td>
				<td>
					<input type="text" name="name">
					<input type="hidden" name="method" value="addPrivilege">
				</td>
			</tr>
			
			<tr>
				<td>权限描述</td>
				<td>
					<textarea rows="5" cols="50" name="description"></textarea>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td>
					<input type="submit" value="添加权限">
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	
	<%-- 列出所有资源的页面 --%>
	<c:if test="${allresource!=null }">
		<table width="60%">
			<tr>
				<td></td>
				<td></td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=addResourceUI">添加资源</a>
				</td>
				<td>${message}</td>
			</tr>
		</table>
		<table width="60%" frame="border">
			<tr>
				<td>资源uri</td>
				<td>控制权限</td>
				<td>资源描述</td>
				<td>操作</td>
			</tr>
		<%--  
		<c:forEach var="r" items="${list }">
		<tr>
				<td>${r.uri }</td>
				<td>${r.privilege.name }</td>
				<td>${r.description }</td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=updateResourcePrivilegeUI&id=${r.id}">修改权限</a>
					<a href="">删除</a>
				</td>
		</tr>
		</c:forEach>
		这段代码一直出现propertynotfound的错误，是Privilege类里边找不到uri这个属性，最后发现是forEach标签用错了，首先List里存放的是privilege类型，却当做resource来处理了
		========================================================
		不是上边说的那样，问题在于在同一个页面上处理太多请求，但是servlet传过来的list的名字都是一样的，导致取list的时候把存在page域的privilegelist当做resourcelist取了，才导致的这个问题
		========================================================
		很奇怪的错误，即使在request域内取还是出错，必须把所有的list命名不一样才可以，或许不同的功能应该用不同的jsp显示？
		--%>
		<c:forEach var="r" items="${requestScope.resourcelist }">
		<tr>
				<td>${r.uri }</td>
				<td>${r.privilege.name }</td>
				<td>${r.description }</td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=updateResourcePrivilegeUI&id=${r.id}">修改权限</a>
					<a href="${pageContext.request.contextPath }/DeleteServlet?method=deleteResource&id=${r.id}">删除</a>
				</td>
		</tr>
		</c:forEach>
		</table>
	</c:if>
	
	<%-- 添加新资源的页面 --%>
	<c:if test="${addResourceUI!=null }">
	<form action="${pageContext.request.contextPath }/PrivilegeServlet">
		<table width="60%">
			<tr>
				<td>资源uri</td>
				<td>
					<input type="text" name="uri">
					<input type="hidden" name="method" value="addResource">
				</td>
			</tr>
			
			<tr>
				<td>资源描述</td>
				<td>
					<textarea rows="5" cols="50" name="description"></textarea>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td>
					<input type="submit" value="添加资源">
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	
	<c:if test="${updateResourcePrivilegeUI!=null }">
		<table frame="border" width="40%">
			<tr>
				<td>资源URI</td>
				<td>${resource.uri }</td>
			</tr>
			
			<tr>
				<td>资源描述</td>
				<td>${resource.description }</td>
			</tr>
			
			<tr>
				<td>资源原有权限</td>
				<td>${resource.privilege.name }</td>
			</tr>
			
			<tr>
				<td>需要授予资源的权限</td>
				<td>
					<form action="${pageContext.request.contextPath }/PrivilegeServlet" method="post">
						<input type="hidden" name="method" value="updateResourcePrivilege">
						<input type="hidden" name="rid" value="${resource.id }">
						<c:forEach var="p" items="${requestScope.privilegelist }">
							<input type="radio" name="pid" value=${p.id }>${p.name }<br/>
						</c:forEach>
						<input type="submit" value="授权">
					</form>
				</td>
			</tr>
		</table>
	
	</c:if>
	
	<%-- 列出所有角色的页面--%>
	<c:if test="${allrole!=null }">
		<table width="60%">
			<tr>
				<td></td>
				<td></td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=addRoleUI">添加角色</a>
				</td>
				<td>${message}</td>
			</tr>
		</table>
		<table width="60%" frame="border">
			<tr>
				<td>角色名称</td>
				<td>角色描述</td>
				<td>角色权限</td>
				<td>操作</td>
			</tr>
		<c:forEach var="role" items="${requestScope.rolelist }">
		<tr>
				<td>${role.name }</td>
				<td>${role.description }</td>
				<td>
					<c:forEach var="p" items="${role.privileges}">
						${p.name},
					</c:forEach>
				</td>
				<td>
					<a href="${pageContext.request.contextPath }/PrivilegeServlet?method=updateRolePrivilegeUI&id=${role.id}">修改</a>
					<a href="${pageContext.request.contextPath }/DeleteServlet?method=deleteRole&id=${role.id}">删除</a>
				</td>
		</tr>
		</c:forEach>
		</table>
	</c:if>
	
	<%-- 添加新角色的页面 --%>
	<c:if test="${addRoleUI!=null }">
	<form action="${pageContext.request.contextPath }/PrivilegeServlet">
		<table width="60%">
			<tr>
				<td>角色名称</td>
				<td>
					<input type="text" name="name">
					<input type="hidden" name="method" value="addRole">
				</td>
			</tr>
			
			<tr>
				<td>角色权限</td>
				<td>
					<c:forEach var="p" items="${requestScope.privilegelist }">
						<input type="checkbox" name="pid" value=${p.id }>${p.name }<br/>
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<td>角色描述</td>
				<td>
					<textarea rows="5" cols="50" name="description"></textarea>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td>
					<input type="submit" value="添加角色">
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	
	
	<%-- 更新角色权限的页面--%>
	<c:if test="${updateRolePrivilegeUI!=null }">
	<table frame="border" width="80%">
			<tr>
				<td>角色名称</td>
				<td>${role.name }</td>
			</tr>
			
			<tr>
				<td>角色描述</td>
				<td>${role.description }</td>
			</tr>
			
			<tr>
				<td>角色原有权限</td>
				<td>
					<c:forEach var="p" items="${role.privileges}">
						${p.name},
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<td>需要授予角色的权限</td>
				<td>
					<form action="${pageContext.request.contextPath }/PrivilegeServlet" method="post">
						<input type="hidden" name="method" value="updateRolePrivilege">
						<input type="hidden" name="roleid" value="${role.id }">
						<c:forEach var="p" items="${requestScope.privilegelist }">
							<input type="checkbox" name="pid" value=${p.id }>${p.name }<br/>
						</c:forEach>
						<input type="submit" value="授权">
					</form>
				</td>
			</tr>
		</table>
	</c:if>
	
	<%-- 更新用户角色的页面--%>
	<c:if test="${updateUserRoleUI!=null }">
	${message }
	<table frame="border" width="80%">
			<tr>
				<td>用户名</td>
				<td>${user.username }</td>
			</tr>
			
			<tr>
				<td>用户昵称</td>
				<td>${user.nickname }</td>
			</tr>
			
			<tr>
				<td>用户原角色</td>
				<td>
					<c:forEach var="role" items="${user.roles}">
						${role.name},
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<td>用户原有权限</td>
				<td>
					<c:forEach var="p" items="${userplist}">
						${p.name},
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<td>需要授予用户的角色</td>
				<td>
					<form action="${pageContext.request.contextPath }/PrivilegeServlet" method="post">
						<input type="hidden" name="method" value="updateUserRole">
						<input type="hidden" name="userid" value="${user.id }">
						<c:forEach var="role" items="${requestScope.rolelist }">
							<input type="checkbox" name="roleid" value=${role.id }>${role.name }<br/>
						</c:forEach>
						<input type="submit" value="授权">
					</form>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>