package com.kh.model.dto.detail;

import com.kh.constant.Message;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDetailRequestDto {

  private int planId;
  private String contents;
  private LocalDate startDate;
  private LocalTime startTime;
  private LocalTime endTime;

  public static CreateDetailRequestDto from(HttpServletRequest request) {
    try {
      String planId = request.getParameter("planId");
      String contents = request.getParameter("contents");
      String startDate = request.getParameter("startDate");
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");

      return CreateDetailRequestDto.builder()
          .planId(Integer.parseInt(planId))
          .contents(contents)
          .startDate(parseLocalDate(startDate))
          .startTime(parseLocalTime(startTime))
          .endTime(parseLocalTime(endTime))
          .build();
    } catch (RuntimeException e) {
      throw new RuntimeException(Message.INVALID_REQUEST);
    }
  }

  private static LocalDate parseLocalDate(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      throw new RuntimeException(Message.EMPTY_DETAIL_DATE);
    }
    return LocalDate.parse(dateString);
  }

  private static LocalTime parseLocalTime(String timeString) {
    if (timeString == null || timeString.isEmpty()) {
      throw new RuntimeException(Message.EMPTY_DETAIL_TIME);
    }
    return LocalTime.parse(timeString);
  }

  public void validate() {
    Plan parent = new PlanDao().findByPlanId(this.getPlanId());

    if (this.getStartDate().isAfter(parent.getEndDate().toLocalDate().plusDays(1))
        || this.getStartDate().isBefore(parent.getStartDate().toLocalDate())) {
      throw new RuntimeException(Message.INVALID_DETAIL_DATE);
    }

    if (this.getStartTime().isAfter(this.getEndTime())) {
      throw new RuntimeException(Message.INVALID_DETAIL_TIME);
    }
  }
}
