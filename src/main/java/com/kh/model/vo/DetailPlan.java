package com.kh.model.vo;

import com.kh.model.dao.PlanDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@Builder
public class DetailPlan {

  private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static String DATE_FORMAT = "yyyy-MM-dd";
  private static String TIME_FORMAT = "HH:mm";

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
        .startTime(parseLocalDateTime(resultSet.getString("START_TIME")))
        .endTime(parseLocalDateTime(resultSet.getString("END_TIME")))
        .remindAlarmTime(parseLocalDateTime(resultSet.getString("REMIND_ALARM_TIME")))
        .createDate(parseLocalDateTime(resultSet.getString("CREATE_DATE")))
        .complete(resultSet.getString("COMPLETE"))
        .build();
  }

  private static LocalDateTime parseLocalDateTime(String sqlDate) {
    return sqlDate == null ? null :
        LocalDateTime.parse(sqlDate, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
  }

  public static DetailPlan postRequestDto(HttpServletRequest req, Object user)
      throws NullPointerException, IllegalArgumentException {
    String planId = req.getParameter("planIdForDetail");
    String contents = req.getParameter("detailContents");
    String startDate = req.getParameter("detailStartDate");
    String startTime = req.getParameter("detailStartTime");
    String endTime = req.getParameter("detailEndTime");

    if (startDate.isEmpty()) {
      throw new ValidationException("start date가 유효하지 않습니다.");
    }

    DetailPlan newDetail = DetailPlan.builder()
        .planId(Integer.parseInt(planId))
        .writer(String.valueOf(user))
        .contents(contents.trim())
        .startTime(LocalDateTime.parse(startDate + startTime, DateTimeFormatter.ofPattern(DATE_FORMAT + TIME_FORMAT)))
        .endTime(LocalDateTime.parse(startDate + endTime, DateTimeFormatter.ofPattern(DATE_FORMAT + TIME_FORMAT)))
        .complete("N")
        .build();

    Plan parent = new PlanDao().findByPlanId(Integer.parseInt(planId));
    LocalDateTime planStartDate = parent.getStartDate().toLocalDate().atStartOfDay();
    LocalDateTime planEndDate = parent.getEndDate().toLocalDate().plusDays(1).atStartOfDay();

    if (newDetail.getStartTime().isBefore(planStartDate) || newDetail.getStartTime().isAfter(planEndDate)) {
      throw new ValidationException("start date가 유효하지 않습니다.");
    }

    if (newDetail.getStartTime().isAfter(newDetail.getEndTime())) {
      throw new ValidationException("start/end time이 유효하지 않습니다.");
    }

    return newDetail;
  }

  public DetailPlan putRequestDto(JSONObject requestBody) throws NullPointerException, IllegalArgumentException {
    String updateContents = requestBody.getString("updateContents");
    String updateStartDate = requestBody.getString("updateStartDate");
    String updateStartTime = requestBody.getString("updateStartTime");
    String updateEndTime = requestBody.getString("updateEndTime");
    String updateAlarmTime = requestBody.getString("updateRemindAlarmTime");

    this.setContents(updateContents.trim());
    this.setStartTime(LocalDateTime.parse(updateStartDate + updateStartTime,
        DateTimeFormatter.ofPattern(DATE_FORMAT + TIME_FORMAT)));
    this.setEndTime(LocalDateTime.parse(updateStartDate + updateEndTime,
        DateTimeFormatter.ofPattern(DATE_FORMAT + TIME_FORMAT)));
    this.setRemindAlarmTime(updateAlarmTime.isEmpty() ? null : LocalDateTime.parse(updateAlarmTime));

    Plan parent = new PlanDao().findByPlanId(this.getPlanId());
    LocalDateTime planStartDate = parent.getStartDate().toLocalDate().atStartOfDay();
    LocalDateTime planEndDate = parent.getEndDate().toLocalDate().plusDays(1).atStartOfDay();

    if (this.getStartTime().isBefore(planStartDate) || this.getStartTime().isAfter(planEndDate)) {
      throw new ValidationException("start date가 유효하지 않습니다.");
    }

    if (this.getStartTime().isAfter(this.getEndTime())) {
      throw new ValidationException("start/end time이 유효하지 않습니다.");
    }

    return this;
  }

  public JSONObject parseJson() {
    JSONObject result = new JSONObject();
    result.put("detailPlanId", this.getDetailPlanId());
    result.put("planId", this.getPlanId());
    result.put("contents", this.getContents());
    result.put("startDate", this.getStartDateString());
    result.put("startTime", this.getStartTimeString());
    result.put("endTime", this.getEndTimeString());
    result.put("remindAlarmTime", this.getRemindAlarmTime());
    result.put("complete", this.getComplete());

    return result;
  }

  public String getStartDateString() {
    return this.getStartTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
  }

  public String getStartTimeString() {
    return this.getStartTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
  }

  public String getEndTimeString() {
    return this.getEndTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
  }
}
