package com.gg.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gg.ssm.dto.RoleDto;
import com.gg.ssm.entity.Area;
import com.gg.ssm.entity.Dept;
import com.gg.ssm.entity.Menu;
import com.gg.ssm.entity.Role;
import com.gg.ssm.entity.RoleToArea;
import com.gg.ssm.entity.RoleToDept;
import com.gg.ssm.entity.RoleToMenu;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IAreaService;
import com.gg.ssm.service.IDeptService;
import com.gg.ssm.service.IMenuService;
import com.gg.ssm.service.IRoleService;

@Controller
@RequestMapping("/sysmanager/role")
public class RoleController {
	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private IMenuService iMenuService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private IAreaService iAreaService;
	/**
	 * 
	 * 去往角色页面
	 * com.gg.ssm.controller 
	 * 方法名：goArea
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月13日-下午4:01:16 
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/goRole")
	public String goArea(Model model){
		
		List<Role> roleList = new ArrayList<Role>();
		roleList = iRoleService.getRoleList();
		model.addAttribute("RoleList", roleList);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "/sysmanager/role/role";
	}
	/**
	 * 
	 * 去往角色增加页面
	 * com.gg.ssm.controller 
	 * 方法名：goAddRole
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月13日-下午4:01:34 
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/goAddRole")
	public String goAddRole(Model model){
		//不管是修改还是增加,都需要在页面生成 菜单  部门  区域的三棵树
		List<Menu> menuList = iMenuService.getAllListMenu();
		List<Dept> deptList = iDeptService.getAllDeptList();
		List<Area> areaList = iAreaService.getAllAreaList();
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("deptList", deptList);
		model.addAttribute("areaList", areaList);
		return "/sysmanager/role/roleAddAndUpdate";
	}
	/**
	 * 
	 * 角色增加功能
	 * com.gg.ssm.controller 
	 * 方法名：AddRole
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月13日-下午4:01:54 
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/AddRole")
	public @ResponseBody Map<String, Object> AddRole(@RequestBody RoleDto roleDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (iRoleService.AddRole(roleDto)) {
			resultMap.put("result", "增加角色成功!");
		}else{
			resultMap.put("result", "增加角色失败!");
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 角色删除
	 * com.gg.ssm.controller 
	 * 方法名：delRole
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月13日-下午5:52:04 
	 * @param roleId
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/delRole")
	public @ResponseBody Map<String, Object> delRole(Long roleId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iRoleService.delRole(roleId)){
			resultMap.put("result", 1);
		}else{
			resultMap.put("result",0);
		}
		return resultMap;
	}
	/**
	 * 
	 * 修改角色信息,需要查询出改角色所拥有的权限信息
	 * com.gg.ssm.controller 
	 * 方法名：goUpateRole
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月13日-下午6:23:11 
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/goUpateRole")
	public String goUpateRole(Model model,Long roleId){
		//不管是修改还是增加,都需要在页面生成 菜单  部门  区域的三棵树
		List<Menu> menuList = iMenuService.getAllListMenu();
		List<Dept> deptList = iDeptService.getAllDeptList();
		List<Area> areaList = iAreaService.getAllAreaList();
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("deptList", deptList);
		model.addAttribute("areaList", areaList);
		
		
		List<RoleToMenu> roleToMenuList = iRoleService.getMenuListByRoleId(roleId);
		List<RoleToDept> roleToDeptList = iRoleService.getDeptListByRoleId(roleId);
		List<RoleToArea> roleToAreaList = iRoleService.getAreaListByRoleId(roleId);
		
		model.addAttribute("roleMenuList", roleToMenuList);
		model.addAttribute("roleDeptList", roleToDeptList);
		model.addAttribute("roleAreaList", roleToAreaList);
		Role role= iRoleService.getRoleById(roleId);
		model.addAttribute("role", role);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		return "/sysmanager/role/roleAddAndUpdate";
	}
	@RequestMapping("/updateRole")
	public @ResponseBody Map<String, Object> updateRole(@RequestBody RoleDto roleDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iRoleService.UpdateRole(roleDto)){
			resultMap.put("result", "修改角色信息成功!");
		}else{
			resultMap.put("result", "修改角色信息失败!");
		}
		
		return resultMap;
	}
}
