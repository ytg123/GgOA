package com.gg.ssm.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.entity.Dept;
import com.gg.ssm.entity.RoleToDept;
import com.gg.ssm.entity.User;
import com.gg.ssm.mapper.DeptMapper;
import com.gg.ssm.mapper.RoleMapper;
import com.gg.ssm.service.IDeptService;
@Service
public class IDeptServiceImpl implements IDeptService{
	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private RoleMapper rmapper;
	//查询所有
	@Override
	public List<Dept> getAllDeptList() {
		return deptMapper.getAllDeptList();
	}
	//根据id查询部门明细
	@Override
	public Dept getDeptListById(Long id) {
		return deptMapper.getDeptListById(id);
	}
	//增加
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean addDept(Dept dept) {
		boolean flag = false;
		dept.setUpdateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			dept.setUpdateBy(user.getUserId().toString());
		}
		flag = deptMapper.addDept(dept);
		//增加部门时,要给超级管理员一个映射
		RoleToDept rtp = new RoleToDept();
		rtp.setDeptId(dept.getId());
		rtp.setRoleId(1l);
		flag = rmapper.addRoleToDept(rtp);
		return flag;
	}
	//查看有无子节点
	@Override
	public Integer getChildCount(Long deptId) {
		return deptMapper.getChildCount(deptId);
	}
	//根据部门id删除部门角色对应表
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean DeptToRoleDelete(Long deptId) {
		return deptMapper.DeptToRoleDelete(deptId);
	}
	//删除本身
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean DeptDelete(Long deptId) {
		return deptMapper.DeptDelete(deptId);
	}
	//修改
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean updateDept(Dept dept) {
		dept.setUpdateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			dept.setUpdateBy(user.getUserId().toString());
		}
		return deptMapper.updateDept(dept);
	}

}
