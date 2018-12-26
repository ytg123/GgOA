package com.gg.ssm.mapper;
/**
 * 用户增删改查以及登录验证的mapper代理接口
 * 
 * UserMapper
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月6日-上午12:37:12 
 * @version 1.0.0
 *
 */

import java.util.List;

import com.gg.ssm.Vo.UserVo;
import com.gg.ssm.dto.UserDto;
import com.gg.ssm.entity.User;
import com.gg.ssm.entity.UserToRole;
import com.gg.ssm.framework.dto.PageParam;
import com.github.pagehelper.PageInfo;

public interface UserMapper {
	//登录验证
	public List<User> getUserList(User user);
	//通过userId后去用户对象
	public User getUserById(Long userId);
	//修改密码
	public boolean updatePsd(Long userId,String newPsd);
	//通过userId获取用户信息列表包含部门名称及角色列表
	public UserDto getUserInfoById(Long userId);
	//修改用户信息
	public boolean updateUserInfo(User user);
	//查询用户列表 分页
	public PageInfo<UserDto> getUserList(User user,PageParam pageParam);
	//分页查询用户信息
	public List<UserDto> getUserDtoList(User user);
	//新增用户 包含用户角色对应表
	public boolean addUser(User user);
	//批量添加角色用户信息对应表
	public boolean addUserRoleBatch(List<UserToRole> userRoleList);
	//删除角色用户信息对应表
	public boolean deleteUserRoleByUserId(Long userId);
	//删除用户信息
	public boolean deleteUser(Long userId);
	//查询某个用户所拥有的角色信息
	public List<UserToRole> getUserRoleByUserId(Long userId);
}
