package com.kh.model.vo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

  private int id;
  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate createDate;

  @Override
  public String toString() {
    return "title: " + this.getTitle();
  }

}
