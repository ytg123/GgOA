package com.gg.ssm.service;
/**
 * 
 * 菜单接口
 * IMenuService
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月10日-下午1:18:37 
 * @version 1.0.0
 *
 */

import java.util.List;

import com.gg.ssm.entity.Menu;

public interface IMenuService {
	//查询菜单所有
	public List<Menu> getAllListMenu();
	//删除菜单
	public boolean MenuDelete(Long menuId);
	//通过菜单id删除菜单角色对应关系
	public boolean MenuToRoleDelete(Long menuId);
	//查看有无子节点
	public Integer getChildCount(Long menuId);
	//通过id查询菜单明细
	public Menu getMenuListById(Long id);
	//修改菜单
	public boolean updateMenu(Menu menu);
	//增加
	public boolean addMenu(Menu menu);
	//查询用户权限控制内的菜单
	public List<Menu> getMenuListByUserId(Long UserId);
}
