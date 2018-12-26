package com.gg.ssm.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.entity.Area;
import com.gg.ssm.entity.RoleToArea;
import com.gg.ssm.entity.User;
import com.gg.ssm.mapper.AreaMapper;
import com.gg.ssm.mapper.RoleMapper;
import com.gg.ssm.service.IAreaService;
@Service
public class IAreaServiceImpl implements IAreaService{
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private RoleMapper rmapper;
	//查询区域所有
	@Override
	public List<Area> getAllAreaList() {
		return areaMapper.getAllAreaList();
	}
	//根据id查询区域明细
	@Override
	public Area getAreaListById(Long id) {
		return areaMapper.getAreaListById(id);
	}
	//增加
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean addArea(Area area) {
		boolean flag = false;
		area.setUpdateDate(new Date());
		area.setCreateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			area.setUpdateBy(user.getUserId().toString());
			area.setCreateBy(user.getUserId().toString());
		}
		flag = areaMapper.addArea(area);
		//增加区域时要给超级管理员一个映射
		RoleToArea rta = new RoleToArea();
		rta.setRoleId(1l);
		rta.setAreaId(area.getId());
		flag = rmapper.addRoleToArea(rta);
		return flag;
	}
	//查询有无子节点
	@Override
	public Integer getChildCount(Long areaId) {
		return areaMapper.getChildCount(areaId);
	}
	//删除关联关系
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean AreaToRoleDelete(Long areaId) {
		return areaMapper.AreaToRoleDelete(areaId);
	}
	//删除本身
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean AreaDelete(Long areaId) {
		return areaMapper.AreaDelete(areaId);
	}
	//修改
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean updateArea(Area area) {
		area.setUpdateDate(new Date());
		//在service中拿到登录用户的信息
		User user = (User) httpSession.getAttribute("user");
		if(user != null){
			area.setUpdateBy(user.getUserId().toString());
		}
		return areaMapper.updateArea(area);
	}

}
