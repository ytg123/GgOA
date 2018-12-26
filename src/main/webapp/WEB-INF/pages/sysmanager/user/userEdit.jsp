<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户编辑页</title>
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
<style type="text/css">
	#remarks{resize:none;}
	.form-inline .form-group{display:block;margin-top:15px;position:relative;}
	.btn{margin-top:8px;}
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
				<form class="form-inline" id="userEditForm">
					<input id="userId" name="userId" type="hidden" value="${userDto.userId}"/>
					<div class="form-group">
						<label>部门名称:</label>
						<input id="deptId" name="deptId"  type="hidden" value="${userDto.deptId}"/>
						<input id="deptName" name="deptName"  class="form-control" readonly="readonly"  type="text" value="${userDto.deptName}" />
						<a href="javascript:showUserDept();" class="btn btn-default" style="margin-top:0;">搜索</a>
						<div id="userDeptContent"  style="display:none; position: absolute; background: #f0f6e4;z-index:100;">
							<ul id="userDeptTree" class="ztree" style="margin-top:0; width:260px;"></ul>
						 </div>
					</div>
					 
					 <div class="form-group">
					 	<label>工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
					 	<input type="number" id="userNo" name="userNo" class="form-control"  value="${userDto.userNo}" maxlength="50"/>
					 	<span style="color:#f00;">*</span>
					 </div>
					 <div class="form-group">
					 	<label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
					 	<input type="text" id="userName" name="userName" class="form-control" value="${userDto.userName}" maxlength="50"/>
					 	<span style="color:#f00;">*</span>
					 </div>
					 <div class="form-group">
					 	<label>登&nbsp;&nbsp;录&nbsp;&nbsp;名:</label>
					 	<input type="text" id="loginName" name="loginName" class="form-control"  value="${userDto.loginName}" maxlength="50"/>
					 	<span style="color:#f00;">*</span>
					 </div>
					 <div class="form-group">
					 	<label>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
					 	<input type="email" id="email" name="email" class="form-control"  value="${userDto.email}" maxlength="50"/>
					 	<span style="color:#f00;">*</span>
					 </div>
					 <div class="form-group">
					 	<label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</label>
					 	<input type="number" id="phone" name="phone" class="form-control"  value="${userDto.phone}" maxlength="50"/>
					 </div>
					 <div class="form-group">
					 	<label>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机:</label>
					 	<input type="number" id="mobile" name="mobile" class="form-control"  value="${userDto.mobile}" maxlength="50"/>
					 </div>
					 <div class="form-group">
					 	<label>用户角色:</label>
					 	<c:forEach items="${roleList}" var="role">				 			
							<span>						
								<input id="roleId${role.id}" name="roleIdList"  type="checkbox" 
								<c:if test="${roleCheckMap[role.id] == role.id}">checked</c:if>  value="${role.id}"/>
								<label for="roleId${role.id}">${role.name}</label>
							</span>
							 
						</c:forEach>
					 	<span style="color:#f00;">*</span>
					 </div>
					 <div class="form-group">
					 	<label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
					 	<textarea id="remarks" name="remarks" class="form-control">${userDto.remarks}</textarea>
					 </div>
					 <a href="javascript:;" class="btn btn-success" id="saveUserInfo">保存</a>
					 <a href="javascript:history.go(-1);" class="btn btn-default">返回</a>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId: 0
				}
			},
			callback: {
	 			onClick: onClick 
			}
		};
	 
		function onClick(e, treeId, treeNode) {
			//树上元素的点击事件我们需要做几件事件，需要将点击的元素赋值给我们指定的元素  然后点击完隐藏树结构
			var zTree = $.fn.zTree.getZTreeObj("userDeptTree");
			var node = zTree.getSelectedNodes();
			var nodeName = node[0].name;
			var nodeId = node[0].id;
			//alert(nodeId);
			$("#deptId").attr("value", nodeId);
			$("#deptName").attr("value", nodeName);
			hideMenu();
			 
		}
	 
		function showUserDept() {
	 		var deptNameObj = $("#deptName");
			var deptNameOffset = $("#deptName").offset();
			$("#userDeptContent").css({left:"55px", top:"35px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#userDeptContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "userDeptBtn" || event.target.id == "userDeptContent" || $(event.target).parents("#userDeptContent").length>0)) {
				hideMenu();
			}
		}
		$(document).ready(function(){
			$.get("<%=path%>/sysmanager/dept/getAllDeptList", function (zNodes) {
		        // 初始化树结构
				var tree = $.fn.zTree.init($("#userDeptTree"), setting, zNodes);
				var selectNodeId =$("#deptId").val();
		        if(selectNodeId!=null){
		        	tree.selectNode(tree.getNodeByParam("id",selectNodeId, null)); 
		        }	
				tree.expandAll(true);        
	
		    })
		});
	
	
	
		$(function(){
			$("#saveUserInfo").on("click",function(){
				//获取用户信息数据,组装成json字符串
				var formObject = {};
				var formArray =$("#userEditForm").serializeArray();
				$.each(formArray, function(i, item){
					formObject[item.name]=item.value;
				 });
				var userVo = {};
				userVo["user"]=formObject;
			 	//获取复选框的值
			 	var roleCheckObj = {};
			 	var roleIdArray = $("input[name='roleIdList']:checked").serializeArray();
	 			$.each(roleIdArray, function(i, item){
	 				roleCheckObj[item.value]=item.value;
				 });
				userVo["roleIds"]=roleCheckObj;
				var url = location.href;
				if(url.indexOf("userAddEdit")>0){
					$.ajax({
						type:"post",
						url:"<%=path%>/sysmanager/user/saveUserDtoInfo",
						dataType:"json",
						contentType :"application/json;charset=UTF-8",
						data:JSON.stringify(userVo),
						success:function(data){
							alert(data.result);
						},
						error:function(data){
							alert(data.responseText);
						}
					});
				}else{
					$.ajax({
						type:"post",
						url:"<%=path%>/sysmanager/user/updateUserDtoInfo",
						dataType:"json",
						contentType :"application/json;charset=UTF-8",
						data:JSON.stringify(userVo),
						success:function(data){
							alert(data.result);
						},
						error:function(data){
							alert(data.responseText);
						}
					});
				}
				
			})
		})
	</script>
</body> 
</html>