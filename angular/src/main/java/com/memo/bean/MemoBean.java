package com.memo.bean;

/**
 *Created by jiangcy on 2014年10月10日.
 */
public class MemoBean {
	
	private int id;
	private int userId;
	private String userName;
	private String content;
	private String createTime;
	private int importance;
	private int status;
	private int reminder;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReminder() {
		return reminder;
	}

	public void setReminder(int reminder) {
		this.reminder = reminder;
	}	

}
