package com.gg.ssm.mapper;

import java.util.List;

import com.gg.ssm.dto.RoleDto;
import com.gg.ssm.entity.Role;
import com.gg.ssm.entity.RoleToArea;
import com.gg.ssm.entity.RoleToDept;
import com.gg.ssm.entity.RoleToMenu;
/**
 * 
 * 角色dao接口
 * RoleMapper
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午2:09:21 
 * @version 1.0.0
 *
 */
public interface RoleMapper {
	//角色列表查询
	public List<Role> getRoleList();
	//增加角色信息
	public boolean AddRole(RoleDto roleDto);
	//增加角色对象
	public boolean addRoleO(Role role);
	//批量添加角色菜单对应信息
	public boolean addRoleToMenuBatch(List<RoleToMenu> roleMenuList);
	//批量添加角色部门对应信息
	public boolean addRoleToDeptBatch(List<RoleToDept> roleDeptList);
	//批量添加角色区域对应信息
	public boolean addRoleToAreaBatch(List<RoleToArea> roleAreaList);
	//增加角色菜单对应记录
	public boolean addRoleToMenu(RoleToMenu roleMenu);
	//增加角色区域对应记录
	public boolean addRoleToArea(RoleToArea roleArea);
	// 增加角色部门对应记录
	public boolean addRoleToDept(RoleToDept roleDept);
	//删除角色菜单对应信息
	public boolean delRoleMenuRoleId(Long roleId);
	//删除角色部门对应信息
	public boolean delRoleDeptRoleId(Long roleId);
	//删除角色区域对应信息
	public boolean delRoleAreaRoleId(Long roleId);
	//删除角色用户对应信息
	public boolean delRoleUserRoleId(Long roleId);
	//删除角色对象
	public boolean delRole(Long roleId);
	//根据角色id查询角色部门关联
	public List<RoleToDept> getDeptListByRoleId(Long roleId);
	// 根据角色id查询角色菜单关联
	public List<RoleToMenu> getMenuListByRoleId(Long roleId);
	// 根据角色id查询角色区域关联
	public List<RoleToArea> getAreaListByRoleId(Long roleId);
	//通过id查询角色信息
	public Role getRoleById(Long roleId);
	//修改角色信息,返回修改页面
	public boolean UpdateRole(RoleDto roleDto);
	//修改角色信息
	public boolean UpdateRoleO(Role role);
}
