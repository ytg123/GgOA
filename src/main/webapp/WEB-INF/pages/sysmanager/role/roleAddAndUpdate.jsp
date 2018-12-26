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
<link  rel="stylesheet" type="text/css" href="<%=path%>/static/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<style type="text/css">
	.form-control{width:95%;display:inline-block;}
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
			<div class="col-md-6 col-md-offset-1">
				<form class="form-horizontal" id="roleEditForm">
					<input type="hidden" name="id" value="${role.id }"/>
					<div class="form-group">
						<label>名称:</label>
						<input type="text" name="name" value="${role.name }" class="form-control"/>
						<span style="color:#f00;">*</span>
					</div>
					<div class="form-group">
						<label>备注:</label>
						<textarea name="remarks" class="form-control" style="resize:none;">${role.remarks }</textarea>
					</div>
					<div class="form-group">
						<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
		 				<div id="deptTree" class="ztree" style="margin-left:50px;margin-top:3px;float:left;"></div>
		 				<div id="areaTree" class="ztree" style="margin-left:50px;margin-top:3px;float:left;"></div>
					</div>
					<a href="javascript:;" id="saveRole" class="btn btn-success">保存</a>
					<a href="javascript:history.go(-1);" class="btn btn-default">返回</a>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			
			//菜单资源树
			var menuSetting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						menuTree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var menuZNodes=[
					<c:forEach items="${menuList}" var="menu">
					{
						id: "${menu.id}",
						pId: "${menu.parentId}", 
						name: "${menu.name}"
					},
					</c:forEach>
		            ];
			// 初始化树结构
			var menuTree = $.fn.zTree.init($("#menuTree"), menuSetting, menuZNodes);
			//进入修改页面的时候 ，选中已经拥有资源的节点	
			<c:forEach items="${roleMenuList}" var="rm">
				var node = menuTree.getNodeByParam("id",${rm.menuId});
				menuTree.checkNode(node,true,false);
			</c:forEach>
			
			//默认展开全部节点
			menuTree.expandAll(true);
			
			
			//部门资源树
			var deptSetting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						deptTree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-部门
			var deptZNodes=[
					<c:forEach items="${deptList}" var="dept">
					{
						id: "${dept.id}",
						pId: "${dept.parentId}", 
						name: "${dept.name}"
					},
					</c:forEach>
		            ];
			// 初始化树结构
			var deptTree = $.fn.zTree.init($("#deptTree"), deptSetting, deptZNodes);
			//进入修改页面的时候 ，选中已经拥有资源的节点	
			<c:forEach items="${roleDeptList}" var="roleDept">
				var node = deptTree.getNodeByParam("id",${roleDept.deptId});
				deptTree.checkNode(node,true,false);
			</c:forEach>
			//默认展开全部节点
			deptTree.expandAll(true);
			
			
			//区域资源树
			var areaSetting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						areaTree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-区域
			var areaZNodes=[
					<c:forEach items="${areaList}" var="area">
					{
						id: "${area.id}",
						pId: "${area.parentId}", 
						name: "${area.name}"
					},
					</c:forEach>
		            ]; 
			// 初始化树结构
			var areaTree = $.fn.zTree.init($("#areaTree"), areaSetting, areaZNodes);
			//进入修改页面的时候 ，选中已经拥有资源的节点	
			<c:forEach items="${roleAreaList}" var="roleArea">
				var node = areaTree.getNodeByParam("id",${roleArea.areaId});
				areaTree.checkNode(node,true,false);
			</c:forEach>
			//默认展开全部节点
			areaTree.expandAll(true);
			$("#saveRole").on('click',function(){
				
				var formObject = {};
				var formArray =$("#roleEditForm").serializeArray();
				$.each(formArray, function(i, item){
					formObject[item.name]=item.value;
				 });

				var roleDto = {};
				roleDto["role"]=formObject;
			 	//获取菜单树被选中的节点
			 	var menuObj = {};
			 	var menuNodes = menuTree.getCheckedNodes(true);
			 	$.each(menuNodes, function(i, item){
			 		menuObj[item.id]=item.id;
				 });
			 	roleDto["menuIds"]=menuObj;
			 	

			 	//获取部门树被选中的节点
			 	var deptObj = {};
			 	var deptNodes = deptTree.getCheckedNodes(true);
			 	$.each(deptNodes, function(i, item){
			 		deptObj[item.id]=item.id;
				 });
				roleDto["deptIds"]=deptObj;
			 	
			 	

			 	//获取区域树被选中的节点
			 	var areaObj = {};
			 	var areaNodes = areaTree.getCheckedNodes(true);
			 	$.each(areaNodes, function(i, item){
			 		areaObj[item.id]=item.id;
				 });
			 	var areaIds = JSON.stringify(areaObj);
				roleDto["areaIds"]=areaObj;
				var url = location.href;
				if(url.indexOf("goAddRole")>0){
					//增加
					$.ajax({
						type:'post',
						url:"<%=path%>/sysmanager/role/AddRole",
						dataType:'json',
						contentType :"application/json;charset=UTF-8",
						data:JSON.stringify(roleDto),
						success:function(data){
							alert(data.result);
							history.go(-1);
						},
						error:function(data){
							alert(data.responseText);
						}
						
					});
				}else{
					//修改
					$.ajax({
						type:'post',
						url:"<%=path%>/sysmanager/role/updateRole",
						dataType:'json',
						contentType :"application/json;charset=UTF-8",
						data:JSON.stringify(roleDto),
						success:function(data){
							alert(data.result);
							history.go(-1);
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