package com.jqq.dao;

import java.util.List;

import com.jqq.entity.SysUser;
/**
 * ϵͳ�û��ӿ�
 * @author jqq
 *
 */
public interface UserDao {
	public boolean login(String username, String password); //�û���¼
	public boolean register(SysUser user); //�û�ע��
	public List<SysUser> getSysUser(); //��ȡ�û����ݼ���
	public boolean deleteUser(Long userId); //�����û�idɾ���û�����
}
