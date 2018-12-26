package com.gg.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gg.ssm.entity.Menu;
import com.gg.ssm.framework.dto.TreeDto;
import com.gg.ssm.framework.utils.TreeTableUtil;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IMenuService;
import com.sun.source.tree.Tree;

/**
 * 菜单管理
 * 
 * MenuController
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月10日-下午12:33:54 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("sysmanager/menu")
public class MenuController {
	@Autowired
	private IMenuService iMenuService;
	/**
	 * 
	 * 菜单列表
	 * com.gg.ssm.controller 
	 * 方法名：goMenu
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月10日-下午10:03:16 
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/goMenu")
	public String goMenu(Model model){
		List<Menu> menuList = iMenuService.getAllListMenu();
		List<Menu> sortMenuList = new ArrayList<Menu>();
		TreeTableUtil.sortMenuList(sortMenuList, menuList, 0l);
		model.addAttribute("menuList", sortMenuList);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "/sysmanager/menu/menu";
	}
	/**
	 * 
	 * 菜单删除
	 * com.gg.ssm.controller 
	 * 方法名：deleteMenu
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月10日-下午10:03:02 
	 * @param id
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/deleteMenu")
	public @ResponseBody Map<String, Object> deleteMenu(Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//对树形结构的数据,我们在删除的时候要注意,必须确保无子节点才可以直接删除,否则要给出提示
		if(iMenuService.getChildCount(id).intValue()>0){
			resultMap.put("result","此菜单下面还有子菜单,确认删除全部子菜单后再进行此操作!");
			return resultMap;
		}
		//删除菜单角色对应关系
		iMenuService.MenuToRoleDelete(id);
		//删除本身
		if(iMenuService.MenuDelete(id)){
			resultMap.put("result",1);
		}else{
			resultMap.put("result",0);
		}
		return resultMap;
	}
	/**
	 * 
	 * 菜单的修改
	 * com.gg.ssm.controller 
	 * 方法名：updateMenu
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月11日-下午11:34:42 
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/updateMenu")
	public String updateMenu(Long id,Model model){
		Menu menu = iMenuService.getMenuListById(id);
		model.addAttribute("menu", menu);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "/sysmanager/menu/menuAddAndUpdate";
	}
	@RequestMapping("/updatedMenu")
	public @ResponseBody Map<String, Object> updatedMenu(@RequestBody Menu menu){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(iMenuService.updateMenu(menu));
		if(iMenuService.updateMenu(menu)){
			resultMap.put("result", "修改菜单成功");
		}else{
			resultMap.put("result", "修改菜单失败");
		};
		return resultMap;
	}
	
	/**
	 * 
	 * 菜单的增加
	 * com.gg.ssm.controller 
	 * 方法名：updateMenu
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月11日-下午11:34:42 
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/addMenu")
	public String addMenu(Long parentId,Model model){
		if(parentId != null){
			Menu parentMenu = iMenuService.getMenuListById(parentId);
			Menu menu = new Menu();
			menu.setParentId(parentMenu.getId());
			menu.setParentName(parentMenu.getName());
			model.addAttribute("menu", menu);
			//将用户对应的所有菜单查询出来,返回个页面动态加载
			List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
			model.addAttribute("menusList", menuListAll);
			//将用户名传给页面,并在头部显示
			model.addAttribute("userName", UserInfo.getCurrentUserName());
		}
		return "/sysmanager/menu/menuAddAndUpdate";
	}
	@RequestMapping("/addedMenu")
	public @ResponseBody Map<String, Object> addedMenu(@RequestBody Menu menu){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(iMenuService.addMenu(menu));
		if(iMenuService.addMenu(menu)){
			resultMap.put("result", "增加菜单成功");
		}else{
			resultMap.put("result", "增加菜单失败");
		};
		return resultMap;
	}
	/**
	 * 
	 * 树结构的信息获取
	 * com.gg.ssm.controller 
	 * 方法名：getParentMenuTreeData
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月12日-上午11:46:54 
	 * @param menuId
	 * @return List<TreeDto>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/getParentMenuTreeData")
	public @ResponseBody List<TreeDto> getParentMenuTreeData(Long menuId){
		List<TreeDto> treeList = new ArrayList<TreeDto>();
		List<Menu> menuList = this.iMenuService.getAllListMenu();
		for(Menu menu : menuList){
			TreeDto treeDto = new TreeDto();
			treeDto.setId(menu.getId());
			treeDto.setName(menu.getName());
			treeDto.setParentId(menu.getParentId());
			treeList.add(treeDto);
		}
		
		//如果进入修改页面,为防止死循环,我们必须把本节点以及本机诶单下所有的儿子,孙子,全部过滤掉
		if(menuId != null){//为修改页面
			Map<Long, TreeDto> childrenMap = new HashMap<Long, TreeDto>();
			//将指定节点下的所有子节点找出来
			TreeTableUtil.getAllChildrenMap(childrenMap, treeList, menuId);
			Iterator<TreeDto> treeIterator = treeList.iterator();
			while(treeIterator.hasNext()){
				//如果子节点列表里面存在这个对象,则删除
				TreeDto treeDto = treeIterator.next();
				if(childrenMap.get(treeDto.getId()) != null){
					treeIterator.remove();
				}
				//删除本身
				if(treeDto.getId().equals(menuId)){
					treeIterator.remove();
				}
			}
		}
		return treeList;
	}
}
