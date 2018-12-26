package com.gg.ssm.mapper;
/**
 * 区域dao接口
 * 
 * AreaMapper
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月13日-下午2:46:19 
 * @version 1.0.0
 *
 */

import java.util.List;

import com.gg.ssm.entity.Area;
import com.gg.ssm.entity.Dept;

public interface AreaMapper {
	//查询所有
	public List<Area> getAllAreaList();
	//通过id查询区域列表明细
	public Area getAreaListById(Long id);
	//增加区域
	public boolean addArea(Area area);
	//查看有无子节点
	public Integer getChildCount(Long areaId);
	//删除对应关系表
	public boolean AreaToRoleDelete(Long areaId);
	//删除本身
	public boolean AreaDelete(Long areaId);
	//修改区域
	public boolean updateArea(Area area);
}
