<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel-group" id="accordion">
	<c:forEach items="${menusList }" var="mlist" varStatus="i">
			 <c:if test="${mlist.parentId eq '1' && mlist.isShow == '1'}">
				<div class="panel panel-default">
			        <div class="panel-heading">
			            <h4 class="panel-title">
			                <a data-toggle="collapse" data-parent="#accordion" 
			                href="#collapse${i.index }">
			                	${mlist.name }
			                </a>
			            </h4>
			        </div>
			        <div id="collapse${i.index }" class="panel-collapse collapse">
			            <div class="panel-body">
				            <c:forEach items="${menusList }" var="mlist2">	
				            	<c:if test="${mlist2.parentId eq mlist.id && mlist2.parentId ne '0' && mlist2.isShow eq '1' }">
					                <ul class="list-group">
									    <li class="list-group-item"><a href="${mlist2.href }">${mlist2.name }</a></li>
									</ul>
								</c:if>
							</c:forEach>
			            </div>
			        </div>
			    </div>
		    </c:if>
	</c:forEach>
</div>
    <!-- <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" 
                href="#collapseOne">
                	系统设置
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body">
                <ul class="list-group">
				    <li class="list-group-item"><a href="/sysoa/sysmanager/menu/goMenu">菜单管理</a></li>
				    <li class="list-group-item"><a href="/sysoa/sysmanager/dict/goDict">字典管理</a></li>
				    <li class="list-group-item"><a href="/sysoa/sysmanager/user/goUserList">用户管理</a></li>
				    <li class="list-group-item"><a href="/sysoa/sysmanager/dept/goDept">部门管理</a></li>
				    <li class="list-group-item"><a href="/sysoa/sysmanager/area/goArea">区域管理</a></li>
				    <li class="list-group-item"><a href="/sysoa/sysmanager/role/goRole">角色管理</a></li>
				</ul>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" 
                href="#collapseTwo">
               		个人信息管理
            </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse">
        <div class="panel-body">
            <ul class="list-group">
            	<li class="list-group-item"><a href="/sysoa/sysmanager/user/goUinfo">个人信息</a></li>
            	<li class="list-group-item"><a href="/sysoa/sysmanager/user/goChangePsd">密码修改</a></li>
            </ul>
        </div>
        </div>
    </div> -->
