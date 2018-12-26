<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>不好意思,您的参数或数据为空,请想明白后再来!<a href="javascript:history.back(-1);">返回</a></h2>
	<h2>请求地址:${pageContext.errorData.requestURI }</h2>
	<h2>状态吗:${pageContext.errorData.statusCode }</h2>
	<h2>异常:${pageContext.errorData.throwable }</h2>
	<h2>servlet名称:${pageContext.errorData.servletName }</h2>
</body>
</html>