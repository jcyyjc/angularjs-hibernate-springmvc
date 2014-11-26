package com.memo.service;

import java.util.List;
import java.util.Map;

import com.memo.bean.MemoBean;
import com.memo.entity.MemoInfoEntity;
import com.memo.entity.UserInfoEntity;

public interface MemoService {
	
	/**
	 * 保存用户信息
	 * @param regisiter 用户信息集
	 */
	public void saveUserInfo(Map<String, Object> regisiter);
	/**
	 * 查找用户信息
	 * @param login 用户登录信息集
	 * @return 用户信息实体
	 */
	public UserInfoEntity login(Map<String, String> login);
	/**
	 * 按照用户姓名查找用户信息
	 * @param name 用户名
	 * @return 返回用户信息实体
	 */
	public UserInfoEntity findUser(String name);
	/**
	 * 修改用户密码
	 * @param name 用户名
	 * @param oldPass 用户原来的密码
	 * @param newPass 用户新密码
	 * @return 修改成功返回true，否则返回false
	 */
	public Boolean changePassword(String name, String oldPass, String newPass);
	/**
	 * 更新用户信息
	 * @param user 用户信息实体
	 * @return 更新成功返回true
	 */
	public Boolean updateUser(UserInfoEntity user);
	/**
	 * 添加待办备忘
	 * @param thing 待办备忘集合
	 * @param userId 用户id
	 */
	public void addMemo(Map<String, Object> memo,int userId);
	/**
	 * 查找待办备忘
	 * @param status 待办状态
	 * @return 返回待办备忘信息集合
	 */
	public List<MemoBean> showMemo(Integer status);
	/**
	 * 通过id删除待办备忘
	 * @param id 待办备忘id
	 */
	public void deleteMemo(int id);
	/**
	 * 查找待办备忘
	 * @param id 待办备忘id
	 * @return 返回待办备忘集合集
	 */
	public MemoInfoEntity findMemo(int id);
	/**
	 * 修改待办备忘
	 * @param id 待办备忘id
	 * @param Memo 待办备忘集合集
	 * @param status 待办状态
	 */
	public void modifyMemo(Map<String, Object> memo);
	/**
	 * 修改待办备忘状态
	 * @param id 待办备忘id
	 * @param status 待办状态
	 */
	public void modifyStatus(int id);

}
