package com.memo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "jiangcy_memoinfo")
@Component(value = "memoEntity")
public class MemoInfoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "userId")
	private int userId;
	@Column(name = "content")
	private String content;
	@Column(name = "createTime")
	private Timestamp createTime;
	@Column(name = "reminder")
	private int reminder;
	@Column(name = "status")
	private int status;
	@Column(name = "importance")
	private int importance;
	
	public MemoInfoEntity(){}
	
	public MemoInfoEntity(int userId, String content, Timestamp createTime, 
			int reminder, int status, int importance){
		this.userId = userId;
		this.content = content;
		this.createTime = createTime;
		this.reminder = reminder;
		this.status = status;
		this.importance = importance;
	}
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getReminder() {
		return reminder;
	}
	public void setReminder(int reminder) {
		this.reminder = reminder;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	

}
