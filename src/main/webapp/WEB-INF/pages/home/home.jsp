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
<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/main.css" />
<script type="text/javascript" src="<%=path%>/static/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
	html,body{width:100%;height:100%;background:url("<%=path%>/static/images/oabg.jpg");background-size:100% 100%;}
	.container .col-md-3{width:24%;height:260px;background:#E1E6F6;margin:0 5px 10px 5px;border-radius:5px;box-shadow:0 0 5px #e4e4e4;}
	.container .col-md-3 a{display:block;width:100%;height:100%;transition:2s;text-align:center;line-height:260px;text-decoration:none;color:#f50;text-shadow:1px 0 3px #999;}
	.container .col-md-3 a:hover{transform:rotate(360deg);background:#F1F7D0;}
</style>
</head>
<body>
	<!-- 头部 -->
	<%@ include file="header.jsp" %>
	<!-- 内容 -->
	<div class="container">
		<div class="row td">
			<c:if test="${menusList.size() eq 0 }">
				<div class="col-md-6 col-md-offset-3">
					<h4 class="text-danger text-center">不好意思,您还没有任何权限,赶紧去找管理员开通吧!</h4>
				</div>
			</c:if>
			<c:if test="${menusList.size() ne 0 }">
				<c:forEach items="${menusList }" var="mlist">	
					<c:if test="${mlist.parentId ne '1' && mlist.parentId ne '0'}">	
						<div class="col-md-3">
							 <a href="${mlist.href }">${mlist.name }</a>
						</div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</div>
</body>
</html>