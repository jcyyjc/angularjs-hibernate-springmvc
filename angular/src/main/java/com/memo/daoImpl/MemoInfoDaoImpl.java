package com.memo.daoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.memo.dao.MemoInfoDao;
import com.memo.entity.MemoInfoEntity;

@Repository
@SuppressWarnings("unchecked")
public class MemoInfoDaoImpl implements MemoInfoDao {

	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 保存待办备忘
	 */
	@Override
	@Transactional
	public void save(MemoInfoEntity memo) {		
		Session session = sessionFactory.getCurrentSession();
		session.save(memo);
	}
	
	/**
	 * 通过id删除待办备忘
	 * @param id 备忘的id
	 */
	@Override
	@Transactional
	public void deleteById(int id) {		
		Session session = sessionFactory.openSession();
		String hql = "delete from MemoInfoEntity where id = ?";
		Query query = session.createQuery(hql);
		query.setInteger(0, id);
		query.executeUpdate();
	}

	/**
	 * 更新待办备忘实体
	 */
	@Override
	@Transactional
	public void update(MemoInfoEntity memo) {
		Session session = sessionFactory.getCurrentSession();
		session.update(memo);
	}

	/**
	 * 按备忘的状态查找待办备忘
	 * @return 返回待办备忘实体列表
	 */
	@Override
	@Transactional
	public List<MemoInfoEntity> findMemoByStatus(Integer status) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from MemoInfoEntity m where m.status = :status order by createTime desc");
		query.setInteger("status", status);
		return query.list();
	}
	
	/**
	 * 查找所有的待办备忘实体
	 * @return 返回备忘实体列表
	 */
	@Override
	@Transactional
	public List<MemoInfoEntity> findAll() {
		Session session =sessionFactory.getCurrentSession();
		Query query = session.createQuery("from MemoInfoEntity order by createTime desc");
		return query.list();
	}
	
	/**
	 * 通过id查找待办备忘
	 * @return 返回待办备忘实体
	 */
	@Override
	@Transactional
	public MemoInfoEntity findById(int id) {		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from MemoInfoEntity m where m.id = ?");
		query.setInteger(0, id);
		return (MemoInfoEntity) query.uniqueResult();
	}
	
	/**
	 * 修改待办备忘
	 * @param memo 待办备忘实体
	 */
	@Override
	@Transactional
	public void modify(MemoInfoEntity memo){
		Session session = sessionFactory.getCurrentSession();
		session.merge(memo);
	}
	
}
