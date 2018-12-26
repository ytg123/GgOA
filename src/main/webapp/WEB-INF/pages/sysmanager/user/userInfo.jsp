<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人信息</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
	#remarks{resize:none;}
</style>
</head>
<body>
	<!-- 头部 -->
	<%@ include file="../../home/header.jsp" %>
	<!-- 内容 -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="../../home/side.jsp"></jsp:include>
			</div>
			<div class="col-md-6 col-md-offset-2">
				<form class="form-inline">
					<input type="hidden"  value="${userDto.userId }" id="userId"/>
					<p><label>所属部门:</label><input type="text" readonly="readonly" value="${userDto.deptName }" class="form-control"/></p>
					<p><label>登陆名称:</label><input type="text" readonly="readonly" value="${userDto.loginName }" class="form-control"/></p>
					<p><label>用户名称:</label><input type="text" value="${userDto.userName }" class="form-control" id="userName"/></p>
					<p><label>工号:</label><input type="text" value="${userDto.userNo }" class="form-control" id="userNo"/></p>
					<p><label>邮箱:</label><input type="email" value="${userDto.email }" class="form-control" id="email"/></p>
					<p><label>电话:</label><input type="number" value="${userDto.phone }" class="form-control" id="phone"/></p>
					<p><label>手机:</label><input type="number" value="${userDto.mobile }" class="form-control" id="mobile"/></p>
					<p>
						<label>备注:</label><textarea  class="form-control" id="remarks">${userDto.remarks }</textarea>
					</p>
					<p>
						<label class="control-label">用户角色:</label>
						<c:forEach items="${roleList }" var="urole">
							<span>${urole.name }</span>
						</c:forEach>	
					</p>
					<a href="javascript:;" class="btn btn-primary" id="saveUserInfo">保存</a>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#saveUserInfo").on("click",function(){
				var userId = $("#userId").val(),
				userName = $("#userName").val(),
				userNo = $("#userNo").val(),
				email = $("#email").val(),
				phone = $("#phone").val(),
				mobile = $("#mobile").val(),
				remarks = $("#remarks").val(),
				json = {
					userId:userId,
					userName:userName,
					userNo:userNo,
					email:email,
					phone:phone,
					mobile:mobile,
					remarks:remarks
				};
				var user = JSON.stringify(json);
				if(userName == ""){
					alert("用户名不能为空!");
				}else if(userNo == ""){
					alert("工号不能为空!");
				}else if(email == ""){
					alert("邮箱不能为空!");
				}else if(phone == ""){
					alert("电话不能为空!");
				}else if(mobile == ""){
					alert("手机不能为空!");
				}else if(remarks == ""){
					alert("备注不能为空!");
				}else{
					$.ajax({
						type:"post",
						url:"<%=path%>/sysmanager/user/saveUserInfo",
						dataType:"json",
						contentType :"application/json;charset=UTF-8",
						data:user,
						success:function(data){
							alert(data.result);
						},
						error:function(data){
							alert(data.responseText);
						}
					});
				}
			});
		})
	</script>
</body> 
</html>