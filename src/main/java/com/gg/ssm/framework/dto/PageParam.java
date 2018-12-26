package com.gg.ssm.framework.dto;
/**
 * 
 * 分页:页面传过来的参数
 * PageParam
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月9日-下午10:47:04 
 * @version 1.0.0
 *
 */
public class PageParam {
	//第几页
	private Integer pageNo=1;
	//每页几条数据
	private Integer pageSize=10;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
		
	
}
