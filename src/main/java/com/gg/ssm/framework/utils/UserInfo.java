package com.gg.ssm.framework.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gg.ssm.entity.User;

/**
 * 
 * 获取登录用户的信息
 * UserInfo
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月12日-下午5:37:03 
 * @version 1.0.0
 *
 */
public class UserInfo {
	//获取userId
	public static Long getCurrentUserId(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if(request.getSession().getAttribute("user") != null){
			User user = (User) request.getSession().getAttribute("user");
			return user.getUserId();
		}else{
			return null;
		}
	}
	//获取用户名
	public static String getCurrentUserName(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if(request.getSession().getAttribute("user") != null){
			User user = (User) request.getSession().getAttribute("user");
			return user.getUserName();
		}else{
			return null;
		}
	}
}
