package com.gg.ssm.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.gg.ssm.framework.dto.TreeDto;

/**
 * 
 * 部门JavaBean
 * Dept
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月12日-下午6:42:12 
 * @version 1.0.0
 *
 */
@Alias("dept")
public class Dept extends TreeDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String parentName;
   	private Integer sort;
  	private String code;
  	private String address;
  	private String master;
  	private String phone;
  	private String fax;
  	private String email; 	  
	private String	updateBy;
	private Date updateDate;
	private String remarks;
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
}
