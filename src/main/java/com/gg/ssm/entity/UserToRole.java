package com.gg.ssm.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
@Alias("userToRole")
public class UserToRole implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long roleId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
