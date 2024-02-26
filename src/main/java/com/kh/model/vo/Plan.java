package com.kh.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

	private int planId;
	private String writer;
	private String title;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private Date remindAlarmDate;
	private String complete;
	
}
