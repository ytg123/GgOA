package com.gg.ssm.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.gg.ssm.framework.dto.TreeDto;

/**
 * 
 * 区域JavaBean
 * Dept
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月12日-下午6:42:12 
 * @version 1.0.0
 *
 */
@Alias("area")
public class Area extends TreeDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String parentName;
   	private Integer sort;
  	private String code;
  	private String	createBy;
  	private  Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
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
