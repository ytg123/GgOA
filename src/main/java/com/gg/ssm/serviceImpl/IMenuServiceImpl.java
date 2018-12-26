package com.gg.ssm.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.entity.Menu;
import com.gg.ssm.entity.RoleToMenu;
import com.gg.ssm.entity.User;
import com.gg.ssm.mapper.MenuMapper;
import com.gg.ssm.mapper.RoleMapper;
import com.gg.ssm.service.IMenuService;
@Service
public class IMenuServiceImpl implements IMenuService{
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private RoleMapper rmapper;
	//菜单列表查询
	@Override
	public List<Menu> getAllListMenu() {
		return menuMapper.getAllListMenu();
	}
	//菜单的删除
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean MenuDelete(Long menuId) {
		return menuMapper.MenuDelete(menuId);
	}
	//查看有无子节点
	@Override
	public Integer getChildCount(Long menuId) {
		return menuMapper.getChildCount(menuId);
	}
	//通过id查询菜单明细
	@Override
	public Menu getMenuListById(Long id) {
		return menuMapper.getMenuListById(id);
	}
	//修改菜单信息
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean updateMenu(Menu menu) {
		menu.setUpdateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			menu.setUpdateBy(user.getUserId().toString());
		}
		return menuMapper.updateMenu(menu);
	}
	//增加菜单信息
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean addMenu(Menu menu) {
		boolean flag = false;
		menu.setUpdateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			menu.setUpdateBy(user.getUserId().toString());
		}
		flag = menuMapper.addMenu(menu);
		//增加菜单时,要给超级管理员一个映射
		RoleToMenu rtm = new RoleToMenu();
		rtm.setRoleId(1l);
		rtm.setMenuId(menu.getId());
		flag = rmapper.addRoleToMenu(rtm);
		return flag;
	}
	//查询用户权限控制内的菜单
	@Override
	public List<Menu> getMenuListByUserId(Long UserId) {
		return menuMapper.getMenuListByUserId(UserId);
	}
	//根据菜单id删除菜单角色对应关系
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean MenuToRoleDelete(Long menuId) {
		return menuMapper.MenuToRoleDelete(menuId);
	}

}
