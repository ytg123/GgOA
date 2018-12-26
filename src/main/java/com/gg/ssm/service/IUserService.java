package com.gg.ssm.service;

import java.util.List;

import com.gg.ssm.Vo.UserVo;
import com.gg.ssm.dto.UserDto;
import com.gg.ssm.entity.User;
import com.gg.ssm.entity.UserToRole;
import com.gg.ssm.framework.dto.PageParam;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 用户业务处理接口
 * IUserService
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月6日-上午12:33:02 
 * @version 1.0.0
 *
 */
public interface IUserService {
	//登录
	public User loginUser(String loginName,String password);
	//通过userId后去用户对象
	public User getUserById(Long userId);
	//校验密码
	public boolean validatorPassword(String plainPsd,String encyptPsd);
	//修改密码
	public boolean updatePsd(Long userId,String newPsd);
	//密码加密
	public String encryptPsd(String plainPsd);
	//通过userId获取用户信息列表包含部门名称及角色列表
	public UserDto getUserInfoById(Long userId);
	//修改用户信息
	public boolean updateUserInfo(User user);
	//查询用户列表 分页
	public PageInfo<UserDto> getUserDtoList(User user,PageParam pageParam);
	//新增用户信息 包含用户角色对应表
	public boolean addUser(UserVo userVo);
	//删除用户信息和用户角色对应表
	public boolean  deleteUser(Long userId);
	//通过userId查询角色用户对应表
	public List<UserToRole> getUserRoleByUserId(Long userId);
	//更新用户信息以及用户角色对应表
	public boolean updateUserVo(UserVo userVo);
}
