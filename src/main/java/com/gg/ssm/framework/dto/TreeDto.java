package com.gg.ssm.framework.dto;
/**
 * 属性结构对象的父类
 * 
 * TreeDto
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月10日-下午2:13:33 
 * @version 1.0.0
 *
 */
public class TreeDto {
	private Long id;
 	private String name;
 	private Long parentId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
