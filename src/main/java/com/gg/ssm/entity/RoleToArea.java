package com.gg.ssm.entity;



import org.apache.ibatis.type.Alias;
/**
 * 角色区域对应实体类
 * 
 * RoleToMenu
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午4:35:10 
 * @version 1.0.0
 *
 */
@Alias("roleToArea")
public class RoleToArea {
	private Long roleId;
	private Long areaId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
}
