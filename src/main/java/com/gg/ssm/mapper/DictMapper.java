package com.gg.ssm.mapper;

import java.util.List;

import com.gg.ssm.entity.Dict;
/**
 * 字典业务相关接口(增删改查)
 * 
 * DictMapper
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月9日-上午12:41:06 
 * @version 1.0.0
 *
 */
public interface DictMapper {
	//查询所有字典类型
	public List<String>  getAllDictType();
	//根据条件查询字典列表
	public List<Dict>  queryDictList(Dict dict);
	//修改字典信息
	public boolean updateDict(Dict dict);
	//增加字典
	public boolean addDict(Dict dict);
	//删除字典
	public boolean deleteDict(Long id);
}
