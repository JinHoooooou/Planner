package com.kh.plan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Plan {

  private int id;
  private String title;
  private int timerCount;
  private String memo;
  private boolean clear;

  public Plan() {
    this.timerCount = 0;
    this.clear = false;
    this.memo = "";
  }


  private Plan(String title, String memo) {
    this.title = title;
    this.memo = memo;
  }

  public static Plan create(String title) {
    return new Plan(title, "");
  }

  public static Plan create(String title, String memo) {
    return new Plan(title, memo);
  }


  @Override
  public String toString() {
    return "title: " + this.getTitle() + " / memo: " + this.getMemo();
  }

}
