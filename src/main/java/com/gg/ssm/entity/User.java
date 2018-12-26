package com.gg.ssm.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;
 
/**
 * 
 * 类描述 用户对象bean：  
 * 类名称：com.tz.ssspm.sysmanage.bean.User       
 * 创建人：keven  
 * 创建时间：2016年11月5日 下午9:06:40
 * @version   V1.0
 */
@Alias("user")
public class User implements java.io.Serializable{
	
	
	private static final long serialVersionUID = 6566516211603581252L;
	private Long userId;
	private Long deptId;
 	private String loginName;
	private String	password;	 
	private String	userNo;
	private String userName;
	private String	email;
	private String	phone;
	private String	mobile;
	private String	updateBy;
	private Date updateDate;
	private String remarks;
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
 
	 
}