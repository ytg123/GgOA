package com.gg.ssm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.dto.RoleDto;
import com.gg.ssm.entity.Role;
import com.gg.ssm.entity.RoleToArea;
import com.gg.ssm.entity.RoleToDept;
import com.gg.ssm.entity.RoleToMenu;
import com.gg.ssm.mapper.RoleMapper;
import com.gg.ssm.service.IRoleService;
@Service
public class IRoleServiceImpl implements IRoleService{
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private HttpSession httpSession;
	//查询角色列表
	@Override
	public List<Role> getRoleList() {
		return roleMapper.getRoleList();
	}
	/**
	 * 保存角色信息,返回角色id
	 * 根据角色id,部门id拼凑角色部门对应列表  进行批量插入
	 * 根据角色id,区域id拼凑角色区域对应列表  进行批量插入
	 * 根据角色id,菜单id拼凑角色菜单对应列表  进行批量插入
	 */
	//事务
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean AddRole(RoleDto roleDto) {
		boolean flag = false;
		//增加角色对象
		Role role = roleDto.getRole();
		role.setUpdateBy(httpSession.getId());
		role.setUpdateDate(new Date());
		flag = roleMapper.addRoleO(role);
		//保存角色对象后,根据角色id,菜单id拼凑角色菜单对应列表
		Long roleId = role.getId();
		List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
		RoleToMenu roleToMenu;
		for(Long menuId:roleDto.getMenuIds().values()){
			roleToMenu = new RoleToMenu();
			roleToMenu.setMenuId(menuId);
			roleToMenu.setRoleId(roleId);
			roleMenuList.add(roleToMenu);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToMenuBatch(roleMenuList);
		
		//保存角色对象后,根据角色id,部门id拼凑角色部门对应列表
		List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
		RoleToDept roleToDept;
		for(Long deptId:roleDto.getDeptIds().values()){
			roleToDept = new RoleToDept();
			roleToDept.setDeptId(deptId);
			roleToDept.setRoleId(roleId);
			roleDeptList.add(roleToDept);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToDeptBatch(roleDeptList);
		//根据角色id,区域id拼凑角色区域对应列表  进行批量插入
		List<RoleToArea> roleToAreaList = new ArrayList<RoleToArea>();
		RoleToArea roleToArea;
		for(Long areaId:roleDto.getAreaIds().values()){
			roleToArea = new RoleToArea();
			roleToArea.setAreaId(areaId);
			roleToArea.setRoleId(roleId);
			roleToAreaList.add(roleToArea);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToAreaBatch(roleToAreaList);
		return flag;
	}
	/**
	 * 删除角色菜单对应表 删除角色部门对应表 删除角色区域对应表
	 * 删除角色信息
	 */
	//事务
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean delRole(Long roleId) {
		boolean flag = false;
		//删除角色菜单对应表
		flag = roleMapper.delRoleMenuRoleId(roleId);
		//删除角色部门对应表
		flag = roleMapper.delRoleDeptRoleId(roleId);
		//删除角色区域对应表
		flag = roleMapper.delRoleAreaRoleId(roleId);
		//删除角色用户对应表
		flag = roleMapper.delRoleUserRoleId(roleId);
		//删除角色信息
		flag = roleMapper.delRole(roleId);
		return flag;
		
	}
	//根据角色id查询部门信息
	@Override
	public List<RoleToDept> getDeptListByRoleId(Long roleId) {
		return roleMapper.getDeptListByRoleId(roleId);
	}
	//根据角色id查询菜单信息
	@Override
	public List<RoleToMenu> getMenuListByRoleId(Long roleId) {
		List<RoleToMenu> rMenus = roleMapper.getMenuListByRoleId(roleId);
		System.out.println(rMenus.size());
		return rMenus;
	}
	//根据角色id查询区域信息
	@Override
	public List<RoleToArea> getAreaListByRoleId(Long roleId) {
		return roleMapper.getAreaListByRoleId(roleId);
	}
	//根据角色id查询角色信息
	@Override
	public Role getRoleById(Long roleId) {
		return roleMapper.getRoleById(roleId);
	}
	/**
	 * 修改角色信息
	 * 根据角色id,删除角色部门对应列表  角色区域对应列表 角色菜单对应列表
	 * 根据角色id,部门id拼凑角色部门对应列表  进行批量插入
	 * 根据角色id,区域id拼凑角色区域对应列表  进行批量插入
	 * 根据角色id,菜单id拼凑角色菜单对应列表  进行批量插入
	 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean UpdateRole(RoleDto roleDto) {
		boolean flag = false;
		//修改角色对象
		Role role = roleDto.getRole();
		role.setUpdateBy(httpSession.getId());
		role.setUpdateDate(new Date());
		flag = roleMapper.UpdateRoleO(role);
		
		Long roleId = role.getId();
		
		//根据角色id删除 删除角色部门对应列表  角色区域对应列表 角色菜单对应列表
		flag = this.roleMapper.delRoleMenuRoleId(roleId);
		flag = this.roleMapper.delRoleDeptRoleId(roleId);
		flag = this.roleMapper.delRoleAreaRoleId(roleId);
		
		//保存角色对象后,根据角色id,菜单id拼凑角色菜单对应列表
		List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
		RoleToMenu roleToMenu;
		for(Long menuId:roleDto.getMenuIds().values()){
			roleToMenu = new RoleToMenu();
			roleToMenu.setMenuId(menuId);
			roleToMenu.setRoleId(roleId);
			roleMenuList.add(roleToMenu);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToMenuBatch(roleMenuList);
		
		//保存角色对象后,根据角色id,部门id拼凑角色部门对应列表
		List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
		RoleToDept roleToDept;
		for(Long deptId:roleDto.getDeptIds().values()){
			roleToDept = new RoleToDept();
			roleToDept.setDeptId(deptId);
			roleToDept.setRoleId(roleId);
			roleDeptList.add(roleToDept);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToDeptBatch(roleDeptList);
		//根据角色id,区域id拼凑角色区域对应列表  进行批量插入
		List<RoleToArea> areaList = new ArrayList<RoleToArea>();
		RoleToArea roleToArea;
		for(Long areaId:roleDto.getAreaIds().values()){
			roleToArea = new RoleToArea();
			roleToArea.setAreaId(areaId);
			roleToArea.setRoleId(roleId);
			areaList.add(roleToArea);
		}
		// 进行批量插入
		flag = roleMapper.addRoleToAreaBatch(areaList);
		return flag;
	}
}
