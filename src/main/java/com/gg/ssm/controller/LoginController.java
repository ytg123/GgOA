package com.gg.ssm.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 
 * 登录控制器
 * LoginController
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月5日-下午6:46:45 
 * @version 1.0.0
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gg.ssm.entity.Menu;
import com.gg.ssm.entity.User;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IMenuService;
import com.gg.ssm.service.IUserService;
@Controller
public class LoginController {
	static Logger log = Logger.getLogger(LoginController.class);
	@RequestMapping("/goLogin")
	public String gotoLogin(){
		return "login";
	}
	@Autowired 
	private IUserService iUserService;
	@Autowired 
	private IMenuService iMenuService;
	@RequestMapping("/loginUser")
	public String login(@RequestParam(value="loginName") String loginName,String password,HttpSession session,Model model){
		if(StringUtils.isNotEmpty(loginName)&&StringUtils.isNotEmpty(password)){
			User user = iUserService.loginUser(loginName, password);
			if(user != null){
				log.info("登录成功!");
				//将用户名放到session中,,用于拦截器使用
				session.setAttribute("user", user);
				//return "forward:WEB-INF/pages/home/home.jsp";
				return "redirect:/home";
			}else{
				model.addAttribute("loginErr","用户名或密码不对,请您重新登录!");
				return "forward:/WEB-INF/pages/login.jsp";
			}
		}else{
			model.addAttribute("loginErr","用户名或密码不对,请您重新登录!");
			return "forward:/WEB-INF/pages/login.jsp";
		}
	}
	//进入主页
	@RequestMapping("/home")
	public String home(Model model){
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuList = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuList);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "home/home";
	}
	//退出
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		//使session失效
		session.invalidate();
		return "forward:/WEB-INF/pages/login.jsp";
	}
	
}
