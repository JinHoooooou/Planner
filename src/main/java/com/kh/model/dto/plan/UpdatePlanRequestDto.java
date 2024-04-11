package com.kh.model.dto.plan;

import com.kh.constant.Message;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePlanRequestDto {

  private int planId;
  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate alarmDate;

  public static UpdatePlanRequestDto from(HttpServletRequest request) {
    try {
      String title = request.getParameter("title");
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      String alarmDate = request.getParameter("alarmDate");

      return UpdatePlanRequestDto.builder()
          .title(title)
          .startDate(parseLocalDate(startDate))
          .endDate(parseLocalDate(endDate))
          .alarmDate(parseLocalDate(alarmDate))
          .build();
    } catch (Exception e) {
      throw new RuntimeException(Message.INVALID_REQUEST);
    }
  }

  private static LocalDate parseLocalDate(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      return null;
    }
    return LocalDate.parse(dateString);
  }

  public void validate() {
    if (this.getTitle() == null || this.getTitle().isEmpty()) {
      throw new RuntimeException(Message.EMPTY_PLAN_TITLE);
    }
    if (this.getStartDate() == null || this.getEndDate() == null) {
      throw new RuntimeException(Message.EMPTY_PLAN_DATE);
    }
    if (this.getStartDate().isAfter(this.getEndDate())) {
      throw new RuntimeException(Message.INVALID_PLAN_DATE);
    }
    if (this.getAlarmDate() != null && this.getAlarmDate().isAfter(this.getEndDate())) {
      throw new RuntimeException(Message.INVALID_PLAN_ALARM_DATE);
    }
  }
}
