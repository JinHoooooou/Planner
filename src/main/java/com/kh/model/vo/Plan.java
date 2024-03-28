package com.kh.model.vo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.JSONObject;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

  private int planId;
  private String writer;
  private String title;
  private Date startDate;
  private Date endDate;
  private Date remindAlarmDate;
  private String complete;
  private Date createDate;

  public static Plan from(ResultSet resultSet) throws SQLException {
    return Plan.builder()
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

  public static Plan from(HttpServletRequest req) {
    return Plan.builder()
        .writer(String.valueOf(req.getSession().getAttribute("userId")))
        .title(req.getParameter("title"))
        .startDate(Date.valueOf(req.getParameter("startDate")))
        .endDate(Date.valueOf(req.getParameter("endDate")))
        .remindAlarmDate(Date.valueOf(req.getParameter("remindAlarmDate")))
        .complete(String.valueOf(req.getParameter("complete")))
        .createDate(Date.valueOf("create_date"))
        .build();
  }

  public static Plan postRequestDto(HttpServletRequest req, Object user)
      throws NullPointerException, IllegalArgumentException {
    String title = req.getParameter("title");
    Date startDate = Date.valueOf(req.getParameter("startDate"));
    Date endDate = Date.valueOf(req.getParameter("endDate"));
    Date remindAlarmDate = req.getParameter("remindAlarmDate").isEmpty() ? null
        : Date.valueOf(req.getParameter("remindAlarmDate"));

    if (title == null || title.isEmpty()) {
      throw new ValidationException("Title이 유효하지 않습니다.");
    }
    if (startDate.after(endDate)) {
      throw new ValidationException("start/end date가 유효하지 않습니다.");
    }

    if (remindAlarmDate != null
        && (remindAlarmDate.before(startDate) || remindAlarmDate.after(endDate))) {
      throw new ValidationException("alarm date가 유효하지 않습니다.");
    }

    return Plan.builder()
        .writer(String.valueOf(user))
        .title(title)
        .startDate(startDate)
        .endDate(endDate)
        .remindAlarmDate(remindAlarmDate)
        .complete("N")
        .build();
  }

  public JSONObject responseDto() {
    JSONObject result = new JSONObject();
    result.put("planId", this.getPlanId());
    result.put("title", this.getTitle());
    result.put("startDate", this.getStartDate());
    result.put("endDate", this.getEndDate());
    result.put("remindAlarmDate", this.getRemindAlarmDate());
    result.put("complete", this.getComplete());

    return result;
  }

  public Plan putRequestDto(JSONObject requestBody) {
    String updateTitle = requestBody.getString("title");
    String updateStartDate = requestBody.getString("startDate");
    String updateEndDate = requestBody.getString("endDate");
    String updateRemindAlarmDate = requestBody.getString("remindAlarmDate");

    if (updateTitle == null || updateTitle.isEmpty()) {
      throw new ValidationException("Title이 유효하지 않습니다.");
    }

    if (updateStartDate == null || updateStartDate.isEmpty()
        || updateEndDate == null || updateEndDate.isEmpty()) {
      throw new ValidationException("start/end date가 유효하지 않습니다.");
    }

    if (Date.valueOf(updateStartDate).after(Date.valueOf(updateEndDate))) {
      throw new ValidationException("start/end date가 유효하지 않습니다.");
    }

    if (updateRemindAlarmDate == null) {
      throw new ValidationException("alarm date가 유효하지 않습니다.");
    }
    if (!updateRemindAlarmDate.isEmpty()
        && (Date.valueOf(updateRemindAlarmDate).before(Date.valueOf(updateStartDate))
        || Date.valueOf(updateRemindAlarmDate).after(Date.valueOf(updateEndDate)))) {
      throw new ValidationException("alarm date가 유효하지 않습니다.");
    }

    this.setTitle(updateTitle.trim());
    this.setStartDate(Date.valueOf(updateStartDate));
    this.setEndDate(Date.valueOf(updateEndDate));
    this.setRemindAlarmDate(
        updateRemindAlarmDate.isEmpty() ? null : Date.valueOf(updateRemindAlarmDate));

    return this;
  }
}
