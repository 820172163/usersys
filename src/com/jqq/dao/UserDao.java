package com.jqq.dao;

import java.util.List;

import com.jqq.entity.SysUser;
/**
 * 系统用户接口
 * @author jqq
 *
 */
public interface UserDao {
	public boolean login(String username, String password); //用户登录
	public boolean register(SysUser user); //用户注册
	public List<SysUser> getSysUser(); //获取用户数据集合
	public boolean deleteUser(Long userId); //根据用户id删除用户数据
}
