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
public class DetailPlan_kim {

	private int detailPlanId;
	private int planId;
	private String Writer;
	private String Contents;
	private Date startTime;
	private Date endTime;
	private Date remindAlarmTime;
	private String Complete;
	private Date createDate;

	public DetailPlan_kim(int detailPlanId, int planId, String Writer, String Contents, Date startTime, Date endTime,
			Date remindAlarmTime, Date createDate, String Complete) {
		this.detailPlanId = detailPlanId;
		this.planId = planId;
		this.Writer = Writer;
		this.Contents = Contents;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remindAlarmTime = remindAlarmTime;
		this.Complete = Complete;
		this.createDate = createDate;

	}

	public static DetailPlan_kim from(ResultSet resultSet) throws SQLException {
	    return DetailPlan_kim.builder()
		        .detailPlanId(resultSet.getInt("DETAIL_PLAN_ID"))
		        .planId(resultSet.getInt("PLAN_ID"))
		        .Writer(resultSet.getString("WRITER  "))
		        .Contents(resultSet.getString("CONTENTS "))
		        .startTime(resultSet.getDate("START_TIME "))
		        .endTime(resultSet.getDate("END_TIME"))
		        .remindAlarmTime(resultSet.getDate("REMIND_ALARM_TIME"))
		        .Complete(resultSet.getString("COMPLETE"))
		        .createDate(resultSet.getDate("CREATE_DATE"))		        
		        .build();
		
	}

}
