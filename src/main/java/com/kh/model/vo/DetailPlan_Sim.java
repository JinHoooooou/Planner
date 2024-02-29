package com.kh.model.vo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DetailPlan_Sim {
	private int detailPlanId;
	private int planId;
	private String writer;
	private String contents;
	private Date startTime;
	private Date endTime;
	private Date remindAlarmTime;
	private Date createDate;
	private String complete;
	
	  public static DetailPlan_Sim from(ResultSet resultSet) throws SQLException {
		    return DetailPlan_Sim.builder()
		        .detailPlanId(resultSet.getInt("detaiil_plan_id"))
		        .planId(resultSet.getInt("plan_id"))
		        .writer(resultSet.getString("writer"))
		        .contents(resultSet.getString("contents"))
		        .startTime(resultSet.getDate("start_time"))
		        .endTime(resultSet.getDate("end_time"))
		        .remindAlarmTime(resultSet.getDate("remind_alarm_time"))
		        .createDate(resultSet.getDate("create_date"))
		        .build();
		  }
}
