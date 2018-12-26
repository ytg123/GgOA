package com.gg.ssm.entity;



import org.apache.ibatis.type.Alias;
/**
 * 角色菜单对应实体类
 * 
 * RoleToMenu
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午4:35:10 
 * @version 1.0.0
 *
 */
@Alias("roleToMenu")
public class RoleToMenu{
	private Long roleId;
	private Long menuId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
