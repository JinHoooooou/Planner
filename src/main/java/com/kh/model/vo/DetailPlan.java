package com.kh.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DetailPlan {

  private static String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
  private static String LOCAL_TIME_FORMAT = "HH:mm";

  private int detailPlanId;
  private int planId;
  private String writer;
  private String contents;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private LocalDateTime remindAlarmTime;
  private String complete;
  private LocalDateTime createDate;

  public static DetailPlan from(ResultSet resultSet) throws SQLException {
    return DetailPlan.builder()
        .detailPlanId(resultSet.getInt("DETAIL_PLAN_ID"))
        .planId(resultSet.getInt("PLAN_ID"))
        .writer(resultSet.getString("WRITER"))
        .contents(resultSet.getString("CONTENTS"))
        .startTime(parse(resultSet.getString("START_TIME")))
        .endTime(parse(resultSet.getString("END_TIME")))
        .remindAlarmTime(parse(resultSet.getString("REMIND_ALARM_TIME")))
        .createDate(parse(resultSet.getString("CREATE_DATE")))
        .complete(resultSet.getString("COMPLETE"))
        .build();
  }

  private static LocalDateTime parse(String sqlDate) {
    return sqlDate == null ? null :
        LocalDateTime.parse(sqlDate, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT));
  }

  public String getStartDateString() {
    return this.getStartTime().format(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
  }

  public String getStartTimeString() {
    return this.getStartTime().format(DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT));
  }

  public String getEndTimeString() {
    return this.getEndTime().format(DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT));
  }
}
