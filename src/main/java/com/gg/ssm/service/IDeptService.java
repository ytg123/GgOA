package com.gg.ssm.service;

import java.util.List;

import com.gg.ssm.entity.Dept;
import com.gg.ssm.entity.Menu;

/**
 * 
 * 部门服务层接口
 * IDeptService
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午2:44:14 
 * @version 1.0.0
 *
 */
public interface IDeptService {
	//查询所有
	public List<Dept> getAllDeptList();
	//通过id查询部门列表明细
	public Dept getDeptListById(Long id);
	//增加部门
	public boolean addDept(Dept dept);
	//查看有无子节点
	public Integer getChildCount(Long deptId);
	//删除对应关系表
	public boolean DeptToRoleDelete(Long deptId);
	//删除本身
	public boolean DeptDelete(Long deptId);
	//修改部门
	public boolean updateDept(Dept dept);
}
