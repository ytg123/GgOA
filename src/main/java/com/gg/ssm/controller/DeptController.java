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

import com.gg.ssm.entity.Dept;
import com.gg.ssm.entity.Menu;
import com.gg.ssm.framework.dto.TreeDto;
import com.gg.ssm.framework.utils.TreeTableUtil;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IDeptService;
import com.gg.ssm.service.IMenuService;

@Controller
@RequestMapping("/sysmanager/dept")
public class DeptController {
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private IMenuService iMenuService;
	//去往部门页面
	@RequestMapping("/goDept")
	public String goDept(Model model){
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		//所有部门信息
		List<Dept> deptList = this.iDeptService.getAllDeptList();
		//对树结构进行排序
		List<Dept> sortDeptList = new ArrayList<Dept>();
		TreeTableUtil.sortTreeList(sortDeptList, deptList, 0L);
		model.addAttribute("deptList", sortDeptList);
		return "/sysmanager/dept/dept";
	}
	//获取所有部门信息
	@RequestMapping("/getAllDeptList")
	public @ResponseBody List<TreeDto> getAllDeptList(){
		List<TreeDto> treeList = new ArrayList<TreeDto>();
		List<Dept> depts = this.iDeptService.getAllDeptList();
		for(Dept dept:depts){
			TreeDto treeDto = new TreeDto();
			treeDto.setId(dept.getId());
			treeDto.setName(dept.getName());
			treeDto.setParentId(dept.getParentId());
			treeList.add(treeDto);
		}
		return treeList;
	}
	/**
	 * 
	 * 去往部门增加页面
	 * com.gg.ssm.controller 
	 * 方法名：addDept
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月25日-下午5:48:38 
	 * @param parentId
	 * @param model
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/addDept")
	public String addDept(Long parentId,Model model){
		if(parentId != null){
			Dept parentDept = iDeptService.getDeptListById(parentId);
			Dept dept = new Dept();
			dept.setParentId(parentDept.getId());
			dept.setParentName(parentDept.getName());
			model.addAttribute("dept", dept);
			//将用户对应的所有菜单查询出来,返回个页面动态加载
			List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
			model.addAttribute("menusList", menuListAll);
			//将用户名传给页面,并在头部显示
			model.addAttribute("userName", UserInfo.getCurrentUserName());
		}
		return "/sysmanager/dept/deptAddAndUpdate";
	}
	/**
	 * 
	 * 增加功能的实现
	 * com.gg.ssm.controller 
	 * 方法名：addedDept
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月25日-下午7:38:01 
	 * @param dept
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/addedDept")
	public @ResponseBody Map<String, Object> addedDept(@RequestBody Dept dept){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iDeptService.addDept(dept)){
			resultMap.put("result", "增加部门成功");
		}else{
			resultMap.put("result", "增加部门失败");
		};
		return resultMap;
	}
	/**
	 * 
	 * 部门的删除
	 * com.gg.ssm.controller 
	 * 方法名：deleteDept
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月25日-下午7:47:58 
	 * @param id
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/deleteDept")
	public @ResponseBody Map<String, Object> deleteDept(Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//对树形结构的数据,我们在删除的时候要注意,必须确保无子节点才可以直接删除,否则要给出提示
		if(iDeptService.getChildCount(id).intValue()>0){
			resultMap.put("result","此菜单下面还有子菜单,确认删除全部子菜单后再进行此操作!");
			return resultMap;
		}
		//删除菜单角色对应关系
		iDeptService.DeptToRoleDelete(id);
		//删除本身
		if(iDeptService.DeptDelete(id)){
			resultMap.put("result",1);
		}else{
			resultMap.put("result",0);
		}
		return resultMap;
	}
	/**
	 * 
	 * 部门的修改
	 * com.gg.ssm.controller 
	 * 方法名：updateMenu
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月11日-下午11:34:42 
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/updateDept")
	public String updateMenu(Long id,Model model){
		Dept dept = iDeptService.getDeptListById(id);
		model.addAttribute("dept", dept);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		return "/sysmanager/dept/deptAddAndUpdate";
	}
	/**
	 * 
	 * 修改功能的实现
	 * com.gg.ssm.controller 
	 * 方法名：updatedDept
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月25日-下午8:11:01 
	 * @param dept
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/updatedDept")
	public @ResponseBody Map<String, Object> updatedDept(@RequestBody Dept dept){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iDeptService.updateDept(dept)){
			resultMap.put("result", "修改部门成功");
		}else{
			resultMap.put("result", "修改部门失败");
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
	@RequestMapping("/getParentDeptTreeData")
	public @ResponseBody List<TreeDto> getParentDeptTreeData(Long deptId){
		List<TreeDto> treeList = new ArrayList<TreeDto>();
		List<Dept> deptList = this.iDeptService.getAllDeptList();
		for(Dept dept : deptList){
			TreeDto treeDto = new TreeDto();
			treeDto.setId(dept.getId());
			treeDto.setName(dept.getName());
			treeDto.setParentId(dept.getParentId());
			treeList.add(treeDto);
		}
		
		//如果进入修改页面,为防止死循环,我们必须把本节点以及本节点下所有的儿子,孙子,全部过滤掉
		if(deptId != null){//为修改页面
			Map<Long, TreeDto> childrenMap = new HashMap<Long, TreeDto>();
			//将指定节点下的所有子节点找出来
			TreeTableUtil.getAllChildrenMap(childrenMap, treeList, deptId);
			Iterator<TreeDto> treeIterator = treeList.iterator();
			while(treeIterator.hasNext()){
				//如果子节点列表里面存在这个对象,则删除
				TreeDto treeDto = treeIterator.next();
				if(childrenMap.get(treeDto.getId()) != null){
					treeIterator.remove();
				}
				//删除本身
				if(treeDto.getId().equals(deptId)){
					treeIterator.remove();
				}
			}
		}
		return treeList;
	}
}
