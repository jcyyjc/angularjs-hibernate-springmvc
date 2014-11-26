package com.memo.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memo.bean.MemoBean;
import com.memo.entity.MemoInfoEntity;
import com.memo.entity.UserInfoEntity;
import com.memo.service.MemoService;

@Controller
@SuppressWarnings("rawtypes")
public class MemoController {
	
	
	@Autowired
	private MemoService memoService;
	
//	/**
//	 * 用户注册
//	 * @param regisiter
//	 * @return
//	 */
//	@RequestMapping(value="/memo/regisiters",method=RequestMethod.PUT)
//	@ResponseBody
// 	public Map regisiter(@RequestBody Map<String,String> regisiter){
//		Map<String,Boolean> check = new HashMap<String, Boolean>();
//		if (!regisiter.isEmpty()) {
//			memoService.saveUserInfo(regisiter);
//			check.put("check", true);
//		} else {
//			check.put("check", false);
//		}
//		return check;
//	}
//	
	/**
	 * 删除待办备忘
	 * @return 返回删除后的待办备忘
	 */
	@RequestMapping(value="/memo/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id){
		if (id != null) {
			memoService.deleteMemo(id);
		}
	}
	
	
	/**
	 * 获取待办备忘列表
	 * @return 返回的待办备忘列表
	 */
	
	@RequestMapping(value="/memo/{status}",method=RequestMethod.GET)
	@ResponseBody
	public List showTodos(@PathVariable Integer status){
		if (status != null) {
			List<MemoBean> memo = memoService.showMemo(status);
			return memo;
		}
		return null;
	}
	
	/**
	 * 添加待办备忘
	 */
	@RequestMapping(value="/memo",method=RequestMethod.PUT)
	@ResponseBody
	public Map<String,Object> add(@RequestBody Map<String, Object> memo){
		Integer userId = (Integer) memoService.findUser((String) memo.get("userName")).getId();
		Map<String,Object> map = new HashMap<String,Object>();
		if (userId == null) {
			map.put("check", false);
		} else {
			memoService.addMemo(memo, userId);
			map.put("check", true);
		}
		return map;
	}
	
	/**
	 * 修改待办备忘的内容及重要性
	 * @return 返回待办备忘列表
	 */
	@RequestMapping(value="/memo/modify", method=RequestMethod.PUT)
	@ResponseBody
	public void modify(@RequestBody Map<String, Object> memo){
		if (!memo.isEmpty()) {
			memoService.modifyMemo(memo);
		}
	}
	
	/**
	 * 通过id查找对应的待办备忘内容
	 * @return 返回待办备忘的的具体内容
	 */
	@RequestMapping(value="/memo/modify/{id}", method=RequestMethod.GET)
	@ResponseBody
	public MemoInfoEntity  findMemo(@PathVariable Integer id){
		if (id != null) {
			MemoInfoEntity memo = (MemoInfoEntity) memoService.findMemo(id);
			if(memo == null){
				return null;
			}
			return memo;
		}
		return null;
	} 
	
	/**
	 * 更改待办备忘的完成状态
	 * @return 返回待办备忘内容
	 */
	@RequestMapping(value="/memo/modify/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void changeStatus(@PathVariable Integer id) {
		if (id != null) {
			memoService.modifyStatus(id);
		}
	}
	
	/**
	 * 获取用户详细信息
	 * @param name 用户名
	 * @return 返回用户信息实体
	 */
	@RequestMapping(value="/memo/user/{name}", method = RequestMethod.GET)
	@ResponseBody
	public UserInfoEntity getUserInfo(@PathVariable String name) {
		if (name != null) {
			return memoService.findUser(name);
		}
		return null;
	}
	
	/**
	 * 用户修改密码
	 * @param name 用户名
	 * @param password 用户密码
	 */
	@RequestMapping(value = "/memo/user", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Boolean> changePassword(@RequestBody Map<String, String> password) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (!password.isEmpty()) {
			Boolean flag = memoService.changePassword(password.get("userName"), password.get("oldPass"), password.get("newPass"));
			map.put("check", flag);
		} else {
			map.put("check", false);
		}
		return map;
	}

	/**
	 * 更新用户信息
	 * @param user 用户信息实体
	 * @return 更新成功返回true，否则false
	 */
	@RequestMapping(value = "/memo/user/modify", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Boolean> updateUserInfo(@RequestBody UserInfoEntity user) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (user != null) {
			boolean flag = memoService.updateUser(user);
			map.put("check", flag);
		} else {
			map.put("check", false);
		}
		return map;
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public void fileUpload(@RequestParam MultipartFile fileUp, HttpServletRequest request) {
		System.out.println(fileUp.getOriginalFilename());
		System.out.println(fileUp.getContentType());
		System.out.println(fileUp.getSize());
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
		try {
			FileUtils.copyInputStreamToFile(fileUp.getInputStream(), new File(path,fileUp.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
