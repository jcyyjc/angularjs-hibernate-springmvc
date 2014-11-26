package com.memo.dao;

import java.util.List;

import com.memo.entity.MemoInfoEntity;

public interface MemoInfoDao {
	
	/**
	 * 保存待办备忘实体
	 * @param memo 待办实体
	 */
	public void save(MemoInfoEntity memo);
	/**
	 * 通过id删除待办备忘实体
	 * @param id 待办备忘id
	 */
	public void deleteById(int id);
	/**
	 * 更新待办备忘实体
	 * @param memo 待办实体
	 */
	public void update(MemoInfoEntity memo);
	/**
	 * 查找所有待办备忘实体
	 * @return 返回待办备忘实体列表
	 */
	public List<MemoInfoEntity> findMemoByStatus(Integer status);
	/**
	 * 查找所有的待办备忘实体
	 * @return 返回备忘实体列表
	 */
	public List<MemoInfoEntity> findAll();
	/**
	 * 通过id查找待办备忘实体
	 * @param id 待办备忘id
	 * @return 返回待办备忘实体
	 */
	public MemoInfoEntity findById(int id);
	/**
	 * 修改待办备忘
	 * @param memo 待办备忘实体
	 */
	public void modify(MemoInfoEntity memo);

}
