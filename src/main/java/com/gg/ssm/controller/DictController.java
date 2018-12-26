package com.gg.ssm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gg.ssm.entity.Dict;
import com.gg.ssm.entity.Menu;
import com.gg.ssm.framework.dto.PageParam;
import com.gg.ssm.framework.utils.PageUtils;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.service.IDictService;
import com.gg.ssm.service.IMenuService;
import com.github.pagehelper.PageInfo;
/**
 * 字典控制层
 * 
 * DictController
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月9日-上午12:18:37 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("sysmanager/dict")
public class DictController {
	static Logger log = Logger.getLogger(DictController.class);
	@Autowired
	private IDictService iDctService;
	@Autowired
	private IMenuService iMenuService;
	//进入字典页面
	@RequestMapping("/goDict")
	public String goDict(Model model){
		List<String> dictTypeList = iDctService.getAllDictType();
		model.addAttribute("dictTypeList", dictTypeList);
		//将用户对应的所有菜单查询出来,返回个页面动态加载
		List<Menu> menuListAll = this.iMenuService.getMenuListByUserId(UserInfo.getCurrentUserId());
		model.addAttribute("menusList", menuListAll);
		//将用户名传给页面,并在头部显示
		model.addAttribute("userName", UserInfo.getCurrentUserName());
		//进入字典列表时初始化下拉列表
		return "/sysmanager/dict/dict";
	}
	/**
	 * 
	 * 根据  类型  和 描述  查询  字典列表
	 * com.gg.ssm.controller 
	 * 方法名：queryDictList
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月9日-上午1:24:21 
	 * @param type
	 * @param description
	 * @return List<Dict>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("queryDictList")
	public @ResponseBody Map<String, Object> queryDictList(String type,String description,int pageNo,int pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//获取查询条件
		Dict dict  = new Dict();
		if(StringUtils.isNotEmpty(type))dict.setType(type);
		if(StringUtils.isNotEmpty(description))dict.setDescription(description);
		//构造分页对象
		PageParam pageParam = new PageParam();
		pageParam.setPageNo(pageNo);
		pageParam.setPageSize(pageSize);
		PageInfo<Dict> pageInfo = iDctService.getDictListPage(dict,pageParam);
		List<Dict> dictList = pageInfo.getList();
		resultMap.put("dictList", dictList);
		//获取返回的分页条
		String pageStr = PageUtils.pageStr(pageInfo, "getListPage");
		resultMap.put("pageStr", pageStr);
		return resultMap;
	}
	/**
	 * 
	 * 字典信息增加
	 * com.gg.ssm.controller 
	 * 方法名：addDict
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月9日-下午1:32:37 
	 * @param dict
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/addDict")
	public @ResponseBody Map<String, Object> addDict(@RequestBody Dict dict){
		System.out.println("value:"+dict.getValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iDctService.addDict(dict)){
			resultMap.put("result", "增加字典信息成功!");
		}else{
			resultMap.put("result", "增加字典信息失败!");
		}
		return resultMap;
	}
	/**
	 * 
	 * 字典信息修改
	 * com.gg.ssm.controller 
	 * 方法名：addDict
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月9日-下午1:32:37 
	 * @param dict
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/updateDict")
	public @ResponseBody Map<String, Object> updateDict(@RequestBody Dict dict){
		System.out.println("value:"+dict.getValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iDctService.updateDict(dict)){
			resultMap.put("result", "修改字典信息成功!");
		}else{
			resultMap.put("result", "修改字典信息失败!");
		}
		return resultMap;
	}
	/**
	 * 
	 * 字典删除
	 * com.gg.ssm.controller 
	 * 方法名：deleteDict
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月9日-下午10:08:50 
	 * @param id
	 * @return Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("/deleteDict")
	public @ResponseBody Map<String, Object> deleteDict(Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(iDctService.deleteDict(id)){
			resultMap.put("result", 1);
		}else{
			resultMap.put("result", 0);
		}
		
		return resultMap;
	}
}
