<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/sg/css/sg.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/static/sg/sg.js"></script>
<script src="<%=path %>/static/sg/tz_util.js"></script>
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
			<div class="col-md-9">
				<table class="table table-striped table-hover table-bordered">
				  <caption class="text-center">角色管理</caption>
				  <thead>
				    <tr>
				      <th class="text-center">名称</th>
				      <th class="text-center">描述</th>
				      <th class="text-center">操作</th>
				    </tr>
				  </thead>
				  <tbody>
					<c:forEach items="${RoleList }" var="role">  
					    <tr class="text-center">
					      <td>${role.name }</td>
					      <td>${role.remarks }</td>
					      <td>
					      	<a href="javascript:;" class="delRole" data-id="${role.id }">删除</a>
					      	<a href="<%=path %>/sysmanager/role/goUpateRole?roleId=${role.id}">修改</a>
					      	<a href="<%=path %>/sysmanager/role/goAddRole">添加</a>
					      </td>
					    </tr>
					</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			  //删除
		        $(".delRole").on("click",function(){
		        	let uid = $(this).data("id");
		        		that = $(this);
					$.tzAlert({
						content:"您确定要删除吗？",
						callback:function(ok){
							 if(ok){
								 $.ajax({
									type:"get",
									url:"<%=path%>/sysmanager/role/delRole",
									async:true,
									dataType:"json",
									data:{
										roleId:uid
									},
									success:function(data){
										if(data.result == 1){
											that.parents("tr").fadeOut(1000,function(){
												$(this).remove();
											});
										}else{
											alert(data.result);
										}
									},
									error:function(data){
										alert(data.responseText);
									}
								});
							}
						}
					});
		        	
		        });
		})
	</script>
</body>
</html>