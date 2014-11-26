package com.memo.dao;

import com.memo.entity.UserInfoEntity;

public interface UserInfoDao {
	
	/**
	 * 保存用户信息
	 * @param user 用户信息实体
	 */
	public void save(UserInfoEntity user);
	/**
	 * 删除用户信息
	 * @param id 用户id
	 */
	public void delete(int id);
	/**
	 * 更新用户信息
	 * @param user 用户信息实体
	 */
	public boolean update(UserInfoEntity user);
	/**
	 * 通过用户名查找用户信息
	 * @param name 用户名
	 * @return 返回用户实体
	 */
	public UserInfoEntity findByName(String name);
	/**
	 * 通过id寻找用户信息
	 * return:返回用户姓名
	 */
	public String findById(int id);
	/**
	 * 根据用户名，修改用户的密码
	 * @param name 用户名
	 * @param password 密码
	 * @return 更新成功返回true
	 */
	public Boolean updatePassword(String name, String password);

}
