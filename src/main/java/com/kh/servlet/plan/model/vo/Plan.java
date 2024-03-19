package com.kh.servlet.plan.model.vo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Plan {

	private int planId;
	private String writer;
	private String title;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private Date remindAlarmDate;
	private String complete;
	
	
	public Plan() {
		super();
	}

	
	public Plan(int planId, String writer, String title, Date startDate, Date endDate, Date createDate,
			Date remindAlarmDate, String complete) {
		super();
		this.planId = planId;
		this.writer = writer;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.remindAlarmDate = remindAlarmDate;
		this.complete = complete;
	}


	public Plan(String writer, String title, Date startDate, Date endDate, Date remindAlarmDate, String complete) {
		this.writer = writer;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remindAlarmDate = remindAlarmDate;
		this.complete = complete;
	}

	

	public Plan(String title, Date startDate, Date endDate, Date remindAlarmDate) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remindAlarmDate = remindAlarmDate;
	}


	public int getPlanId() {
		return planId;
	}


	public void setPlanId(int planId) {
		this.planId = planId;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getRemindAlarmDate() {
		return remindAlarmDate;
	}


	public void setRemindAlarmDate(Date remindAlarmDate) {
		this.remindAlarmDate = remindAlarmDate;
	}


	public String getComplete() {
		return complete;
	}


	public void setComplete(String complete) {
		this.complete = complete;
	}


	@Override
	public String toString() {
		return "Plan [planId=" + planId + ", writer=" + writer + ", title=" + title + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", createDate=" + createDate + ", remindAlarmDate=" + remindAlarmDate
				+ ", complete=" + complete + "]";
	}
	
	
	 
}
	
