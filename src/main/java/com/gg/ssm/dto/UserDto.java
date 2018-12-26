package com.gg.ssm.dto;



import org.apache.ibatis.type.Alias;

import com.gg.ssm.entity.User;
 
/**
 * 
 * 类描述 用户对象bean：  
 * 类名称：com.tz.ssspm.sysmanage.bean.User       
 * 创建人：keven  
 * 创建时间：2016年11月5日 下午9:06:40
 * @version   V1.0
 */
@Alias("userDto")
public class UserDto extends User implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	} 
}