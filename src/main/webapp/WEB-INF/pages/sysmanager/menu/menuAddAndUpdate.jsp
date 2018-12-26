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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<style type="text/css">
	.form-group{margin:15px;}
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
			<div class="col-md-9">
				<form class="form-inline" id="menuEdit">
					 <div class="form-group">
					    <input id="id" name="id" type="hidden" value="${menu.id}"/>
					    <label>上级菜单:</label>
					    <div class="input-group">
					    	<input type="text" name="parentId" class="form-control" id="parentName" readonly="readonly" value="${menu.parentName}" placeholder="请输入名称" style="width:76%;">
					    	<input id="parentId" name="parentId" class="required" type="hidden" value="${menu.parentId}"/>
					    	<a href="javascript:showMenu();"  class="btn btn-default">搜索</a>
					    </div>
					    <div id="menuContent"  style="display:none; position: absolute; background: #f0f6e4;">
							<ul id="menuTree" class="ztree" style="margin-top:0; width:232px;"></ul>
						</div>
					  </div>
					  <div class="form-group">
					    <label>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label>
					    <input type="text" name="name"  class="form-control" id="mpian" value="${menu.name}" placeholder="请输入名称">
					  </div>
					  <div class="form-group">
					    <label>链&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接:</label>
					    <input type="text" name="href" class="form-control" id="hrefs" placeholder="请输入连接" value="${menu.href}">
					    <sub>点击菜单跳转的页面</sub>
					  </div>
					  <div class="form-group">
					    <label>目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标:</label>
					    <input type="text" name="target" class="form-control" id="shut" value="${menu.target}" placeholder="请输入目标">
					    <sub>链接地址打开的目标窗口，默认：mainFrame</sub>
					  </div>
					  <div class="form-group">
					    <label>图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标:</label>
					    <input type="text" name="icon" class="form-control" id="tbiao" placeholder="请输入图标" value="${menu.icon}">
					  </div>
					  <div class="form-group">
					    <label>排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label>
					    <input type="text" name="sort" class="form-control" id="sorts" value="${menu.sort}" placeholder="请输入名称">
					  </div>
					  <div class="form-group">
					    <label>可&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;见:</label>
					   <label for="showme"> <input type="radio" class="form-control" id="showme" name="isShow" value="1"  <c:if test="${menu.isShow==1}">checked</c:if>/>显示</label>
					    <label for="hideme"><input type="radio" class="form-control" id="hideme" name="isShow" value="0" <c:if test="${menu.isShow==0}">checked</c:if>/>隐藏</label>
					    <sub>该菜单或操作是否显示到系统菜单中</sub>
					  </div>
					   <div class="form-group">
					    <label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
					   	<textarea class="form-control" name="remarks" id="remarkss">${menu.remarks}</textarea>
					  </div>
					  <a href="javascript:saveMenu();" class="btn btn-success">保存</a>
					  <a href="javascript:history.back();" class="btn btn-default">返回</a>
				</form>
			</div>
		</div>
</div>
	<script type="text/javascript">
		//ztree的设置
		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey:"id",
					pIdKey:"parentId",
					rootPId:0
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		}; 
		function beforeClick(treeId, treeNode) {
			/* var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择城市...");
			return check; */
		};
		
		//点击树节点 时将选中节点返回给页面
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			var nodes = zTree.getSelectedNodes();
			var nodeName = nodes[0].name;
			var nodeId = nodes[0].id;
 			$("#parentId").attr("value", nodeId);
			$("#parentName").attr("value", nodeName);
			//为使得用户体验更加，选中节点后，影藏数结构
			hideMenu() ;
		};
		//点击搜索框，显示树
		function showMenu() {
			$("#menuContent").css({left:"95px", top:"50px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		};
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		};
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		};
		$(document).ready(function(){
			//此地方要稍微注意下，考虑不将本身以及下级节点显示出来，怕选择错误，造成连环套
			var menuId = $("#id").val();
			$.get("<%=path%>/sysmanager/menu/getParentMenuTreeData?menuId="+menuId,
					function(zNodes){
						//初始化数结构
						//var jsonObj= $.parseJSON(zNodes.jsonObj);
						var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
						//默认展开节点
						var nodes = tree.getNodesByParam("level",2);
						for(var i=0;i<nodes.length;i++){
							tree.expandNode(nodes[i],true,false,false);
						}
						//如果是进入修改页面，定位到当前选中的节点
						var selectNodeId = $("#parentId").val();
						if(selectNodeId!=null){
							tree.selectNode(tree.getNodeByParam("id",selectNodeId,null));
						}
			});
		});
		var urime = location.href;
		if(urime.indexOf("updateMenu")>0){
			//修改
			function saveMenu(){
				var url = '<%=path%>/sysmanager/menu/updatedMenu';
				menuInfo(url);
			}
		}else{
			//增加
			function saveMenu(){
				var url = '<%=path%>/sysmanager/menu/addedMenu';
				menuInfo(url);
			}
		}
		//增加和修改函数封装
		function menuInfo(url){
			var formObject = {};
			var formArray =$("#menuEdit").serializeArray();
			$.each(formArray, function(i, item){
				formObject[item.name]=item.value;
			 });
		 	var jsonObj = JSON.stringify(formObject);
		 	$.ajax({
				type:'post',//请求方式
				url:url, 
				contentType :"application/json;charset=UTF-8",
				dataType:'json', //返回数据的几种格式 xml html json text 等常用
				//data传值的另外一种方式 form的序列化
				data: jsonObj,//传递给服务器的参数					
				success:function(data){//与服务器交互成功调用的回调函数
					alert(data.result);
					//返回列表页
					history.back();
				},
				error:function(data){
					alert(data.responseText);
				}
			});
		}
	</script>
</body>
</html>