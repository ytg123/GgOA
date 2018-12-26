package com.gg.ssm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gg.ssm.Vo.UserVo;
import com.gg.ssm.dto.UserDto;
import com.gg.ssm.entity.User;
import com.gg.ssm.entity.UserToRole;
import com.gg.ssm.framework.dto.PageParam;
import com.gg.ssm.framework.utils.EncryptUtil;
import com.gg.ssm.framework.utils.UserInfo;
import com.gg.ssm.mapper.UserMapper;
import com.gg.ssm.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 用户业务接口的实现
 * IUserServiceImpl
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月6日-上午12:36:07 
 * @version 1.0.0
 *
 */
@Service
public class IUserServiceImpl implements IUserService{
	//常量
	public static final int SALT_SIZE = 8;
	public static final int HASH_ITERATIONS = 1024;
	@Autowired
	private UserMapper userMapper;
	//登录实现
	@Override
	public User loginUser(String loginName, String password) {
		//根据loginName查出用户对象,用查出来的密码与界面的密码对比
		User user = new User();
		user.setLoginName(loginName);
		List<User> uList = userMapper.getUserList(user);
		if(uList.isEmpty()){
			return null;
		}else{
			String newEncypt = uList.get(0).getPassword();
			if(validatorPassword(password,newEncypt)){
				return uList.get(0);
			}else{
				return null;
			}
		}
	}

	/**
	 * 
	 * 密码校验方法
	 * com.gg.ssm.controller 
	 * 方法名：validatorPassword
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月7日-上午12:41:36 
	 * @param plainPsd
	 * @param encyptPsd
	 * @return boolean
	 * @exception 
	 * @since  1.0.0
	 */
	public boolean validatorPassword(String plainPsd,String encyptPsd){
		boolean flag = false;
		//将密文逆转,截取salt
		byte[] salt = EncryptUtil.decodeHex(encyptPsd.substring(0, SALT_SIZE*2));
		//重新将  salt  plainPsd  迭代次数   进行加密
		byte[] hashPassword = EncryptUtil.sha1(plainPsd.getBytes(), salt, HASH_ITERATIONS);
		//将salt + hashPassword拼在一起
		String newEnPsd = EncryptUtil.encodeHex(salt) + EncryptUtil.encodeHex(hashPassword);
		flag = newEnPsd.equals(encyptPsd);
		System.out.println(flag);
		return flag;	
	}
	/**
	 * 
	 * 密码加密
	 * com.gg.ssm.serviceImpl 
	 * 方法名：encryptPsd
	 * 创建人:Tengguang Yang
	 * 手机：18734153794
	 * 时间：2017年7月7日-下午2:58:54 
	 * @param plainPsd
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	public String encryptPsd(String plainPsd){
		/**
		 *  加密步骤:
		 *  	1:生成一个随机数
		 *  	2:用可逆的加密算法加密随机数(hex)
		 *  	3:将随机数和我们的密码用 不可逆加密算法加密 (sha1)
		 *  	4:将第三部得到的字符串用可逆的加密算法加密
		 *		5:将第二步和第四步的值拼在一起就是我们需要的加密密码
		 */
		//1
		byte[] radom = EncryptUtil.generateSalt(8);
		//2
		String radomHex = EncryptUtil.encodeHex(radom);
		//3
		byte[] thirdEn = EncryptUtil.sha1(plainPsd.getBytes(), radom, 1024);
		//4
		String foursEn = EncryptUtil.encodeHex(thirdEn);
		//5  75482c3e0c7cffe048c8c527deef6279ebc6a3a856434c7639aa669b
		String finalEn = radomHex + foursEn;
		return finalEn;
	}
	/**
	 * 通过userId获取用户对象
	 */
	@Override
	public User getUserById(Long userId) {
		User user = userMapper.getUserById(userId);
		return user;
	}
	/**
	 * 修改密码
	 */
	@Override
	public boolean updatePsd(Long userId, String newPsd) {
		//将新密加密
		String encryPsd = this.encryptPsd(newPsd);
		return  userMapper.updatePsd(userId, encryPsd);
	}
	/**
	 * 通过userId获取用户信息列表包含部门名称及角色列表
	 */
	@Override
	public UserDto getUserInfoById(Long userId) {
		UserDto userDto = userMapper.getUserInfoById(userId);
		return userDto;
	}
	/**
	 * 更改用户信息
	 */
	@Override
	public boolean updateUserInfo(User user) {
		return userMapper.updateUserInfo(user);
	}
	/**
	 * 分页查询用户列表
	 */
	@Override
	public PageInfo<UserDto> getUserDtoList(User user, PageParam pageParam) {
		//开始分页
		PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());		
		List<UserDto> userList = this.userMapper.getUserDtoList(user);
		PageInfo<UserDto> pageInfo = new PageInfo<UserDto>(userList);
		return pageInfo;
	}
	//新增用户信息 包含用户角色对应表
	//事务
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean addUser(UserVo userVo) {
		boolean flag = false;
		//新增时,注意填充当前用户和时间
		User user = userVo.getUser();
		user.setUpdateBy(UserInfo.getCurrentUserName());
		user.setUpdateDate(new Date());
		//密码,默认密码
		String pwd = "123456";
		String encryPsd = this.encryptPsd(pwd);
		user.setPassword(encryPsd);
		//添加
		flag = this.userMapper.addUser(user);
		Long userId = user.getUserId();
		//增加后,需要将用户和角色对应关系加入到用户角色对应表
		List<UserToRole> userRoleList = new ArrayList<UserToRole>();
		UserToRole userRole;
		for(Long roleId :userVo.getRoleIds().values()){
			userRole = new UserToRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRoleList.add(userRole);
		}
		flag = this.userMapper.addUserRoleBatch(userRoleList);
		return flag;
	}
	//删除用户信息和用户角色对应表
	//事务
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean deleteUser(Long userId) {
		boolean flag = false;
		flag = this.userMapper.deleteUserRoleByUserId(userId);
		flag = this.userMapper.deleteUser(userId);
		return flag;
	}
	//查询某个用户所拥有的角色
	@Override
	public List<UserToRole> getUserRoleByUserId(Long userId) {
		return this.userMapper.getUserRoleByUserId(userId);
	}
	//更新用户信息以及用户角色对应表
	//事务
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public boolean updateUserVo(UserVo userVo) {
		boolean flag = false;
		//新增时,注意填充当前用户和时间
		User user = userVo.getUser();
		user.setUpdateBy(UserInfo.getCurrentUserName());
		user.setUpdateDate(new Date());
		//删除
		Long userId = user.getUserId();
		flag = userMapper.deleteUserRoleByUserId(userId);
		//增加后,需要将用户和角色对应关系加入到用户角色对应表
		List<UserToRole> userRoleList = new ArrayList<UserToRole>();
		UserToRole userRole;
		for(Long roleId :userVo.getRoleIds().values()){
			userRole = new UserToRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRoleList.add(userRole);
		}
		flag = this.userMapper.addUserRoleBatch(userRoleList);
		//更新自己
		flag = userMapper.updateUserInfo(user);
		return flag;
	}
}
