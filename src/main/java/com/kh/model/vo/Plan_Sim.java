package com.kh.model.vo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan_Sim {

	private int planId;
	private String writer;
	private String title;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private Date remindAlarmDate;
	private String complete;
	
	public Plan_Sim(String writer, String title, Date startDate, Date endDate, Date remindAlarmDate, String complete) {
		this.writer = writer;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remindAlarmDate = remindAlarmDate;
		this.complete = complete;
	}
	 
	public static Plan_Sim from(ResultSet resultSet) throws SQLException {
		    return Plan_Sim.builder()
		        .planId(resultSet.getInt("PLAN_ID"))
		        .writer(resultSet.getString("WRITER"))
		        .title(resultSet.getString("TITLE"))
		        .startDate(resultSet.getDate("START_DATE"))
		        .endDate(resultSet.getDate("END_DATE"))
		        .createDate(resultSet.getDate("CREATE_DATE"))
		        .remindAlarmDate(resultSet.getDate("REMIND_ALARM_DATE"))
		        .complete(resultSet.getString("COMPLETE"))
		        .build();
		  }
	
}
