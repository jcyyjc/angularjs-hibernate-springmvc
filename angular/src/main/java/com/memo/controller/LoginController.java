package com.memo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.entity.UserInfoEntity;
import com.memo.service.MemoService;

/**
 * Created by jiangcy on 2014年10月13日.
 */
@Controller
public class LoginController {
	@Autowired
	private MemoService memoService;

	/**
	 * 用户登录 return:返回登录状态的json成功true,失败false
	 */
	@RequestMapping(value = "/memo/logins", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> memo(@RequestBody Map<String, Object> login) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!login.isEmpty()) {
			String name = (String) login.get("userName");
			String password = (String) login.get("password");
			UserInfoEntity user = (UserInfoEntity) memoService.findUser(name);
			if (user== null) {
				map.put("check", false);
			} else if (!password.equals(user.getPassword())) {
				map.put("check", false);
			} else {
				map.put("check", true);
			}
		} else {
			map.put("check", false);
		}
		return map;
	}
	
	/**
	 * 用户注册
	 * @param regisiter
	 * @return 注册成功返回true,否则返回false
	 */
	@RequestMapping(value="/memo/regisiter",method=RequestMethod.PUT)
	@ResponseBody
 	public Map<String, Boolean> regisiter(@RequestBody Map<String, Object> regisiter){
		Map<String,Boolean> check = new HashMap<String, Boolean>();
		if (!regisiter.isEmpty()) {
			memoService.saveUserInfo(regisiter);
			check.put("check", true);
		} else {
			check.put("check", false);
		}
		return check;
	}
}
