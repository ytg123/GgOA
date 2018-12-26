<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<meta charset="utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/style.css" />
	</head>
	<body>
		<!-- login S -->
		<div id="login">
			<div class="message">
				广广之家OA管理系统
			</div>
			<div id="darkbannerwrap"></div>
			<form action="<%=path%>/loginUser" method="post" id="formy">
				<input type="text" name="loginName" id="loginName"  placeholder="loginName..." />
				<hr class="hr15"/>
				<input type="password" name="password" id="password" placeholder="password..." />
				<hr class="hr15"/>
				<a href="javascript:;" class="submit" id="submit">登录</a>
				<hr class="hr20"/>
			</form>
			<!-- 错误提示语 -->
			<c:if test="${loginErr != null}">	
				<p class="error">
					${loginErr}
				</p>
			</c:if>
		</div>
		<!-- E login -->
		
		<script type="text/javascript">
				window.onkeyup = function(e){
					var ev = e || window.event;
					if(ev.keyCode == 13){
						var name = document.getElementById("loginName").value,
							pwd = document.getElementById("password").value,
							reg = /^(\w){6,20}$/;
						if(name == null){
							alert("用户名不能为空!");
						}else if(!reg.test(pwd)){
							alert("密码应为字母、数字、下划线,长度在6到20之间");
						}else{
							document.getElementById("formy").submit();
						}
					}
				}
				document.getElementById("submit").onclick = function(){
					var name = document.getElementById("loginName").value,
						pwd = document.getElementById("password").value,
						reg = /^(\w){6,20}$/;
					if(name == null){
						alert("用户名不能为空!");
					}else if(!reg.test(pwd)){
						alert("密码应为字母、数字、下划线,长度在6到20之间");
					}else{
						document.getElementById("formy").submit();
					}
				}
			
		</script>
	</body>
</html>