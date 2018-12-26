package com.gg.ssm.serviceImpl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.entity.Dict;
import com.gg.ssm.framework.dto.PageParam;
import com.gg.ssm.mapper.DictMapper;
import com.gg.ssm.service.IDictService;
import com.github.pagehelper.PageHelper;
/**
 * 字典业务相关接口实现类
 * 
 * IDictServiceImpl
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月9日-上午12:40:19 
 * @version 1.0.0
 *
 */
import com.github.pagehelper.PageInfo;
@Service
public class IDictServiceImpl implements IDictService{
	@Autowired
	private DictMapper dictMapper;
	//查询所有字典类型
	@Override
	public List<String> getAllDictType() {
		return dictMapper.getAllDictType();
	}
	//根据条件查询字典列表
	@Override
	public List<Dict> queryDictList(Dict dict) {
		return dictMapper.queryDictList(dict);
	}
	//修改字典
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean updateDict(Dict dict) {
		dict.setUpdateDate(new Date());
		dict.setUpdateBy("1l");
		return dictMapper.updateDict(dict);
	}
	//增加字典
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean addDict(Dict dict) {
		dict.setUpdateDate(new Date());
		dict.setUpdateBy("1l");
		return dictMapper.addDict(dict);
	}
	//删除字典
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean deleteDict(Long id) {
		return dictMapper.deleteDict(id);
	}
	
	//分页
	public PageInfo<Dict>  getDictListPage(Dict dict,PageParam pageParam){
		PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
		List<Dict> dictList = this.dictMapper.queryDictList(dict);
		PageInfo<Dict> pageInfo = new PageInfo<Dict>(dictList);
		return pageInfo;
	}

}
