package com.memo.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memo.bean.MemoBean;
import com.memo.dao.MemoInfoDao;
import com.memo.dao.UserInfoDao;
import com.memo.entity.MemoInfoEntity;
import com.memo.entity.UserInfoEntity;
import com.memo.service.MemoService;

@Service
public class MemoServiceImpl implements MemoService {
	
	@Autowired
	private UserInfoDao userDao;
	@Autowired
	private MemoInfoDao memoDao;
	
	/**
	 * 保存用户信息
	 * @param regisiter 用户信息集
	 */
	@Override
	public void saveUserInfo(Map<String, Object> regisiter) {
		UserInfoEntity userEntity = new UserInfoEntity();
		String name = (String) regisiter.get("userName");
		String password = (String) regisiter.get("password");
		String password2 = (String) regisiter.get("password2");
		UserInfoEntity user = userDao.findByName(name);
		if (user == null && password.equals(password2)) {
			userEntity.setUserName(name);
			userEntity.setPassword(password);
			userEntity.setSex((Integer) regisiter.get("sex"));
			userEntity.setEmail((String) regisiter.get("email"));
			userDao.save(userEntity);
		}	
		
	}
	
	/**
	 * 查找用户信息
	 * @param login 用户登录信息集
	 * @return 用户信息实体
	 */
	@Override
	public UserInfoEntity login(Map<String, String> login) {
		String name = login.get("userName");
		UserInfoEntity user = userDao.findByName(name);
		return user;
	}
	
	/**
	 * 按照姓名查找用户信息
	 * return:返回用户信息
	 */
	@Override
	public UserInfoEntity findUser(String name) {
		UserInfoEntity user = userDao.findByName(name);
		return user;
	}
	
	/**
	 * 修改用户密码
	 * @param name 用户名
	 * @param oldPass 用户原来的密码
	 * @param newPass 用户新密码
	 * @return 修改成功返回true，否则返回false
	 */
	@Override
	public Boolean changePassword(String name, String oldPass, String newPass) {
		UserInfoEntity user = userDao.findByName(name);
		if (oldPass.equals(user.getPassword())) {
			return userDao.updatePassword(name, newPass);
		}
		return false;
	}
	
	/**
	 * 更新用户信息
	 * @param user 用户信息实体
	 * @return 更新成功返回true
	 */
	@Override
	public Boolean updateUser(UserInfoEntity user) {
		return userDao.update(user);
	}
	
	/**
	 * 添加待办备忘
	 * @param thing 待办备忘集合
	 * @param userId 用户id
	 */
	@Override
	public void addMemo(Map<String, Object> memo,int userId) {
		MemoInfoEntity memoEntity = new MemoInfoEntity();
		memoEntity.setUserId(userId);
		memoEntity.setContent((String) memo.get("content"));
		memoEntity.setImportance((Integer) memo.get("importance"));
		memoEntity.setStatus(0);
		memoEntity.setReminder((Integer) memo.get("reminder"));
		Date date = new Date();
		memoEntity.setCreateTime(new Timestamp(date.getTime()));
		memoDao.save(memoEntity);
	}
	
	/**
	 * 查找待办备忘
	 * @param status 待办状态
	 * @return 返回待办备忘信息集合
	 */
	@Override
	public List<MemoBean> showMemo(Integer status){
		List<MemoInfoEntity> memoList =  new ArrayList<MemoInfoEntity>();
		if (status == 2) {
			memoList = memoDao.findAll();
		} else {
			memoList =  memoDao.findMemoByStatus(status);
		}
		if (!memoList.isEmpty()) {
			Iterator<MemoInfoEntity> it = memoList.iterator();
			List<MemoBean> memoBeanList = new ArrayList<MemoBean>();
			while (it.hasNext()) {
				MemoInfoEntity memoEntity = it.next();
				MemoBean memo = new MemoBean();
				
				memo.setId(memoEntity.getId());
				memo.setContent(memoEntity.getContent());
				memo.setImportance(memoEntity.getImportance());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				memo.setCreateTime(format.format(memoEntity.getCreateTime()));
				memo.setUserId(memoEntity.getUserId());
				memo.setUserName(userDao.findById(memoEntity.getUserId()));
				memo.setStatus(memoEntity.getStatus());
				memo.setReminder(memoEntity.getReminder());
				memoBeanList.add(memo);
			}
			return memoBeanList;
		}
		return null;
	}
	
	/**
	 * 通过id删除待办备忘
	 * @param id 待办备忘id
	 */
	@Override
	public void deleteMemo(int id) {
		memoDao.deleteById(id);
	}
	
	/**
	 * 查找待办备忘
	 * @param id 待办备忘id
	 * @return 返回待办备忘集合集
	 */
	@Override
	public MemoInfoEntity findMemo(int id){
		return memoDao.findById(id);
	}
	
	/**
	 * 修改待办备忘
	 * @param id 待办备忘id
	 * @param Memo 待办备忘集合集
	 * @param status 待办状态
	 */
	@Override
	@Transactional
	public void modifyMemo(Map<String, Object> memo){
		MemoInfoEntity memoEntity = new MemoInfoEntity();
		memoEntity.setContent((String) memo.get("content"));
		memoEntity.setCreateTime(Timestamp.valueOf((String) memo.get("createTime")));
		memoEntity.setId((Integer) memo.get("id"));
		memoEntity.setImportance((Integer) memo.get("importance"));
		memoEntity.setReminder((Integer) memo.get("reminder"));
		memoEntity.setStatus((Integer) memo.get("status"));
		memoEntity.setUserId((Integer) memo.get("userId"));
		memoDao.update(memoEntity);
	}
	
	
	/**
	 * 修改待办备忘状态
	 * @param id 待办备忘id
	 * @param status 待办状态
	 */
	@Override
	public void modifyStatus(int id){
		MemoInfoEntity memoInfo = memoDao.findById(id);
		int status = memoInfo.getStatus();
		if (status == 1) {
			memoInfo.setStatus(0);
		} else {
			memoInfo.setStatus(1);
		}
		memoDao.modify(memoInfo);
	}
	
}
