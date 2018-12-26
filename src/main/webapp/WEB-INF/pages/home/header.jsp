<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 头部 -->
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container">
	    <div class="row">
	    	<div class="col-md-6">
	    		<div class="navbar-header">
			        <button type="button" class="navbar-toggle" data-toggle="collapse"
			                data-target="#example-navbar-collapse">
			            <span class="sr-only">切换导航</span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			        </button>
			        <a class="navbar-brand" href="/sysoa/home">
			        	<img src="/sysoa/static/img/oa.png" width="100" height="50"/>
			        </a>
			    </div>
	    	</div>
	    	<div class="col-md-4 col-md-offset-1">
	    		<h5 class="welcom">您好,欢迎来到<span class="orange">${userName}</span>来到广广OA系统</h5>
	    	</div>
	    	<div class="col-md-1">
	    		<a href="/sysoa/logout" class="btn btn-success">退出</a>
	    	</div>
	    </div>
    </div>
</nav>