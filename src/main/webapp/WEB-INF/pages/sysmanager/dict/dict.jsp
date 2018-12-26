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
<title>字典管理</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/sg/css/sg.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/static/sg/sg.js"></script>
<script src="<%=path %>/static/sg/tz_util.js"></script>
<style type="text/css">
	.form-group{margin:15px;}
	.form-group #remarksz{resize:none;}
	.text-danger{color:#f00;}
	#pageMenation ul{width:100%;height:auto;margin:0 auto;}
	#pageMenation ul li{width:auto;height:auto;float:left;}
	#pageMenation ul li a{padding:7px 12px;border-radius:5px;background:#E1E6F6;color:#333;margin:0 5px;}
	#pageMenation ul li a input{width:30px;height:30px;border-radius:5px;background:#E1E6F6;margin: -3px 8px;text-align:center;}
	#pageMenation ul li a:hover{text-decoration:none;background:#3A95FF!important;color:#fff;}
	.disabled{readonly:true;}
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
				<div class="row">
					<form class="form-inline">	
						<div class="col-md-4">
							<label>类型:</label>
							<select class="form-control" id="type">
								<option value="">所有类型</option>
						     	<c:forEach items="${dictTypeList}" var="dtls">
						     		<option value="${dtls }">${dtls }</option>
						     	</c:forEach>
						    </select>
						</div>
						<div class="col-md-4">
							<label>描述:</label><input type="text" placeholder="请输入描述..." id="description" class="form-control"/>
						</div>
						<div class="col-md-1">
							<a class="btn btn-success" id="query">查询</a>
						</div>
					</form>
				</div>
				<div class="row" style="margin-top:30px;">
					<div class="col-md-12">
						<table class="table table-bordered table-hover">
						  <thead>
						    <tr>
						    	<th class="text-center">序号</th>
						        <th class="text-center">键值</th>
						        <th class="text-center">标签</th>
						        <th class="text-center">类型</th>
						        <th class="text-center">描述</th>
						        <th class="text-center">排序</th>
						        <th class="text-center">操作</th>
						    </tr>
						  </thead>
						  <tbody id="tb">
						  </tbody>
						</table>
					</div>
					<!-- 增加和修改 -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					                <h4 class="modal-title text-center" id="myModalLabel"></h4>
					            </div>
					            <div class="modal-body">
					            	<form class="form-inline" id="dictForm">
					            		  <input type="hidden" id="id"/>
					            		  <div class="form-group">
										    <label>键值:</label>
										    <input type="text" class="form-control" id="jz" placeholder="请输入键值...">
										    <span class="text-danger">*</span>
										  </div>
					            		  <div class="form-group">
										    <label>标签:</label>
										    <input type="text" class="form-control" id="labels" placeholder="请输入标签...">
										  	<span class="text-danger">*</span>
										  </div>
										  <div class="form-group">
										    <label>类型:</label>
										    <input type="text" class="form-control" id="types" placeholder="请输入类型...">
										  	<span class="text-danger">*</span>
										  </div>
										  <div class="form-group">
										    <label>描述:</label>
										    <input type="text" class="form-control" id="descs" placeholder="请输入描述...">
										  	<span class="text-danger">*</span>
										  </div>
										  <div class="form-group">
										    <label>排序:</label>
										    <input type="text" class="form-control" id="sorts" placeholder="请输入排序...">
										  	<span class="text-danger">*</span>
										  </div>
										  <div class="form-group">
										    <label>备注:</label>
										    <textarea class="form-control" placeholder="请输入备注..." id="remarksz"></textarea>
										  </div>
					            	</form>
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-primary" id="saveDict">保存</button>
					            </div>
					        </div><!-- /.modal-content -->
					    </div><!-- /.modal -->
					</div>
				</div>
			
				<!-- 分页 -->
				<div class="row">
					<div class="col-md-12">
						<div id="pageMenation" class="center-block"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//加载动画
		loading("数据正在加载中...",3);
		$(function(){
			$("#query").on("click",function(){
				getListPage(1,10);
			});
			
		});
		//获取列表
		function getListPage(pageNo,pageSize){
			$.ajax({
				type:"post",
				url:"<%=path%>/sysmanager/dict/queryDictList",
				dataType:"json",
				data:{
					type:$("#type").val(),
					description:$("#description").val(),
					pageNo:pageNo,
					pageSize:pageSize
				},
				success:function(data){
					var html = null;
					var pageStr = data.pageStr;
					var data = data.dictList;
					if(data != null && data.length > 0){
						$("#tb").html("");
						for(var i=0,len=data.length;i<len;i++){
							html += "<tr class='text-center'>"+
							"<td>"+(i+1)+"</td>"+
				  			"<td>"+data[i].value+"</td>"+
				  			"<td>"+data[i].label+"</td>"+
				  			"<td>"+data[i].type+"</td>"+
				  			"<td>"+data[i].description+"</td>"+
				  			"<td>"+data[i].sort+"</td>"+
				  			"<td>"+
				  				"<a href='javascript:;' onclick='deletes(this)' data-id='"+data[i].id+"'>删除</a>"+
				  				"<a href='javascript:;' onclick='updates(this)' data-id='"+data[i].id+"' data-value='"+data[i].value+"' data-label='"+data[i].label+"' data-type='"+data[i].type+"'  data-description='"+data[i].description+"' data-sort='"+data[i].sort+"' data-remarks='"+data[i].remarks+"'>修改</a>"+
				  				"<a href='javascript:;' onclick='adds()'>增加</a>"+
				  			"</td>"+
				  			"</tr>";
						}
						$("#tb").append(html);
						//取分页代码
						$("#pageMenation").html(pageStr);
					}else{
						$("#tb").html("<tr class='text-center'><td colspan='7' class='text-danger'>狠抱歉,没有查到数据哦!</td></tr>");
					}
				},
				error:function(data){
					alert(data.responseText);
				}
			});
		}
		//修改
		function updates(obj){
			$("#myModalLabel").text("字典修改");
			$('#myModal').modal('show');
			//赋值
			$("#id").val($(obj).data("id"));
			$("#jz").val($(obj).data("value"));
			$("#labels").val($(obj).data("label"));
			$("#types").val($(obj).data("type"));
			$("#descs").val($(obj).data("description"));
			$("#sorts").val($(obj).data("sort"));
			$("#remarksz").val($(obj).data("remarks"));
		}
		//增加
		function adds(){
			$("#myModalLabel").text("字典增加");
			$('#myModal').modal('show');
		}
		//增加和修改的保存
		$("#saveDict").on("click",function(){
			var jz = $("#jz").val(),
				id = $("#id").val(),
				labels = $("#labels").val(),
				types = $("#types").val(),
				descs = $("#descs").val(),
				sorts = $("#sorts").val(),
				remarksz = $("#remarksz").val(),
			json = {
				id:id,
				value:jz,
				label:labels,
				type:types,
				description:descs,
				sort:sorts,
				remarks:remarksz
			};
			var dict = JSON.stringify(json);
			if(jz == ""){
				alert("键值不能为空");
			}else if(labels == ""){
				alert("标签不能为空");
			}else if(types == ""){
				alert("类型不能为空");
			}else if(descs == ""){
				alert("描述不能为空");
			}else if(sorts == ""){
				alert("排序不能为空");
			}else if(remarksz == ""){
				alert("备注不能为空");
			}else{
				var text = $("#myModalLabel").text();
				if(text === "字典增加"){
					$.ajax({
						type:"post",
						url:"<%=path%>/sysmanager/dict/addDict",
						dataType:"json",
						contentType:"application/json;charset=UTF-8",
						data:dict,
						success:function(data){
							alert(data.result);
							$('#myModal').modal('hide');
						},
						error:function(data){
							alert(data.responseText);
						}
					});
				}else{
					$.ajax({
						type:"post",
						url:"<%=path%>/sysmanager/dict/updateDict",
						dataType:"json",
						contentType:"application/json;charset=UTF-8",
						data:dict,
						success:function(data){
							alert(data.result);
							$('#myModal').modal('hide');
						},
						error:function(data){
							alert(data.responseText);
						}
					});
				}
			}
		});
		//删除
		function deletes(obj){
			let uid = $(obj).data("id");
			$.tzAlert({
				content:"您确定要删除吗？",
				callback:function(ok){
					 if(ok){
						 $.ajax({
							type:"get",
							url:"<%=path%>/sysmanager/dict/deleteDict",
							async:true,
							dataType:"json",
							data:{
								id:uid
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