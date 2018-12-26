package com.gg.ssm.dto;

import java.io.Serializable;
import java.util.Map;

import com.gg.ssm.entity.Role;
/**
 * role的包装类
 * 
 * RoleDto
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午3:52:01 
 * @version 1.0.0
 *
 */
public class RoleDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Role role;
 	private Map<Long,Long> deptIds;
 	private Map<Long,Long> menuIds;
 	private Map<Long,Long> areaIds;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Map<Long, Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(Map<Long, Long> deptIds) {
		this.deptIds = deptIds;
	}
	public Map<Long, Long> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(Map<Long, Long> menuIds) {
		this.menuIds = menuIds;
	}
	public Map<Long, Long> getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(Map<Long, Long> areaIds) {
		this.areaIds = areaIds;
	}
 	
}
