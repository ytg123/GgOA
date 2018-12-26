package com.gg.ssm.Vo;

import java.io.Serializable;
import java.util.Map;

import com.gg.ssm.entity.User;

public class UserVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private User user;
	private Map<Long, Long> roleIds;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Map<Long, Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Map<Long, Long> roleIds) {
		this.roleIds = roleIds;
	}
}
