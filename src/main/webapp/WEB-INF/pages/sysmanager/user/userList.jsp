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
<title>用户管理</title>
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
	.table{margin-top:30px;}
	#userPageInfo ul{width:100%;height:auto;margin:0 auto;}
	#userPageInfo ul li{width:auto;height:auto;float:left;}
	#userPageInfo ul li a{padding:7px 12px;border-radius:5px;background:#E1E6F6;color:#333;margin:0 5px;}
	#userPageInfo ul li a input{width:30px;height:30px;border-radius:5px;background:#E1E6F6;margin: -3px 8px;text-align:center;}
	#userPageInfo ul li a:hover{text-decoration:none;background:#3A95FF!important;color:#fff;}
</style>
</head>
<body>
	<!-- 头部 -->
	<%@ include file="../../home/header.jsp" %>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="../../home/side.jsp"></jsp:include>
			</div>
			<div class="col-md-9">
				<div class="row">
					<form class="form-inline">
						<div class="col-md-5" style="position:relative;">
							<label>部门名称:</label>
							<input id="deptId" name="deptId"  type="hidden" value=""/>
							<input id="deptName" name="deptName" class="form-control" readonly="readonly" type="text" value="" />
							<a href="javascript:showUserDept();"  class="btn btn-default">搜索</a>
							<div id="userDeptContent"  style="display:none; position: absolute; background: #f0f6e4;z-index:100;">
								<ul id="userDeptTree" class="ztree" style="margin-top:0; width:260px;"></ul>
							</div>
						</div>
						
						<div class="col-md-5">
							<label>用户名称:</label>
							<input id="userName" name="userName" class="form-control" type="text" value="" maxlength="50"/>
						</div>
						<div class="col-md-1">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="getUserList(1,10)"/>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="table table-striped table-hover table-bordered table-responsive">
						  <thead>
						    <tr>
						    	<th class="text-center">序列号</th>
						        <th class="text-center">用户名称</th>
						        <th class="text-center">登陆名称</th>
						        <th class="text-center">所属部门</th>
						        <th class="text-center">电话</th>
						        <th class="text-center">手机</th>
						        <th class="text-center">邮箱</th>
						        <th class="text-center">操作</th>
						    </tr>
						  </thead>
						  <tbody id="tb">
						    
						  </tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="pagination" id = "userPageInfo">

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//加载动画
	loading("数据正在加载中...",3);
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
			$("#userDeptContent").css({left:"65px", top:"33px"}).slideDown("fast");
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
				tree.expandAll(true);
		    });
		   	
		});
		//获取用户列表
		function getUserList(pageNo,pageSize){
			var deptId = $("#deptId").val(),
			userName = $("#userName").val();
			$.ajax({
				type:"post",
				url:"<%=path%>/sysmanager/user/getUserList",
				dataType:"json",
				data: {"deptId":deptId,"userName":userName,"pageNo":pageNo,"pageSize":pageSize},
				success:function(data){
					var html = null;
					var pageStr = data.pageStr;
					var data = data.userList;
					if(data != null && data.length > 0){
						$("#tb").html("");
						for(var i=0,len=data.length;i<len;i++){
							html += "<tr class='text-center'>"+
							"<td>"+(i+1)+"</td>"+
				  			"<td>"+data[i].userName+"</td>"+
				  			"<td>"+data[i].loginName+"</td>"+
				  			"<td>"+data[i].deptName+"</td>"+
				  			"<td>"+data[i].phone+"</td>"+
				  			"<td>"+data[i].mobile+"</td>"+
				  			"<td>"+data[i].email+"</td>"+
				  			"<td>"+
				  				"<a href='javascript:;' onclick='deletes(this)' data-id='"+data[i].userId+"'>删除</a>"+
				  				"<a href='<%=path%>/sysmanager/user/userUpdateEdit?userId="+data[i].userId+"'>修改</a>"+
				  				"<a href='<%=path%>/sysmanager/user/userAddEdit'>增加</a>"+
				  			"</td>"+
				  			"</tr>";
						}
						$("#tb").append(html);
						//取分页代码
						$("#userPageInfo").html(pageStr);
					}else{
						$("#tb").html("<tr class='text-center'><td colspan='8' class='text-danger'>狠抱歉,没有查到数据哦!</td></tr>");
					}
				},
				error:function(data){
					alert(data.responseText);
				}
				
			});
		}
		//删除
		function deletes(obj){
			let uid = $(obj).data("id");
			$.tzAlert({
				content:"您确定要删除吗？",
				callback:function(ok){
					 if(ok){
						 $.ajax({
							type:"get",
							url:"<%=path%>/sysmanager/user/deleteUser",
							async:true,
							dataType:"json",
							data:{
								userId:uid
							},
							success:function(data){
								if(data.result == 1){
									$(obj).parents("tr").fadeOut(1000,function(){
										$(this).remove();
									});
								}
							},
							error:function(data){
								alert(data.responseText);
							}
						});
					}
				}
			});
		}
	</script>
</body>
</html>