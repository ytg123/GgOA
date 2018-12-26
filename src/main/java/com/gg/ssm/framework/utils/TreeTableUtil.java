package com.gg.ssm.framework.utils;

import java.util.List;
import java.util.Map;

import com.gg.ssm.entity.Menu;
import com.gg.ssm.framework.dto.TreeDto;

/**
 * 关于树形结构的工具类
 * 
 * TreeTableUtil
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月10日-下午1:57:17 
 * @version 1.0.0
 *
 */
public class TreeTableUtil {
	/**
	 * 
	 * 对菜单的列表进行一个父子结构的排序
	 * com.gg.ssm.framework.utils 
	 * 方法名：sortMenuList
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月10日-下午1:59:50 
	 * @param sortMenuList
	 * @param menuList
	 * @param parentId void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void sortMenuList(List<Menu> sortMenuList,List<Menu> menuList,Long parentId){
		/**
		 *  轮询带排序列表,找到当前父节点的下一级某个节点
		 *  将当前节点放入指定的列表
		 *  轮询待排序列表,找到当前菜单
		 *  重复 123步
		 *  不停的break
		 *  
		 */
		for(Menu menu :menuList){
			if(menu.getParentId() != null && menu.getParentId().equals(parentId)){
				sortMenuList.add(menu);
				for(Menu childMenu : menuList){
					if(childMenu.getParentId() != null && childMenu.getParentId().equals(parentId)){
						//开始递归
						sortMenuList(sortMenuList,menuList,menu.getId());
						break;
					}
				}
			}
		}
	}
	
	/**
	 *  轮询带排序列表,找到当前父节点的下一级某个节点
	 *  将当前节点放入指定的列表
	 *  轮询待排序列表,找到当前菜单
	 *  重复 123步
	 *  不停的break
	 *  
	 */
	public static <T> void sortTreeList(List<T> sortTreeList,List<T> treeList,Long parentId){
		for(int i=0,len=treeList.size();i<len;i++){
			TreeDto m = (TreeDto)treeList.get(i);
			if(m.getParentId() != null && m.getParentId().equals(parentId)){
				sortTreeList.add((T)m);
				for(T child : treeList){
					if(((TreeDto)child).getParentId() != null && ((TreeDto)child).getParentId().equals(parentId)){
						//开始递归
						sortTreeList(sortTreeList,treeList,m.getId());
						break;
					}
				}
			}
				
		}
	}
	
	/**
	 * 
	 * 获取指定节点下的所有的子节点(儿子,孙子)
	 * com.gg.ssm.framework.utils 
	 * 方法名：getAllChildrenMap
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月12日-上午10:35:37 
	 * @param childrenMap
	 * @param treeList
	 * @param parentId
	 * @return Map<Long,TreeDto>
	 * @exception 
	 * @since  1.0.0
	 */
	public static Map<Long, TreeDto> getAllChildrenMap(Map<Long, TreeDto> childrenMap,List<TreeDto> treeList,long parentId){
		for(TreeDto tree :treeList){
			if(tree.getParentId().longValue() == parentId){
				childrenMap.put(tree.getId(), tree);
				getAllChildrenMap(childrenMap, treeList, tree.getId());
			}
		}
		return childrenMap;
	}
}

