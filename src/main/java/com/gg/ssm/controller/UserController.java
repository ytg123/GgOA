package com.gg.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gg.ssm.Vo.UserVo;
import com.gg.ssm.dto.UserDto;
import com.gg.ssm.entity.Menu;
import com.gg.ssm.entity.Role;
import com.gg.ssm.entity.User;
import com.gg.ssm.entity.UserToRole;
import com.gg.ssm.framework.dto.PageParam;
import com.gg.ssm.framework.utils.PageUtils;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IMenuService;
import com.gg.ssm.service.IRoleService;
import com.gg.ssm.service.IUserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;

/**
 * 
 * 用户信息管理
 * UserController
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月7日-上午1:51:54 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("sysmanager/user")
public class UserController {
	static Logger log = Logger.getLogger(UserController.class);
	@Autowired 
	private IUserService iUserService;
	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private IMenuService iMenuService;
	@Autowired
	private HttpSession httpSession;
	//返回密码修改页面
	@RequestMapping("/goChangePsd")
	public String goChangePsd(Model model){
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "sysmanager/user/updatePsd";
	}
	/**
	 * 
	 * 修改密码
	 * com.gg.ssm.controller 
	 * 方法名：savePsd
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月7日-下午2:23:11 
	 * @param odlPsd
	 * @param newPsd
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/savePsd")
	public @ResponseBody Map<String, Object> savePsd(String odlPsd,String newPsd){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long userId = UserInfo.getCurrentUserId();
		//1:确保旧密码正确
		User user = iUserService.getUserById(userId);
		//2:如果正确,则进行修改密码,否则不行
		boolean odlPsdEn = iUserService.validatorPassword(odlPsd, user.getPassword());
		if(!odlPsdEn){
			resultMap.put("result", "密码修改失败,原始密码错误!");
		}else{
			if(StringUtil.isNotEmpty(newPsd)){
				try{
					if(iUserService.updatePsd(userId, newPsd)){
						resultMap.put("result", "修改密码成功!");
					};
				}catch(Exception ex){
					resultMap.put("result", "修改密码失败!");
					log.error("修改密码失败",ex);
				}
			}else{
				resultMap.put("result", "密码修改失败,新密码不能为空!");
			}
			
		}
		return resultMap;
	}
	//返回个人信息修改页面
	@RequestMapping("/goUinfo")
	public String goUinfo(Model model){
		Long userId = UserInfo.getCurrentUserId();;
		UserDto userDto = iUserService.getUserInfoById(userId);
		model.addAttribute("userDto", userDto);
		//通过用户id查询用户自己拥有的角色
		User user = (User)httpSession.getAttribute("user");
		userId = user.getUserId();
		List<UserToRole> userToRoles = iUserService.getUserRoleByUserId(userId);
		List<Role> roleList = new ArrayList<Role>();
		for(UserToRole urole : userToRoles){
			Role role = iRoleService.getRoleById(urole.getRoleId());
			roleList.add(role);
		}
		model.addAttribute("roleList", roleList);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "sysmanager/user/userInfo";
	}
	/**
	 * 
	 * 修改用户信息
	 * com.gg.ssm.controller 
	 * 方法名：saveUserInfo
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月8日-下午10:55:19 
	 * @param userId
	 * @param userName
	 * @param userNo
	 * @param email
	 * @param phone
	 * @param mobile
	 * @param remarks
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/saveUserInfo")
	public @ResponseBody Map<String, Object>  saveUserInfo(@RequestBody User user){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iUserService.updateUserInfo(user)){
			resultMap.put("result","修改用户信息成功!");
		}else{
			resultMap.put("result","修改用户信息失败!");
		}
		return resultMap;
	}
	/**
	 * 
	 * 去往用户列表页
	 * com.gg.ssm.controller 
	 * 方法名：UserList
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月14日-下午1:12:38 
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/goUserList")
	public String goUserList(Model model){
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "sysmanager/user/userList";
	}
	/**
	 * 
	 * 分页查询用户列表
	 * com.gg.ssm.controller 
	 * 方法名：getUserList
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月14日-下午2:30:12 
	 * @param deptId
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/getUserList")
	public @ResponseBody Map<String, Object> getUserList(Long deptId,String userName,int pageNo,int pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = new User();
		if(deptId != null)user.setDeptId(deptId);
		if(userName != null)user.setUserName(userName);
		//获取分页条件
		PageParam pageParam = new PageParam();
		pageParam.setPageNo(pageNo);
		pageParam.setPageSize(pageSize);
		//获取分页数据和分页的java代码
		List<UserDto> userList = new ArrayList<UserDto>();
		PageInfo<UserDto> pageInfo = this.iUserService.getUserDtoList(user, pageParam);
		userList = pageInfo.getList();
		resultMap.put("userList", userList);
		//java代码
		String pageStr = PageUtils.pageStr(pageInfo, "getUserList");
		resultMap.put("pageStr",pageStr);
		return resultMap;
	}
	//用户信息修改页面
	@RequestMapping("/userUpdateEdit")
	public String userUpdateEdit(Model model,Long userId){
		//查询角色列表
		List<Role> roleList = iRoleService.getRoleList();
		model.addAttribute("roleList", roleList);
		//用户信息
		UserDto userDto = this.iUserService.getUserInfoById(userId);
		model.addAttribute("userDto", userDto);
		//还需要将当前的角色信息查询出来,并给页面默认勾选上角色用户对应表
		List<UserToRole> userRoleList = iUserService.getUserRoleByUserId(userId);
		if(userRoleList !=null){
			Map<Long, Long> userRoleMap = new HashMap<Long, Long>();
			for(UserToRole userRole : userRoleList){
				userRoleMap.put(userRole.getRoleId(), userRole.getRoleId());
			}
			model.addAttribute("roleCheckMap", userRoleMap);
		}
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		return "sysmanager/user/userEdit";
	}
	/**
	 * 
	 * 修改用户信息以及用户角色对应表
	 * com.gg.ssm.controller 
	 * 方法名：updateUserDtoInfo
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月14日-下午7:57:40 
	 * @param userVo
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("updateUserDtoInfo")
	public @ResponseBody Map<String, Object> updateUserDtoInfo(@RequestBody UserVo userVo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(iUserService.updateUserVo(userVo)){
			resultMap.put("result", "修改用户信息成功!");
		}else{
			resultMap.put("result", "修改用户信息失败!");
		};
		
		return resultMap;
	}
	//用户信息新增页面
	@RequestMapping("/userAddEdit")
	public String userAddEdit(Model model){
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//查询角色列表
		List<Role> roleList = iRoleService.getRoleList();
		model.addAttribute("roleList", roleList);
		return "sysmanager/user/userEdit";
	}
	/**
	 * 
	 * 保存(新增)用户管理信息
	 * com.gg.ssm.controller 
	 * 方法名：saveUserDtoInfo
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月14日-下午4:48:29 
	 * @param user
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/saveUserDtoInfo")
	public @ResponseBody Map<String, Object>  saveUserDtoInfo(@RequestBody UserVo userVo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iUserService.addUser(userVo)){
			resultMap.put("result", "增加用户信息成功!");
		}else{
			resultMap.put("result", "增加用户信息失败!");
		};
		return resultMap;
	}
	/**
	 * 
	 * 删除用户信息 和 用户角色对应表
	 * com.gg.ssm.controller 
	 * 方法名：deleteUser
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月14日-下午6:13:03 
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/deleteUser")
	public @ResponseBody Map<String, Object> deleteUser(Long userId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(this.iUserService.deleteUser(userId)){
			resultMap.put("result", 1);
		}else{
			resultMap.put("result", 0);
		};
		return resultMap;
	}
	
}
