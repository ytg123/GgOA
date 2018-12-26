package com.gg.ssm.service;
/**
 * 
 * 字典业务相关接口
 * IDictService
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月9日-上午12:37:53 
 * @version 1.0.0
 *
 */

import java.util.List;

import com.gg.ssm.entity.Dict;
import com.gg.ssm.framework.dto.PageParam;
import com.github.pagehelper.PageInfo;

public interface IDictService {
	//查询所有字典类型
	public List<String>  getAllDictType();
	//根据条件查询字典列表
	public List<Dict>  queryDictList(Dict dict);
	//分页查询字典列表
	public PageInfo<Dict>  getDictListPage(Dict dict,PageParam pageParam);
	//修改字典信息
	public boolean updateDict(Dict dict);
	//增加字典
	public boolean addDict(Dict dict);
	//删除字典
	public boolean deleteDict(Long id);
}
