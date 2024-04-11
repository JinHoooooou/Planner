package com.kh.model.dto.plan;

import com.kh.constant.Message;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePlanRequestDto {

  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate remindAlarmDate;

  public static CreatePlanRequestDto from(HttpServletRequest request) {
    try {
      String title = request.getParameter("title");
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      String remindAlarmDate = request.getParameter("remindAlarmDate");
      return CreatePlanRequestDto.builder()
          .title(title)
          .startDate(parseLocalDate(startDate))
          .endDate(parseLocalDate(endDate))
          .remindAlarmDate(parseLocalDate(remindAlarmDate))
          .build();
    } catch (RuntimeException e) {
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
    if (this.getTitle().isEmpty()) {
      throw new RuntimeException(Message.EMPTY_PLAN_TITLE);
    }
    if (this.getStartDate().isAfter(this.getEndDate())) {
      throw new RuntimeException(Message.INVALID_PLAN_DATE);
    }
    if (this.getRemindAlarmDate() != null && this.getRemindAlarmDate().isAfter(this.getEndDate())) {
      throw new RuntimeException(Message.INVALID_PLAN_ALARM_DATE);
    }
  }
}
