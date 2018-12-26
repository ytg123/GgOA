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
<title>部门管理</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/sg/css/sg.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/treeTable/themes/default/treeTable.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/treeTable/jquery.treeTable.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
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
				<table id="treeTable" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="text-center">名称</th>
							<th class="text-center">编号</th>
							<th class="text-center">排序</th>
							<th class="text-center">负责人</th>
							<th class="text-center">电话</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deptList}" var="dept">
							<tr id="${dept.id}" pId="${dept.parentId}" class="text-center">										 
								<td>${dept.name}</td>
								<td>${dept.code}</td>
								<td>${dept.sort}</td>
								<td>${dept.master}</td>
								<td>${dept.phone}</td>	
								<td nowrap>
									<a href="<%=path%>/sysmanager/dept/updateDept?id=${dept.id}">修改</a>
									<a href="javascript:;" data-id="${dept.id  }" class="deleteD">删除</a>
									<a href="<%=path%>/sysmanager/dept/addDept?parentId=${dept.id}">添加下级部门</a>
								</td>		 
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//treeTable初始化
		$("#treeTable").treeTable({
	        expandLevel : 5
		}).show();
		//删除
        $(".deleteD").on("click",function(){
        	let uid = $(this).data("id");
        		that = $(this);
			$.tzAlert({
				content:"您确定要删除吗？",
				callback:function(ok){
					 if(ok){
						 $.ajax({
							type:"get",
							url:"<%=path%>/sysmanager/dept/deleteDept",
							async:true,
							dataType:"json",
							data:{
								id:uid
							},
							success:function(data){
								if(data.result == 1){
									that.parents("tr").fadeOut(1000,function(){
										$(this).remove();
									});
								}else{
									loading(data.result,2);
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
	</script>
</body>
</html>