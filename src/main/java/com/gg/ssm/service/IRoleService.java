package com.gg.ssm.service;
/**
 * 
 * 角色服务层
 * RoleService
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午2:07:24 
 * @version 1.0.0
 *
 */

import java.util.List;

import com.gg.ssm.dto.RoleDto;
import com.gg.ssm.entity.Role;
import com.gg.ssm.entity.RoleToArea;
import com.gg.ssm.entity.RoleToDept;
import com.gg.ssm.entity.RoleToMenu;

public interface IRoleService {
	//角色列表查询
	public List<Role> getRoleList();
	//增加角色信息
	public boolean AddRole(RoleDto roleDto);
	// 删除角色信息以及一切的对应关系
	public boolean delRole(Long roleId);
	// 根据角色id查询角色部门关联
	public List<RoleToDept> getDeptListByRoleId(Long roleId);
	// 根据角色id查询角色菜单关联
	public List<RoleToMenu> getMenuListByRoleId(Long roleId);
	// 根据角色id查询角色区域关联
	public List<RoleToArea> getAreaListByRoleId(Long roleId);
	//通过id查询角色信息
	public Role getRoleById(Long roleId);
	//修改角色信息
	public boolean UpdateRole(RoleDto roleDto);
}
