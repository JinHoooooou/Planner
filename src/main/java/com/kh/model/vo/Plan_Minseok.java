package com.kh.model.vo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Plan_Minseok {
	private int planId;
	private String writer;
	private String title;
	private Date startDate;
	private Date endDate;
	private Date remindAlarmDate;
	private String complete;
	private LocalDate createDate;

	public static Plan_Minseok from(ResultSet resultSet) throws SQLException {
		return Plan_Minseok.builder()
				.planId(resultSet.getInt("plan_id"))
				.writer(resultSet.getString("writer"))
				.title(resultSet.getString("title"))
				.startDate(resultSet.getDate("start_date"))
				.endDate(resultSet.getDate("end_date"))
				.remindAlarmDate(resultSet.getDate("remind_alarm_date"))
				.complete(resultSet.getString("complete"))
				.createDate(resultSet.getDate("create_date").toLocalDate())
				.build();
	}
}
