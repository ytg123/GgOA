<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>密码修改</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
	.gred{color:#f00;margin-left:5px;}
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
					<p><label>原始密码:</label><input type="password" id="odlPsd" placeholder="请输入原始密码..." class="form-control"/><span class="gred">*</span></p>
					<p><label>新&nbsp;&nbsp;密&nbsp;&nbsp;码:</label><input type="password" id="newPsd" placeholder="请输入新密码..." class="form-control"/><span class="gred">*</span></p>
					<p><label>确认密码:</label><input type="password" placeholder="确认新密码..." id="okNewPsd" class="form-control"/><span class="gred">*</span></p>
					<a href="javascript:;" id="save" class="btn btn-success">保存</a>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#save").on("click",function(){
				var oldPsd = $("#odlPsd").val(),
					newPsd = $("#newPsd").val(),
					okNewPsd = $("#okNewPsd").val(),
					reg = /^(\w){6,20}$/;
					if(oldPsd == null || !reg.test(oldPsd)){
						alert("原始密码不能空,且应为字母、数字、下划线,长度在6到20之间");
						$("#odlPsd").focus();
					}else if(newPsd == null || !reg.test(newPsd)){
						alert("新密码不能空,且应为字母、数字、下划线,长度在6到20之间");
						$("#newPsd").focus();
					}else if(okNewPsd == null || !reg.test(okNewPsd)){
						alert("确认密码不能空,且应为字母、数字、下划线,长度在6到20之间");
						$("#okNewPsd").focus();
					}else if(newPsd != okNewPsd){
						alert("两次输入的密码不一致,请您重新输入新密码!");
						$("#newPsd").focus();
						$("#newPsd").val("");
						$("#okNewPsd").val("");
					}else{
						$.ajax({
							type:"post",
							url:"<%=path %>/sysmanager/user/savePsd",
							dataType:"json",
							data:{
								odlPsd:oldPsd,
								newPsd:newPsd
							},
							success:function(data){
								alert(data.result);
							},
							error:function(data){
								alert(data.responseText);
							}
						});
					}
				
			});
		});
	
	</script>
</body>
</html>