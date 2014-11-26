package com.memo.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.memo.dao.UserInfoDao;
import com.memo.entity.UserInfoEntity;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * 保存用户信息
	 * @param user 用户信息实体
	 */
	@Override
	@Transactional
	public void save(UserInfoEntity user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	/**
	 * 删除用户信息
	 * @param id 用户id
	 */
	@Override
	@Transactional
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("Delete from UserInfoEntity u where u.id = ?");
		query.setInteger(0, id);
		query.executeUpdate();
	}

	/**
	 * 更新用户信息
	 * @param user 用户信息实体
	 */
	@Override
	@Transactional
	public boolean update(UserInfoEntity user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
		return true;
	}

	/**
	 * 通过用户名查找用户信息
	 * @param name 用户名
	 * @return 返回用户实体
	 */
	@Override
	@Transactional
	public UserInfoEntity findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserInfoEntity u where u.userName = ?");
		query.setString(0, name);
		UserInfoEntity user = (UserInfoEntity) query.uniqueResult();
		return user;
	}

	/**
	 * 通过id寻找用户信息
	 * return:返回用户姓名
	 */
	@Override
	@Transactional
	public String findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserInfoEntity u where u.id = ?");
		query.setInteger(0, id);
		UserInfoEntity user = (UserInfoEntity) query.uniqueResult();
		String name = user.getUserName();
		return name;
	}
	
	/**
	 * 根据用户名，修改用户的密码
	 * @param name 用户名
	 * @param password 密码
	 * @return 更新成功返回true
	 */
	@Override
	@Transactional
	public Boolean updatePassword(String name, String password) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserInfoEntity u set u.password = ? where u.userName = ?");
		query.setString(0, password);
		query.setString(1, name);
		query.executeUpdate();
		return true;
	}
	
}
